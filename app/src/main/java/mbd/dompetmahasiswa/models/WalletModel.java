package mbd.dompetmahasiswa.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Naufal on 27/03/2018.
 */

public class WalletModel implements Parcelable {
    private int id;
    private String walletName;
    private int balance;

    public WalletModel() {
    }

    public WalletModel(int id, String walletName, int balance) {
        this.id = id;
        this.walletName = walletName;
        this.balance = balance;
    }

    public WalletModel(String walletName, int balance) {
        this.walletName = walletName;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.walletName);
        dest.writeInt(this.balance);
    }

    protected WalletModel(Parcel in) {
        this.id = in.readInt();
        this.walletName = in.readString();
        this.balance = in.readInt();
    }

    public static final Parcelable.Creator<WalletModel> CREATOR = new Parcelable.Creator<WalletModel>() {
        @Override
        public WalletModel createFromParcel(Parcel source) {
            return new WalletModel(source);
        }

        @Override
        public WalletModel[] newArray(int size) {
            return new WalletModel[size];
        }
    };
}
