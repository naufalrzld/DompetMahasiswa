package mbd.dompetmahasiswa.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Naufal on 27/03/2018.
 */

public class SharedPreferenceUtil {
    private static final String PREF_NAME = "Wallet";
    private static final String KEY_WALLET_ID = "walletID";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setWalletID(int walletID) {
        editor.putInt(KEY_WALLET_ID, walletID);
        editor.commit();
    }

    public int getWalletID() {
        return sharedPreferences.getInt(KEY_WALLET_ID, 0);
    }
}
