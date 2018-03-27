package mbd.dompetmahasiswa.adapter;

import android.app.Activity;
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
import mbd.dompetmahasiswa.models.WalletModel;
import mbd.dompetmahasiswa.utils.CurrencyConverter;
import mbd.dompetmahasiswa.utils.SharedPreferenceUtil;

/**
 * Created by Naufal on 27/03/2018.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Context context;
    private List<WalletModel> listWallet;
    private CurrencyConverter currencyConverter;

    public WalletAdapter(Context context, List<WalletModel> listWallet) {
        this.context = context;
        this.listWallet = listWallet;
        this.currencyConverter = new CurrencyConverter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WalletModel walletModel = listWallet.get(position);

        final SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(context);

        holder.tvWalletName.setText(walletModel.getWalletName());
        holder.tvWalletBalance.setText(currencyConverter.convertToIDR(walletModel.getBalance()));
        holder.walletItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceUtil.setWallet(walletModel.getId());
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listWallet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wallet_item)
        LinearLayout walletItem;
        @BindView(R.id.tv_wallet_name)
        TextView tvWalletName;
        @BindView(R.id.tv_wallet_balance)
        TextView tvWalletBalance;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
