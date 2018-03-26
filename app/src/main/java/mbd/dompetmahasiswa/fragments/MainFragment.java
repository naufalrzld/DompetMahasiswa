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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.adapter.TransactionAdapter;
import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.utils.Database;

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
    @BindView(R.id.lyt_outcome)
    LinearLayout lytOutcome;
    @BindView(R.id.rv_outcome)
    RecyclerView rvOutcome;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private Database db;
    private TransactionAdapter adapter;
    private List<IncomeModel> incomeList = new ArrayList<>();

    private String date;

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

        Bundle dataBundle = this.getArguments();
        date = dataBundle.getString("date");

        adapter = new TransactionAdapter(getContext(), incomeList);
        rvIncome.setHasFixedSize(true);
        rvIncome.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIncome.setNestedScrollingEnabled(false);
        rvIncome.setAdapter(adapter);

        tvIncome.setText(String.valueOf(sumIncome(incomeList)));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllIncome(date);
    }

    private int sumIncome(List<IncomeModel> incomeList) {
        int sum = 0;
        for (IncomeModel incomeModel : incomeList) {
            sum += incomeModel.getIncome();
        }

        return sum;
    }

    private void getAllIncome(String data) {
        incomeList.clear();
        for (IncomeModel incomeModel : db.getAllIncome(data)) {
            int id = incomeModel.getID();
            int income = incomeModel.getIncome();
            String note = incomeModel.getNote();
            String date = incomeModel.getDate();

            Log.d("data", date);

            incomeList.add(new IncomeModel(id, income, note, date));
        }

        if (!incomeList.isEmpty()) {
            scrollView.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);

            tvIncome.setText(String.valueOf(sumIncome(incomeList)));

            adapter.notifyDataSetChanged();
        }
    }
}
