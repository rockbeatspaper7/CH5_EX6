package com.example.myfinances;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AccountDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "finances.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CD_ACCOUNT =
            "CREATE TABLE CD_Account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "accNumber TEXT NOT NULL, " +
                    "initialBalance INTEGER NOT NULL, " +
                    "currentBalance INTEGER NOT NULL, " +
                    "interestRate INTEGER NOT NULL);";

    private static final String CREATE_TABLE_LOANS_ACCOUNT =
            "CREATE TABLE LOANS_Account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "accNumber TEXT NOT NULL, " +
                    "initialBalance INTEGER NOT NULL, " +
                    "currentBalance INTEGER NOT NULL, " +
                    "paymentAmount INTEGER NOT NULL, " +
                    "interestRate INTEGER NOT NULL);";

    private static final String CREATE_TABLE_CHECKINGS_ACCOUNT =
            "CREATE TABLE CHECKINGS_Account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "accNumber TEXT NOT NULL, " +
                    "currentBalance INTEGER NOT NULL);";
    public AccountDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CD_ACCOUNT);
        db.execSQL(CREATE_TABLE_LOANS_ACCOUNT);
        db.execSQL(CREATE_TABLE_CHECKINGS_ACCOUNT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AccountDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion +
                        ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS CD_Account");
        db.execSQL("DROP TABLE IF EXISTS LOANS_Account");
        db.execSQL("DROP TABLE IF EXISTS CHECKINGS_Account");
        onCreate(db);  // Recreate the table
    }
}

