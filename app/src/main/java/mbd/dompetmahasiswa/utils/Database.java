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

/**
 * Created by Naufal on 26/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String TAG = Database.class.getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DompetMahasiswaDB";
    private static final String TB_INCOME = "tb_income";
    private static final String TB_OUTCOME = "tb_outcome";

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
        String CREATE_INCOME_TABLE = "CREATE TABLE " + TB_INCOME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_INCOME + " INTEGER, " +
                KEY_NOTE + " TEXT, " +
                KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_INCOME_TABLE);

        String CREATE_OUTCOME_TABLE = "CREATE TABLE " + TB_OUTCOME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_INCOME + " INTEGER, " +
                KEY_NOTE + " TEXT, " +
                KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_OUTCOME_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_INCOME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_OUTCOME);

        onCreate(sqLiteDatabase);
    }

    public int addIncome(IncomeModel incomeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_INCOME, incomeModel.getIncome());
        values.put(KEY_NOTE, incomeModel.getNote());
        values.put(KEY_DATE, incomeModel.getDate());

        long ID = db.insert(TB_INCOME, null, values);
        db.close();
        Log.d(TAG, "New Income Insert into Table Income: " +ID);
        return (int) ID;
    }

    public List<IncomeModel> getAllIncome(String date) {
        List<IncomeModel> incomeList = new ArrayList<>();
        String query = "SELECT * FROM " + TB_INCOME + " WHERE " + KEY_DATE + " = '" + date + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                IncomeModel incomeModel = new IncomeModel();
                incomeModel.setID(cursor.getInt(0));
                incomeModel.setIncome(cursor.getInt(1));
                incomeModel.setNote(cursor.getString(2));
                incomeModel.setDate(cursor.getString(3));

                incomeList.add(incomeModel);
            } while (cursor.moveToNext());
        }
        return incomeList;
    }
}
