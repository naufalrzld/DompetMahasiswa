package mbd.dompetmahasiswa.models;

/**
 * Created by Naufal on 27/03/2018.
 */

public class WalletModel {
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
}
