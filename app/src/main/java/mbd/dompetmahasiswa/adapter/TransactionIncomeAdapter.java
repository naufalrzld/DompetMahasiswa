package mbd.dompetmahasiswa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;
import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.models.OutcomeModel;
import mbd.dompetmahasiswa.utils.CurrencyConverter;

/**
 * Created by Naufal on 26/03/2018.
 */

public class TransactionIncomeAdapter extends RecyclerView.Adapter<TransactionIncomeAdapter.ViewHolder> {
    private Context context;
    private List<IncomeModel> incomeList;
    private List<OutcomeModel> outcomeList;

    public TransactionIncomeAdapter(Context context, List<IncomeModel> incomeList) {
        this.context = context;
        this.incomeList = incomeList;
    }

    @Override
    public TransactionIncomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransactionIncomeAdapter.ViewHolder holder, int position) {
        IncomeModel incomeModel = incomeList.get(position);

        CurrencyConverter currencyConverter = new CurrencyConverter();

        holder.tvNote.setText(incomeModel.getNote());
        holder.tvNominal.setText(currencyConverter.convertToIDR(incomeModel.getIncome()));
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lyt_income)
        LinearLayout lytIncome;
        @BindView(R.id.tv_note)
        TextView tvNote;
        @BindView(R.id.tv_nominal)
        TextView tvNominal;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
