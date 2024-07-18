package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class CategoriesModel {

    // Các thuộc tính của danh mục
    private int categoryId;
    private String categoryName;
    private String color;
    private String iconName;
    private int type;
    private int userId;

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

    // Hàm lấy danh sách danh mục từ database theo Type và UserId
    public static List<CategoriesModel> getCategoriesByTypeAndUserId(Context context, int type, int userId) {
        List<CategoriesModel> categoryList = new ArrayList<>();
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
