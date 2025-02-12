package com.example.myfinances;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AccountDataSource {
    private SQLiteDatabase database;
    private AccountDBHelper dbHelper;

    public AccountDataSource(Context context) {
        dbHelper = new AccountDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertCDAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("initialBalance", account.getInitialBalance());
            values.put("currentBalance", account.getCurrentBalance());
            values.put("interestRate", account.getInterestRate());

            long result = database.insert("CD_Account", null, values);

            didSucceed = (result > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error inserting CD account", e);
        }
        return didSucceed;
    }

    public boolean updateCDAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("initialBalance", account.getInitialBalance());
            values.put("currentBalance", account.getCurrentBalance());
            values.put("interestRate", account.getInterestRate());

            int rowsUpdated = database.update("CD_Account", values, "id = ?", new String[]{String.valueOf(account.getId())});

            didSucceed = (rowsUpdated > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error updating CD account", e);
        }
        return didSucceed;
    }


    public boolean insertLoanAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("initialBalance", account.getInitialBalance());
            values.put("currentBalance", account.getCurrentBalance());
            values.put("paymentAmount", account.getPaymentAmount());
            values.put("interestRate", account.getInterestRate());

            long result = database.insert("LOANS_Account", null, values);

            didSucceed = (result > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error inserting loan account", e);
        }
        return didSucceed;
    }

    public boolean updateLoanAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("initialBalance", account.getInitialBalance());
            values.put("currentBalance", account.getCurrentBalance());
            values.put("paymentAmount", account.getPaymentAmount());
            values.put("interestRate", account.getInterestRate());

            int rowsUpdated = database.update("LOANS_Account", values, "id = ?", new String[]{String.valueOf(account.getId())});

            didSucceed = (rowsUpdated > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error updating loan account", e);
        }
        return didSucceed;
    }

    public boolean insertCheckingAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("currentBalance", account.getCurrentBalance());

            long result = database.insert("CHECKINGS_Account", null, values);

            didSucceed = (result > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error inserting checking account", e);
        }
        return didSucceed;
    }

    public boolean updateCheckingAccount(Accounts account) {
        boolean didSucceed = false;
        try {
            ContentValues values = new ContentValues();
            values.put("accNumber", account.getAccNumber());
            values.put("currentBalance", account.getCurrentBalance());

            int rowsUpdated = database.update("CHECKINGS_Account", values, "id = ?", new String[]{String.valueOf(account.getId())});

            didSucceed = (rowsUpdated > 0);
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error updating checking account", e);
        }
        return didSucceed;
    }


    public int getLastID(String tableName) {
        int lastId = -1;
        try {
            String query = "SELECT MAX(id) FROM " + tableName;
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                lastId = cursor.getInt(0);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("AccountDataSource", "Error getting last ID for " + tableName, e);
        }
        return lastId;
    }
}
