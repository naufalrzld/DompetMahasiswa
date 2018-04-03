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

public class TransactionOutcomeAdapter extends RecyclerView.Adapter<TransactionOutcomeAdapter.ViewHolder> {
    private Context context;
    private List<OutcomeModel> outcomeList;

    public TransactionOutcomeAdapter(Context context, List<OutcomeModel> outcomeList) {
        this.context = context;
        this.outcomeList = outcomeList;
    }

    @Override
    public TransactionOutcomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransactionOutcomeAdapter.ViewHolder holder, int position) {
        OutcomeModel outcomeModel = outcomeList.get(position);

        CurrencyConverter currencyConverter = new CurrencyConverter();

        holder.tvNote.setText(outcomeModel.getNote());
        holder.tvNominal.setText(currencyConverter.convertToIDR(outcomeModel.getOutcome()));
        holder.tvNominal.setTextColor(context.getResources().getColor(R.color.colorRed));
    }

    @Override
    public int getItemCount() {
        return outcomeList.size();
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
