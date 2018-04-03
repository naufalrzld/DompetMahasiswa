package mbd.dompetmahasiswa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.adapter.TransactionIncomeAdapter;
import mbd.dompetmahasiswa.adapter.TransactionOutcomeAdapter;
import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.models.OutcomeModel;
import mbd.dompetmahasiswa.utils.CurrencyConverter;
import mbd.dompetmahasiswa.utils.Database;
import mbd.dompetmahasiswa.utils.SharedPreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.scrl_item)
    NestedScrollView scrollView;
    @BindView(R.id.tv_income)
    TextView tvIncome;
    @BindView(R.id.tv_outcome)
    TextView tvOutcome;
    @BindView(R.id.tv_sisa)
    TextView tvSisa;

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month_year)
    TextView tvMonthYear;
    @BindView(R.id.rg_type_transaction)
    RadioGroup rgTypeTransaction;
    @BindView(R.id.rb_income)
    RadioButton rbIncome;
    @BindView(R.id.rb_outcome)
    RadioButton rbOutcome;

    @BindView(R.id.lyt_income)
    LinearLayout lytIncome;
    @BindView(R.id.rv_income)
    RecyclerView rvIncome;
    @BindView(R.id.tv_no_income)
    TextView tvNoIncome;
    @BindView(R.id.lyt_outcome)
    LinearLayout lytOutcome;
    @BindView(R.id.rv_outcome)
    RecyclerView rvOutcome;
    @BindView(R.id.tv_no_outcome)
    TextView tvNoOutcome;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private Database db;
    private TransactionIncomeAdapter adapterIncome;
    private TransactionOutcomeAdapter adapterOutcome;
    private List<IncomeModel> incomeList = new ArrayList<>();
    private List<OutcomeModel> outcomeList = new ArrayList<>();

    private int walletID;
    private String date;
    private CurrencyConverter currencyConverter;
    private Bundle dataBundle;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);

        db = new Database(getContext());

        currencyConverter = new CurrencyConverter();
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());

        dataBundle = this.getArguments();
        walletID = sharedPreferenceUtil.getWalletID();
        date = dataBundle.getString("date");

        try {
            Locale localeID = new Locale("in", "ID");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd");
            SimpleDateFormat day = new SimpleDateFormat("EEEE", localeID);
            SimpleDateFormat monthYear = new SimpleDateFormat("MMMM yyyy");

            String showDate = sdfDate.format(sdf.parse(date));
            String showDay = day.format(sdf.parse(date));
            String showMonthYear = monthYear.format(sdf.parse(date));

            tvDate.setText(showDate);
            tvDay.setText(showDay);
            tvMonthYear.setText(showMonthYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapterIncome = new TransactionIncomeAdapter(getContext(), incomeList);
        rvIncome.setHasFixedSize(true);
        rvIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIncome.setNestedScrollingEnabled(false);
        rvIncome.setAdapter(adapterIncome);

        adapterOutcome = new TransactionOutcomeAdapter(getContext(), outcomeList);
        rvOutcome.setHasFixedSize(true);
        rvOutcome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOutcome.setNestedScrollingEnabled(false);
        rvOutcome.setAdapter(adapterOutcome);

        getAllIncome(walletID, date);
        getAllOutcome(walletID, date);

        tvIncome.setText(currencyConverter.convertToIDR(sumIncome(incomeList)));
        tvOutcome.setText(currencyConverter.convertToIDR(sumOutcome(outcomeList)));
        tvSisa.setText(currencyConverter.convertToIDR(sumBalance(sumIncome(incomeList), sumOutcome(outcomeList))));

        rgTypeTransaction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.rb_income) {
                    lytIncome.setVisibility(View.VISIBLE);
                    lytOutcome.setVisibility(View.GONE);
                } else {
                    lytIncome.setVisibility(View.GONE);
                    lytOutcome.setVisibility(View.VISIBLE);
                }
            }
        });

        if (rbIncome.isChecked()) {
            lytOutcome.setVisibility(View.GONE);
        } else {
            lytIncome.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllIncome(walletID, date);
        getAllOutcome(walletID, date);

        tvSisa.setText(currencyConverter.convertToIDR(sumBalance(sumIncome(incomeList), sumOutcome(outcomeList))));

        if (!incomeList.isEmpty() || !outcomeList.isEmpty()) {
            tvNoData.setVisibility(View.GONE);
        }
    }

    private int sumIncome(List<IncomeModel> incomeList) {
        int sum = 0;
        for (IncomeModel incomeModel : incomeList) {
            sum += incomeModel.getIncome();
        }

        return sum;
    }

    private int sumOutcome(List<OutcomeModel> outcomeList) {
        int sum = 0;
        for (OutcomeModel outcomeModel : outcomeList) {
            sum += outcomeModel.getOutcome();
        }

        return sum;
    }

    private int sumBalance(int income, int outcome) {
        return income - outcome;
    }

    private void getAllIncome(int walletID, String date) {
        incomeList.clear();
        for (IncomeModel incomeModel : db.getAllIncome(String.valueOf(walletID), date)) {
            int id = incomeModel.getID();
            int income = incomeModel.getIncome();
            String note = incomeModel.getNote();
            String trxDate = incomeModel.getDate();

            incomeList.add(new IncomeModel(id, walletID, income, note, trxDate));
        }

        if (!incomeList.isEmpty()) {
            scrollView.setVisibility(View.VISIBLE);
            rvIncome.setVisibility(View.VISIBLE);
            tvNoIncome.setVisibility(View.GONE);

            tvIncome.setText(currencyConverter.convertToIDR(sumIncome(incomeList)));

            adapterIncome.notifyDataSetChanged();
        } else {
            rvIncome.setVisibility(View.GONE);
            tvNoIncome.setVisibility(View.VISIBLE);
        }
    }

    private void getAllOutcome(int walletID, String date) {
        outcomeList.clear();
        for (OutcomeModel outcomeModel : db.getAllOutcome(String.valueOf(walletID), date)) {
            int id = outcomeModel.getId();
            int income = outcomeModel.getOutcome();
            String note = outcomeModel.getNote();
            String trxDate = outcomeModel.getDate();

            outcomeList.add(new OutcomeModel(id, walletID, income, note, trxDate));
        }

        if (!outcomeList.isEmpty()) {
            scrollView.setVisibility(View.VISIBLE);
            rvOutcome.setVisibility(View.VISIBLE);
            tvNoOutcome.setVisibility(View.GONE);

            tvOutcome.setText(currencyConverter.convertToIDR(sumOutcome(outcomeList)));

            adapterOutcome.notifyDataSetChanged();
        } else {
            rvOutcome.setVisibility(View.GONE);
            tvNoOutcome.setVisibility(View.VISIBLE);
        }
    }
}
