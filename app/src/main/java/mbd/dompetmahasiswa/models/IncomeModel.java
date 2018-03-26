package mbd.dompetmahasiswa.models;

/**
 * Created by Naufal on 26/03/2018.
 */

public class IncomeModel {
    private int ID;
    private int income;
    private String note;
    private String date;

    public IncomeModel() {
    }

    public IncomeModel(int income, String note, String date) {
        this.income = income;
        this.note = note;
        this.date = date;
    }

    public IncomeModel(int ID, int income, String note, String date) {
        this.ID = ID;
        this.income = income;
        this.note = note;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
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
