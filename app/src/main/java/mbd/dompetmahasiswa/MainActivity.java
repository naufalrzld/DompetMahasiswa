package mbd.dompetmahasiswa;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.activity.AddTransactionActivity;
import mbd.dompetmahasiswa.activity.WalletActivity;
import mbd.dompetmahasiswa.adapter.ViewPagerAdapter;
import mbd.dompetmahasiswa.fragments.MainFragment;
import mbd.dompetmahasiswa.models.WalletModel;
import mbd.dompetmahasiswa.utils.CurrencyConverter;
import mbd.dompetmahasiswa.utils.Database;
import mbd.dompetmahasiswa.utils.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab_add)
    FloatingActionsMenu fabAdd;
    @BindView(R.id.fab_add_income)
    FloatingActionButton fabAddIncome;
    @BindView(R.id.fab_add_expanse)
    FloatingActionButton fabAddExpanse;

    private Database db;
    private ViewPagerAdapter adapter;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private CurrencyConverter currencyConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        db = new Database(this);
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        currencyConverter = new CurrencyConverter();

        fabAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.walletIsEmpty()) {
                    Toast.makeText(getApplicationContext(), "Silahkan buat wallet terlebih dahulu", Toast.LENGTH_SHORT).show();
                    fabAdd.collapse();
                } else {
                    fabAdd.collapse();
                    Intent i = new Intent(MainActivity.this, AddTransactionActivity.class);
                    i.putExtra("type", AddTransactionActivity.TYPE_INCOME);
                    i.putExtra("wallet", getWallet(sharedPreferenceUtil.getWalletID()));
                    startActivity(i);
                }
            }
        });

        fabAddExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.walletIsEmpty()) {
                    Toast.makeText(getApplicationContext(), "Silahkan buat wallet terlebih dahulu", Toast.LENGTH_SHORT).show();
                    fabAdd.collapse();
                } else {
                    fabAdd.collapse();
                    Intent i = new Intent(MainActivity.this, AddTransactionActivity.class);
                    i.putExtra("type", AddTransactionActivity.TYPE_EXPANSE);
                    i.putExtra("wallet", getWallet(sharedPreferenceUtil.getWalletID()));
                    startActivity(i);
                }
            }
        });

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());

            int indexItem = 0;
            int currentDateIndexItem = 0;

            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Calendar calendar = Calendar.getInstance();
            for (int numDays=5; numDays > 0; numDays--) {
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_YEAR, 0-numDays);

                if (sdf.format(calendar.getTime()).equals(sdf.format(currentDate.getTime()))) {
                    currentDateIndexItem = indexItem;
                }
                Bundle bundle = new Bundle();
                bundle.putString("date", sdf.format(calendar.getTime()));

                MainFragment mainFragment = new MainFragment();
                mainFragment.setArguments(bundle);

                adapter.addFragment(mainFragment, sdf.format(calendar.getTime()));
                indexItem++;
            }

            for (int numDays=0; numDays <= 5; numDays++) {
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_YEAR, numDays);

                if (sdf.format(calendar.getTime()).equals(sdf.format(currentDate.getTime()))) {
                    currentDateIndexItem = indexItem;
                }
                Bundle bundle = new Bundle();
                bundle.putString("date", sdf.format(calendar.getTime()));

                MainFragment mainFragment = new MainFragment();
                mainFragment.setArguments(bundle);

                adapter.addFragment(mainFragment, sdf.format(calendar.getTime()));
                indexItem++;
            }

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(currentDateIndexItem);
            viewPager.setOffscreenPageLimit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WalletModel getWallet(int walletID) {
        return db.getWallet(walletID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        if (sharedPreferenceUtil.getWalletID() != 0) {
            WalletModel walletModel = getWallet(sharedPreferenceUtil.getWalletID());
            getSupportActionBar().setTitle(walletModel.getWalletName());
            getSupportActionBar().setSubtitle(currencyConverter.convertToIDR(walletModel.getBalance()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_wallet:
                Intent i = new Intent(MainActivity.this, WalletActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
