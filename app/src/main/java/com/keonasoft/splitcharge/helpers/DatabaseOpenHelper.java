package com.keonasoft.splitcharge.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kushal on 4/6/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SplitChargeDB";
    private static final int DATABASE_VERSION = 1;

    public static final String CHARGE_TABLE_NAME = "charge";
    public static final String CHARGE_ID_COL = "id";
    public static final String CHARGE_NAME_COL = "name";
    private static final String CHARGE_TABLE_CREATE =
            "CREATE TABLE " + CHARGE_TABLE_NAME + " (" +
                    CHARGE_ID_COL + " INT PRIMARY KEY, " +
                    CHARGE_NAME_COL + " TEXT);";

    public static final String PERSON_TABLE_NAME = "person";
    public static final String PERSON_CHARGEID_COL = "charge_id";
    public static final String PERSON_ID_COL = "id";
    public static final String PERSON_USERNAME_COL = "username";
    public static final String PERSON_COST_COL = "cost";
    public static final String PERSON_COMPLETED_COL = "completed";
    private static final String PERSON_TABLE_CREATE =
            "CREATE TABLE " + PERSON_TABLE_NAME + " (" +
                    PERSON_CHARGEID_COL + " INT, " +
                    PERSON_ID_COL + " INT, " +
                    PERSON_USERNAME_COL + " TEXT, " +
                    PERSON_COST_COL + " TEXT, " +
                    PERSON_COMPLETED_COL + " BOOLEAN, " +
                    "FOREIGN KEY(" + PERSON_CHARGEID_COL + ") REFERENCES " + CHARGE_TABLE_NAME + "(" + CHARGE_ID_COL + "), " +
                    "PRIMARY KEY(" + PERSON_CHARGEID_COL + ", " + PERSON_ID_COL + "));";

    public static final String ITEM_TABLE_NAME = "item";
    public static final String ITEM_CHARGEID_COL = "charge_id";
    public static final String ITEM_PERSONID_COL = "person_id";
    public static final String ITEM_ID_COL = "id";
    public static final String ITEM_NAME_COL = "name";
    public static final String ITEM_COST_COL = "cost";
    private static final String ITEM_TABLE_CREATE =
            "CREATE TABLE " + ITEM_TABLE_NAME + " (" +
                    ITEM_CHARGEID_COL + " INT, " +
                    ITEM_PERSONID_COL + " INT, " +
                    ITEM_ID_COL + " INT, " +
                    ITEM_NAME_COL + " TEXT, " +
                    ITEM_COST_COL + " INT, " +
                    "FOREIGN KEY(" + ITEM_CHARGEID_COL + ") REFERENCES " + CHARGE_TABLE_NAME + "(" + CHARGE_ID_COL + "), " +
                    "FOREIGN KEY(" + ITEM_PERSONID_COL + ") REFERENCES " + PERSON_TABLE_NAME + "(" + PERSON_ID_COL + "), " +
                    "PRIMARY KEY(" + ITEM_CHARGEID_COL + ", " + ITEM_PERSONID_COL + ", " + ITEM_ID_COL + "));";

    public static final String SPLIT_TABLE_NAME = "split";
    public static final String SPLIT_CHARGEID_COL = "charge_id";
    public static final String SPLIT_ID_COL = "id";
    public static final String SPLIT_NAME_COL = "name";
    public static final String SPLIT_COST_COL = "cost";
    public static final String SPLIT_PERCENT_COL = "percent";
    private static final String SPLIT_TABLE_CREATE =
            "CREATE TABLE " + SPLIT_TABLE_NAME + " (" +
                    SPLIT_CHARGEID_COL + " INT, " +
                    SPLIT_ID_COL + " INT, " +
                    SPLIT_NAME_COL + " TEXT, " +
                    SPLIT_COST_COL + " INT, " +
                    SPLIT_PERCENT_COL + " BOOLEAN, " +
                    "FOREIGN KEY(" + SPLIT_CHARGEID_COL + ") REFERENCES " + CHARGE_TABLE_NAME + "(" + CHARGE_ID_COL + "), " +
                    "PRIMARY KEY(" + SPLIT_CHARGEID_COL + ", " + SPLIT_ID_COL + "));";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CHARGE_TABLE_CREATE);
        db.execSQL(PERSON_TABLE_CREATE);
        db.execSQL(ITEM_TABLE_CREATE);
        db.execSQL(SPLIT_TABLE_CREATE);
        db.execSQL("INSERT INTO " + CHARGE_TABLE_NAME + " VALUES (1, 'Default');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
