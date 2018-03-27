package mbd.dompetmahasiswa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.adapter.WalletAdapter;
import mbd.dompetmahasiswa.models.WalletModel;
import mbd.dompetmahasiswa.utils.Database;

public class WalletActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_wallet)
    RecyclerView rvWallet;
    @BindView(R.id.lyt_no_wallet)
    LinearLayout lytNoWallet;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private WalletAdapter adapter;
    private Database db;
    private List<WalletModel> listWallet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.activity_title_wallet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new Database(this);

        adapter = new WalletAdapter(this, listWallet);
        rvWallet.setHasFixedSize(true);
        rvWallet.setLayoutManager(new LinearLayoutManager(this));
        rvWallet.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WalletActivity.this, AddWalletActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWallet();
    }

    private void getWallet() {
        listWallet.clear();
        if (!db.walletIsEmpty()) {
            for (WalletModel walletModel : db.getAllWallet()) {
                int id = walletModel.getId();
                String walletName = walletModel.getWalletName();
                int balance = walletModel.getBalance();

                listWallet.add(new WalletModel(id, walletName, balance));
            }

            adapter.notifyDataSetChanged();
            lytNoWallet.setVisibility(View.GONE);
        } else {
            lytNoWallet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
