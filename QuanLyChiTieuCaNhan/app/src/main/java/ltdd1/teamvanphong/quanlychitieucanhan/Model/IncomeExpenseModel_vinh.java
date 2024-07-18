package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class IncomeExpenseModel_vinh {
    private int incomeExpenseId;
    private int type; //0 chi tiêu, 1 thu nhập
    private String amount;
    private String date;
    private String note;
    private int userId;
    private int categoryId;

    public int getIncomeExpenseId() {
        return incomeExpenseId;
    }

    public void setIncomeExpenseId(int incomeExpenseId) {
        this.incomeExpenseId = incomeExpenseId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type;}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private SQLiteDatabase db;

    public IncomeExpenseModel_vinh(Context context) {
        ExpenseDB dbHelper = new ExpenseDB(context);
        db = dbHelper.getReadableDatabase();
    }

    public HashMap<String, Integer> getMonthlyExpenses(int year) {
        HashMap<String, Integer> monthlyExpenses = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyExpenses.put("Tháng " + i, 0);
        }

        String query = "SELECT strftime('%m', " + ExpenseDB.getDate() + ") AS month, SUM(" + ExpenseDB.getAmount() + ") AS total " +
                "FROM " + ExpenseDB.getTableIncomeExpense() + " " +
                "WHERE " + ExpenseDB.getType() + " = 0 AND strftime('%Y', " + ExpenseDB.getDate() + ") = ? " +
                "GROUP BY month";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(year)});

        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                monthlyExpenses.put("Tháng " + Integer.parseInt(month), total);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return monthlyExpenses;
    }

    public HashMap<String, Integer> getMonthlyIncome(int year) {
        HashMap<String, Integer> monthlyIncome = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyIncome.put("Tháng " + i, 0);
        }

        String query = "SELECT strftime('%m', " + ExpenseDB.getDate() + ") AS month, SUM(" + ExpenseDB.getAmount() + ") AS total " +
                "FROM " + ExpenseDB.getTableIncomeExpense() + " " +
                "WHERE " + ExpenseDB.getType() + " = 1 AND strftime('%Y', " + ExpenseDB.getDate() + ") = ? " +
                "GROUP BY month";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(year)});

        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                monthlyIncome.put("Tháng " + Integer.parseInt(month), total);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return monthlyIncome;
    }

    public HashMap<String, Integer> getYearlySummary(int year) {
        HashMap<String, Integer> yearlySummary = new HashMap<>();
        yearlySummary.put("TotalIncome", 0);
        yearlySummary.put("TotalExpense", 0);

        String queryIncome = "SELECT SUM(" + ExpenseDB.getAmount() + ") AS totalIncome " +
                "FROM " + ExpenseDB.getTableIncomeExpense() + " " +
                "WHERE " + ExpenseDB.getType() + " = 1 AND strftime('%Y', " + ExpenseDB.getDate() + ") = ?";
        String queryExpense = "SELECT SUM(" + ExpenseDB.getAmount() + ") AS totalExpense " +
                "FROM " + ExpenseDB.getTableIncomeExpense() + " " +
                "WHERE " + ExpenseDB.getType() + " = 0 AND strftime('%Y', " + ExpenseDB.getDate() + ") = ?";

        Cursor cursorIncome = db.rawQuery(queryIncome, new String[]{String.valueOf(year)});
        Cursor cursorExpense = db.rawQuery(queryExpense, new String[]{String.valueOf(year)});

        if (cursorIncome.moveToFirst()) {
            int totalIncome = cursorIncome.getInt(cursorIncome.getColumnIndexOrThrow("totalIncome"));
            yearlySummary.put("TotalIncome", totalIncome);
        }
        cursorIncome.close();

        if (cursorExpense.moveToFirst()) {
            int totalExpense = cursorExpense.getInt(cursorExpense.getColumnIndexOrThrow("totalExpense"));
            yearlySummary.put("TotalExpense", totalExpense);
        }
        cursorExpense.close();

        return yearlySummary;
    }
}
