package ltdd1.teamvanphong.quanlychitieucanhan.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;

public class UserModel {
    private int userId;
    private String userName;
    private String email;
    private String password;
    private int gender;
    private String phone;

    // Getters and setters
    public int getUserId() {
        return userId;
    }

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
            user.setUserId(cursor.getInt(cursor.getColumnIndex("UserID")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("PassWork")));
            user.setGender(cursor.getInt(cursor.getColumnIndex("Gender")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("Sdt")));
            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }
}
