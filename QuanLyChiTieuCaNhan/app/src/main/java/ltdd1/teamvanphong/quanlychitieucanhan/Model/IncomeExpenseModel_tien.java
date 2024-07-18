package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class IncomeExpenseModel_tien {
    private int incomeExpenseId;
    private int type; // 0 chi tiêu, 1 thu nhập
    private String amount;
    private String date;
    private String note;
    private int userId;
    private int categoryId;

    private String title; // Thu nhập, Chi tiêu, Tổng
    private String amountText; // Để lưu chuỗi báo cáo (e.g., 14,111,111đ)

    // Constructors
    public IncomeExpenseModel_tien() {
        // Default constructor
    }

    public IncomeExpenseModel_tien(String title, String amountText) {
        this.title = title;
        this.amountText = amountText;
    }

    // Getters and setters
    public int getIncomeExpenseId() {
        return incomeExpenseId;
    }

    public void setIncomeExpenseId(int incomeExpenseId) {
        this.incomeExpenseId = incomeExpenseId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmountText() {
        return amountText;
    }

    public void setAmountText(String amountText) {
        this.amountText = amountText;
    }

    // Method to generate report for user
    public static List<IncomeExpenseModel_tien> generateReportForUser(Context context, int userId) {
        List<IncomeExpenseModel_tien> reportList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Fetch income data
        Cursor incomeCursor = db.rawQuery("SELECT SUM(Amount) FROM IncomeExpense WHERE Type = 1 AND UserID = ?", new String[]{String.valueOf(userId)});
        if (incomeCursor.moveToFirst()) {
            String income = incomeCursor.getString(0) != null ? incomeCursor.getString(0) : "0";
            reportList.add(new IncomeExpenseModel_tien("Thu nhập", income + "đ"));
        } else {
            reportList.add(new IncomeExpenseModel_tien("Thu nhập", "0đ"));
        }
        incomeCursor.close();

        // Fetch expense data
        Cursor expenseCursor = db.rawQuery("SELECT SUM(Amount) FROM IncomeExpense WHERE Type = 0 AND UserID = ?", new String[]{String.valueOf(userId)});
        if (expenseCursor.moveToFirst()) {
            String expense = expenseCursor.getString(0) != null ? expenseCursor.getString(0) : "0";
            reportList.add(new IncomeExpenseModel_tien("Chi tiêu", expense + "đ"));
        } else {
            reportList.add(new IncomeExpenseModel_tien("Chi tiêu", "0đ"));
        }
        expenseCursor.close();

        // Calculate total
        if (reportList.size() >= 2) {
            double income = Double.parseDouble(reportList.get(0).getAmountText().replace("đ", "").replace(",", ""));
            double expense = Double.parseDouble(reportList.get(1).getAmountText().replace("đ", "").replace(",", ""));
            double total = income - expense;
            reportList.add(new IncomeExpenseModel_tien("Tổng", total + "đ"));
        } else {
            reportList.add(new IncomeExpenseModel_tien("Tổng", "0đ"));
        }

        db.close();
        return reportList;
    }
}
