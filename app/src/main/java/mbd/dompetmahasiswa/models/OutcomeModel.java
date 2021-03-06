package mbd.dompetmahasiswa.models;

/**
 * Created by Naufal on 26/03/2018.
 */

public class OutcomeModel {
    private int id;
    private int walletID;
    private int outcome = 0;
    private String note;
    private String date;

    public OutcomeModel() {
    }

    public OutcomeModel(int walletID, int outcome, String note, String date) {
        this.walletID = walletID;
        this.outcome = outcome;
        this.note = note;
        this.date = date;
    }

    public OutcomeModel(int id, int walletID, int outcome, String note, String date) {
        this.id = id;
        this.walletID = walletID;
        this.outcome = outcome;
        this.note = note;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
