package mbd.dompetmahasiswa;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.adapter.ViewPagerAdapter;
import mbd.dompetmahasiswa.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
                calendar.add(Calendar.DAY_OF_MONTH, 0-numDays);

                MainFragment mainFragment = new MainFragment();
                adapter.addFragment(mainFragment, sdf.format(calendar.getTime()));
            }

            for (int numDays=0; numDays <= 5; numDays++) {
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, numDays);

                MainFragment mainFragment = new MainFragment();
                adapter.addFragment(mainFragment, sdf.format(calendar.getTime()));
            }

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(5);
            viewPager.setOffscreenPageLimit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}