package mbd.dompetmahasiswa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.models.WalletModel;
import mbd.dompetmahasiswa.utils.Database;

public class AddWalletActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.et_balance)
    EditText etBalance;

    private Database db;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_add_wallet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new Database(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        date = sdf.format(calendar.getTime());
    }

    private boolean isValidInput() {
        if (TextUtils.isEmpty(etWalletName.getText()) || TextUtils.isEmpty(etBalance.getText())) {
            if (TextUtils.isEmpty(etWalletName.getText())) {
                etWalletName.setError("Nama wallet harus diisi!");
            }

            if (TextUtils.isEmpty(etBalance.getText())) {
                etBalance.setError("Saldo awal harus diisi!");
            }

            return false;
        }

        return true;
    }

    private void saveWallet() {
        String walletName = etWalletName.getText().toString();
        int balance = Integer.parseInt(etBalance.getText().toString());

        int ID = db.addWallet(new WalletModel(walletName, balance));
        int incomeID = db.addIncome(new IncomeModel(ID, balance, "Saldo Awal", date));

        String message;
        if (ID != 0 && incomeID != 0) {
            message = "Wallet berhasil dibuat";
        } else {
            message = "Wallet gagal dibuat";
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_wallet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_save:
                if (isValidInput()) {
                    saveWallet();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
