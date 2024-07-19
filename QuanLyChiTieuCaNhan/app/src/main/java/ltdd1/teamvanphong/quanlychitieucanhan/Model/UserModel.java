package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class UserModel {
    private static UserModel session;

    private int userId;
    private String userName;
    private String email;
    private String password;
    private int gender;
    private String phone;

    public UserModel(){

    }
    public static UserModel getSessionUser() {
        return session;
    }
    public static void setSessionUser(UserModel user) {
        session = user;
    }
    public int getUserId() {
        Log.d("getUserID", "getUserId: "+userId);
        return userId; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static UserModel checkLogin(Context context, String username, String password) {
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM User WHERE UserName=? AND PassWork=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            UserModel user = new UserModel();
            user.setUserId(cursor.getInt(0));
            user.setUserName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setGender(cursor.getInt(4));
            user.setPhone(cursor.getString(5));
            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }

    // kiểm tra tên đăng nhập và email có tồn tại hay không
    public static boolean validateUser(Context context, String username, String email) {
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM User WHERE UserName=? AND Email=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, email});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    // cập nhật mật khẩu mới
    public static boolean updatePassword(Context context, String username, String newPassword) {
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PassWork", newPassword);
        int rows = db.update("User", values, "UserName=?", new String[]{username});
        db.close();
        return rows > 0;
    }

    //Đăng kí:
    public static boolean registerUser(Context context, String username, String email, String password, int gender, String phone) {
        SQLiteOpenHelper dbHelper = new ExpenseDB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName", username);
        values.put("Email", email);
        values.put("PassWork", password);
        values.put("Gender", gender);
        values.put("Sdt", phone);

        long userId = db.insert("User", null, values);

        if (userId == -1) {
            db.close();
            return false;
        }

        addDefaultCategories(db, (int) userId);

        db.close();
        return true;
    }
    private static void addDefaultCategories(SQLiteDatabase db, int userId) {
        List<ContentValues> defaultCategories = new ArrayList<>();

        defaultCategories.add(createCategory("Y Tế", "#FF0000", "ic_cate_hospital", 0, userId));
        defaultCategories.add(createCategory("Công Việc", "#964B00", "ic_cate_work", 1, userId));
        defaultCategories.add(createCategory("Cà Phê", "#A52A2A", "ic_cate_cafe", 0, userId));

        for (ContentValues values : defaultCategories) {
            db.insert("Categories", null, values);
        }
    }
    private static ContentValues createCategory(String name, String color, String iconName, int type, int userId) {
        ContentValues values = new ContentValues();
        values.put("CategoryName", name);
        values.put("Color", color);
        values.put("IconName", iconName);
        values.put("Type", type);
        values.put("UserID", userId);
        return values;
    }
}
