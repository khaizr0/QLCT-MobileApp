package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class CategoriesModel {

    // Các thuộc tính của danh mục
    private int categoryId;
    private String categoryName;
    private String color;
    private String iconName;
    private int type;
    private int userId;

    private SQLiteDatabase database;
    private IncomeExpenseModel_nguyen incomeExpenseModel;

    public CategoriesModel() {}

    public CategoriesModel(Context context) {
        ExpenseDB dbHelper = new ExpenseDB(context);
        this.database = dbHelper.getWritableDatabase();
        this.incomeExpenseModel = new IncomeExpenseModel_nguyen(database);
    }

    // Getters và setters
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getIconName() { return iconName; }
    public void setIconName(String iconName) { this.iconName = iconName; }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Map<Integer, CategoriesModel> getAllCategoriesAsMap() {
        Map<Integer, CategoriesModel> categoryMap = new HashMap<>();
        String query = "SELECT * FROM Categories";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CategoriesModel category = new CategoriesModel();
                category.setCategoryId(cursor.getInt(cursor.getColumnIndex("categoryId")));
                category.setCategoryName(cursor.getString(cursor.getColumnIndex("categoryName")));
                category.setIconName(cursor.getString(cursor.getColumnIndex("iconName")));
                category.setColor(cursor.getString(cursor.getColumnIndex("color")));
                categoryMap.put(category.getCategoryId(), category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryMap;
    }
    public CategoriesModel getCategoryByName(String categoryName) {
        String query = "SELECT * FROM Categories WHERE CategoryName = ?";
        Cursor cursor = database.rawQuery(query, new String[]{categoryName});

        if (cursor.moveToFirst()) {
            CategoriesModel category = new CategoriesModel();
            category.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryID")));
            category.setCategoryName(cursor.getString(cursor.getColumnIndex("CategoryName")));
            category.setColor(cursor.getString(cursor.getColumnIndex("Color")));
            category.setIconName(cursor.getString(cursor.getColumnIndex("IconName")));
            category.setType(cursor.getInt(cursor.getColumnIndex("Type")));
            category.setUserId(cursor.getInt(cursor.getColumnIndex("UserID")));
            cursor.close();
            return category;
        }

        cursor.close();
        return null;
    }

    public List<Map<String, String>> getExpenseOrIncomeCategoriesForUser_ReportChartMonth(int userId, int month, int year, int type) {
        List<Map<String, String>> categories = new ArrayList<>();
        String startDate;
        String endDate;

        if (month == 0) {
            startDate = String.format("%04d-01-01", year);
            endDate = String.format("%04d-12-31", year);
        } else {
            startDate = String.format("%04d-%02d-01", year, month);
            endDate = String.format("%04d-%02d-31", year, month);
        }

        String query = "SELECT C.CategoryName, C.IconName " +
                "FROM IncomeExpense IE " +
                "JOIN Categories C ON IE.CategoryID = C.CategoryID " +
                "WHERE IE.UserID = ? AND IE.Type = ? AND IE.Date BETWEEN ? AND ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(type), startDate, endDate});

        if (cursor.moveToFirst()) {
            do {
                Map<String, String> category = new HashMap<>();
                category.put("categoryName", cursor.getString(0));
                category.put("iconName", cursor.getString(1));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }


    public List<String> getExpenseOrIncomeCategoriesForUser(int userId, int month, int year, int type) {
        List<String> categories = new ArrayList<>();
        String startDate;
        String endDate;

        if (month == 0) {
            startDate = String.format("%04d-01-01", year);
            endDate = String.format("%04d-12-31", year);
        } else {
            startDate = String.format("%04d-%02d-01", year, month);
            endDate = String.format("%04d-%02d-31", year, month);
        }

        String query = "SELECT DISTINCT C.CategoryName " +
                "FROM IncomeExpense IE " +
                "JOIN Categories C ON IE.CategoryID = C.CategoryID " +
                "WHERE IE.UserID = ? AND IE.Type = ? AND IE.Date BETWEEN ? AND ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId),String.valueOf(type), startDate, endDate});

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public static List<CategoriesModel> getCategoriesByTypeAndUserId(Context context, int type) {
        List<CategoriesModel> categoryList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Lấy userId từ session
        UserModel session = UserModel.getSessionUser();
        int userId = session.getUserId();

        Cursor cursor = db.query("Categories", null, "Type=? AND UserID=?", new String[]{String.valueOf(type), String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                CategoriesModel category = new CategoriesModel();
                category.setCategoryId(cursor.getInt(cursor.getColumnIndex("CategoryID")));
                category.setCategoryName(cursor.getString(cursor.getColumnIndex("CategoryName")));
                category.setColor(cursor.getString(cursor.getColumnIndex("Color")));
                category.setIconName(cursor.getString(cursor.getColumnIndex("IconName")));
                category.setType(cursor.getInt(cursor.getColumnIndex("Type")));
                category.setUserId(cursor.getInt(cursor.getColumnIndex("UserID")));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }
}
