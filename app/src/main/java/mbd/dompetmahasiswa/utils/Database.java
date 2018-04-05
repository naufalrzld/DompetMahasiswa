package mbd.dompetmahasiswa.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mbd.dompetmahasiswa.models.IncomeModel;
import mbd.dompetmahasiswa.models.OutcomeModel;
import mbd.dompetmahasiswa.models.WalletModel;

/**
 * Created by Naufal on 26/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String TAG = Database.class.getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DompetMahasiswaDB";
    private static final String TB_WALLET = "tb_wallet";
    private static final String TB_INCOME = "tb_income";
    private static final String TB_OUTCOME = "tb_outcome";

    private static final String KEY_WALLET_ID = "wallet_id";
    private static final String KEY_WALLET_NAME = "wallet_name";
    private static final String KEY_BALANCE = "balance";

    private static final String KEY_ID = "id";
    private static final String KEY_INCOME = "income";
    private static final String KEY_OUTCOME = "outcome";
    private static final String KEY_NOTE = "note";
    private static final String KEY_DATE = "date";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WALLET_TABLE = "CREATE TABLE " + TB_WALLET + "(" +
                KEY_WALLET_ID + " INTEGER PRIMARY KEY, " +
                KEY_WALLET_NAME + " TEXT, " +
                KEY_BALANCE + " INTEGER " + ")";
        sqLiteDatabase.execSQL(CREATE_WALLET_TABLE);

        String CREATE_INCOME_TABLE = "CREATE TABLE " + TB_INCOME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_WALLET_ID + " INTEGER, " +
                KEY_INCOME + " INTEGER, " +
                KEY_NOTE + " TEXT, " +
                KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_INCOME_TABLE);

        String CREATE_OUTCOME_TABLE = "CREATE TABLE " + TB_OUTCOME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_WALLET_ID + " INTEGER, " +
                KEY_OUTCOME + " INTEGER, " +
                KEY_NOTE + " TEXT, " +
                KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_OUTCOME_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_WALLET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_INCOME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_OUTCOME);

        onCreate(sqLiteDatabase);
    }

    public boolean walletIsEmpty() {
        String query = "SELECT count(*) FROM " + TB_WALLET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    public int addWallet(WalletModel walletModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WALLET_NAME, walletModel.getWalletName());
        values.put(KEY_BALANCE, walletModel.getBalance());

        long ID = db.insert(TB_WALLET, null, values);
        db.close();
        Log.d(TAG, "New Wallet Insert into Table Wallet: " +ID);
        return (int) ID;
    }

    public List<WalletModel> getAllWallet() {
        List<WalletModel> listWallet = new ArrayList<>();
        String query = "SELECT * FROM " + TB_WALLET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                WalletModel walletModel = new WalletModel();
                walletModel.setId(cursor.getInt(0));
                walletModel.setWalletName(cursor.getString(1));
                walletModel.setBalance(cursor.getInt(2));

                listWallet.add(walletModel);
            } while (cursor.moveToNext());
        }

        return listWallet;
    }

    public WalletModel getWallet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TB_WALLET, new String[] {
                KEY_WALLET_ID,
                KEY_WALLET_NAME,
                KEY_BALANCE
        }, KEY_WALLET_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        WalletModel walletModel = new WalletModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));

        return walletModel;
    }

    public int updateWallet(WalletModel walletModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BALANCE, walletModel.getBalance());

        return db.update(TB_WALLET, values, KEY_WALLET_ID + "=?",
                new String[]{String.valueOf(walletModel.getId())});
    }

    public int addIncome(IncomeModel incomeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WALLET_ID, incomeModel.getWalletID());
        values.put(KEY_INCOME, incomeModel.getIncome());
        values.put(KEY_NOTE, incomeModel.getNote());
        values.put(KEY_DATE, incomeModel.getDate());

        long ID = db.insert(TB_INCOME, null, values);
        db.close();
        Log.d(TAG, "New Income Insert into Table Income: " +ID);
        return (int) ID;
    }

    public List<IncomeModel> getAllIncome(String walletID, String date) {
        List<IncomeModel> incomeList = new ArrayList<>();
        String query = "SELECT * FROM " + TB_INCOME + " WHERE " + KEY_DATE + " = '" + date + "'" +
                " AND " + KEY_WALLET_ID + " = " + walletID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                IncomeModel incomeModel = new IncomeModel();
                incomeModel.setID(cursor.getInt(0));
                incomeModel.setWalletID(cursor.getInt(1));
                incomeModel.setIncome(cursor.getInt(2));
                incomeModel.setNote(cursor.getString(3));
                incomeModel.setDate(cursor.getString(4));

                incomeList.add(incomeModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return incomeList;
    }

    public int addOutcome(OutcomeModel outcomeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WALLET_ID, outcomeModel.getWalletID());
        values.put(KEY_OUTCOME, outcomeModel.getOutcome());
        values.put(KEY_NOTE, outcomeModel.getNote());
        values.put(KEY_DATE, outcomeModel.getDate());

        long ID = db.insert(TB_OUTCOME, null, values);
        db.close();
        Log.d(TAG, "New Outcome Insert into Table Income: " +ID);
        return (int) ID;
    }

    public List<OutcomeModel> getAllOutcome(String walletID, String date) {
        List<OutcomeModel> outcomeList = new ArrayList<>();
        String query = "SELECT * FROM " + TB_OUTCOME + " WHERE " + KEY_DATE + " = '" + date + "'" +
                " AND " + KEY_WALLET_ID + " = " + walletID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                OutcomeModel outcomeModel = new OutcomeModel();
                outcomeModel.setId(cursor.getInt(0));
                outcomeModel.setWalletID(cursor.getInt(1));
                outcomeModel.setOutcome(cursor.getInt(2));
                outcomeModel.setNote(cursor.getString(3));
                outcomeModel.setDate(cursor.getString(4));

                outcomeList.add(outcomeModel);
            } while (cursor.moveToNext());
        }
        db.close();
        return outcomeList;
    }
}
