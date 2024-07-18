package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class IncomeExpenseModel_nguyen {
    private int incomeExpenseId;
    private int type; //0 chi tiêu, 1 thu nhập
    private String amount;
    private String date;
    private String note;
    private int userId;
    private int categoryId;

    private SQLiteDatabase database;

    public IncomeExpenseModel_nguyen() {}
    public IncomeExpenseModel_nguyen(Context context) {
        ExpenseDB dbHelper = new ExpenseDB(context);
        this.database = dbHelper.getWritableDatabase();
    }
    public int getIncomeExpenseId() {
        return incomeExpenseId;
    }
    public void setIncomeExpenseId(int incomeExpenseId) {
        this.incomeExpenseId = incomeExpenseId;
    }
    public int getType() { return type; }
    public void setType(int type) { this.type = type;}
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {this.amount = amount;}
    public String getDate() { return date; }
    public void setDate(String date) {
        this.date = date;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public int getUserId() {return userId;}
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public List<Integer> getExpenseOrIncomeAmountsForUser(int userId, int month, int year, int type) {
        List<Integer> amounts = new ArrayList<>();
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-31", year, month);

        String query = "SELECT SUM(IE.Amount) " +
                "FROM IncomeExpense IE " +
                "JOIN Categories C ON IE.CategoryID = C.CategoryID " +
                "WHERE IE.UserID = ? AND IE.Type = ? AND IE.Date BETWEEN ? AND ? " +
                "GROUP BY C.CategoryID";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(type), startDate, endDate});

        if (cursor.moveToFirst()) {
            do {
                amounts.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return amounts;
    }


    public String getSumIncomeForUser(int userId, int month, int year) {
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-31", year, month);
        Cursor cursor = database.rawQuery(
                "SELECT SUM(Amount) FROM IncomeExpense WHERE UserID = ? AND Type = 1 AND Date BETWEEN ? AND ?",
                new String[]{String.valueOf(userId), startDate, endDate}
        );
        String sumIncome = "0";
        if (cursor.moveToFirst()) {
            sumIncome = cursor.getString(0);
        }
        cursor.close();
        return sumIncome;
    }

    public String getSumExpenseForUser(int userId, int month, int year) {

        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-31", year, month);
        Cursor cursor = database.rawQuery(
                "SELECT SUM(Amount) FROM IncomeExpense WHERE UserID = ? AND Type = 0 AND Date BETWEEN ? AND ?",
                new String[]{String.valueOf(userId), startDate, endDate}
        );
        String sumExpense = "0";
        if (cursor.moveToFirst()) {
            sumExpense = cursor.getString(0);
        }
        cursor.close();
        return sumExpense;
    }


    public List<IncomeExpenseModel_nguyen> getIncomeExpensesByMonth(int userId, int month, int year) {
        List<IncomeExpenseModel_nguyen> list = new ArrayList<>();
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-31", year, month);
        Cursor cursor = database.rawQuery("SELECT * FROM IncomeExpense WHERE UserID = ? AND Date BETWEEN ? AND ?",
                new String[]{String.valueOf(userId), startDate, endDate});
        if (cursor.moveToFirst()) {
            do {
                IncomeExpenseModel_nguyen item = new IncomeExpenseModel_nguyen();
                item.setIncomeExpenseId(cursor.getInt(0));
                item.setType(cursor.getInt(1));
                item.setAmount(cursor.getString(2));
                item.setDate(cursor.getString(3));
                item.setNote(cursor.getString(4));
                item.setUserId(cursor.getInt(5));
                item.setCategoryId(cursor.getInt(6));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

}
