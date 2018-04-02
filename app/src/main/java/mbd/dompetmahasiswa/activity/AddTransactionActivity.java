package mbd.dompetmahasiswa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.models.WalletModel;
import mbd.dompetmahasiswa.utils.Database;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_rp)
    TextView tvRp;
    @BindView(R.id.et_nominal)
    EditText etNominal;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.et_wallet)
    EditText etWallet;

    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPANSE = "expanse";

    private Database db;

    private Intent dataIntent;
    private String type, date, toolbarTitle;
    private WalletModel walletModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new Database(this);

        dataIntent = getIntent();
        type = dataIntent.getStringExtra("type");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        date = sdf.format(calendar.getTime());

        etDate.setText("Hari Ini");

        if (type.equals(TYPE_EXPANSE)) {
            toolbarTitle = "Pengeluaran";

            walletModel = dataIntent.getParcelableExtra("wallet");

            tvRp.setTextColor(this.getResources().getColor(R.color.colorRed));
            etNominal.setTextColor(this.getResources().getColor(R.color.colorRed));
            etWallet.setText(walletModel.getWalletName());
        } else {
            toolbarTitle = "Pendapatan";
        }

        getSupportActionBar().setTitle(toolbarTitle);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddTransactionActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    private void save() {
        int nominal = Integer.parseInt(etNominal.getText().toString());
        String note = etNote.getText().toString();

        db.addIncome(new IncomeModel(nominal, note, date));
        Toast.makeText(getApplicationContext(), "Tersimpan", Toast.LENGTH_SHORT).show();
    }

    private boolean isInputValid() {
        if (TextUtils.isEmpty(etNominal.getText().toString())) {
            etNominal.setError("Masukkan Nominal");
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_transaction_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_save:
                if (isInputValid()) {
                    save();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        if ((monthOfYear+1) < 10) {
            month = "0" + (monthOfYear+1);
        } else {
            month = String.valueOf(monthOfYear+1);
        }
        date = dayOfMonth + "/" + month + "/" + year;
        etDate.setText(date);
    }
}
