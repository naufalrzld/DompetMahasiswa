package mbd.dompetmahasiswa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.dompetmahasiswa.R;

public class AddMoneyActivity extends AppCompatActivity {
    @BindView(R.id.tv_rp)
    TextView tvRp;
    @BindView(R.id.et_nominal)
    EditText etNominal;

    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPANSE = "expanse";

    private Intent dataaIntent;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        ButterKnife.bind(this);

        dataaIntent = getIntent();
        type = dataaIntent.getStringExtra("type");

        if (type.equals(TYPE_EXPANSE)) {
            tvRp.setTextColor(this.getResources().getColor(R.color.colorRed));
            etNominal.setTextColor(this.getResources().getColor(R.color.colorRed));
        }
    }
}
