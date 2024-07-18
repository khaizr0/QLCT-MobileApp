package ltdd1.teamvanphong.quanlychitieucanhan.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExpenseDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "expense.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USER = "User";
    private static final String TABLE_CATEGORIES = "Categories";
    private static final String TABLE_INCOME_EXPENSE = "IncomeExpense";
    private static final String TABLE_FIXED_INCOME_EXPENSE = "FixedIncomeExpense";

    private static final String USER_ID = "UserID";
    private static final String USER_NAME = "UserName";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "PassWork";
    private static final String GENDER = "Gender";
    private static final String PHONE = "Sdt";

    private static final String CATEGORY_ID = "CategoryID";
    private static final String CATEGORY_NAME = "CategoryName";
    private static final String COLOR = "Color";
    private static final String ICON_NAME = "IconName";
    private static final String TYPE = "Type";

    private static final String INCOME_EXPENSE_ID = "IncomeExpenseID";
    private static final String AMOUNT = "Amount";
    private static final String DATE = "Date";
    private static final String NOTE = "Note";

    private static final String FIXED_ID = "FixedID";

    public ExpenseDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT NOT NULL, "
                + EMAIL + " TEXT NOT NULL UNIQUE, "
                + PASSWORD + " TEXT NOT NULL, "
                + GENDER + " INTEGER NOT NULL, "
                + PHONE + " TEXT NOT NULL)";
        db.execSQL(createUserTable);

        String createCategoriesTable = "CREATE TABLE " + TABLE_CATEGORIES + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT NOT NULL, "
                + COLOR + " TEXT NOT NULL, "
                + ICON_NAME + " TEXT NOT NULL, "
                + TYPE + " INTEGER NOT NULL, "
                + USER_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USER + "(" + USER_ID + "))";
        db.execSQL(createCategoriesTable);

        String createIncomeExpenseTable = "CREATE TABLE " + TABLE_INCOME_EXPENSE + " ("
                + INCOME_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE + " INTEGER NOT NULL, "
                + AMOUNT + " TEXT NOT NULL, "
                + DATE + " TEXT NOT NULL, "
                + NOTE + " TEXT, "
                + USER_ID + " INTEGER NOT NULL, "
                + CATEGORY_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + USER_ID + ") REFERENCES " + TABLE_USER + "(" + USER_ID + "), "
                + "FOREIGN KEY(" + CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + CATEGORY_ID + "))";
        db.execSQL(createIncomeExpenseTable);

        String createFixedIncomeExpenseTable = "CREATE TABLE " + TABLE_FIXED_INCOME_EXPENSE + " ("
                + FIXED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT NOT NULL, "
                + INCOME_EXPENSE_ID + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + INCOME_EXPENSE_ID + ") REFERENCES " + TABLE_INCOME_EXPENSE + "(" + INCOME_EXPENSE_ID + "))";
        db.execSQL(createFixedIncomeExpenseTable);

        initData(db);
    }

    private void initData(SQLiteDatabase db) {
        ContentValues userValues = new ContentValues();
        userValues.put(USER_NAME, "123");
        userValues.put(EMAIL, "123@example.com");
        userValues.put(PASSWORD, "123");
        userValues.put(GENDER, 1);  // Giả sử 1 là nam
        userValues.put(PHONE, "1234567890");
        long userId = db.insert(TABLE_USER, null, userValues);

        String[] categoryNames = {"Bệnh Viện", "Làm Bác Sĩ", "Phím"};
        String[] iconNames = {"ic_cate_hospital", "ic_cate_work", "ic_cate_cafe"};
        String[] colors = {"#FFFFFF", "#FF0000", "#00FF00"};
        int[] types = {0, 1, 0};

        for (int i = 0; i < categoryNames.length; i++) {
            ContentValues categoryValues = new ContentValues();
            categoryValues.put(CATEGORY_NAME, categoryNames[i]);
            categoryValues.put(COLOR, colors[i]);
            categoryValues.put(ICON_NAME, iconNames[i]);
            categoryValues.put(TYPE, types[i]);
            categoryValues.put(USER_ID, userId);
            db.insert(TABLE_CATEGORIES, null, categoryValues);
        }

        ContentValues incomeExpenseValues = new ContentValues();
        incomeExpenseValues.put(TYPE, 1);  // Giả sử 1 là thu nhập
        incomeExpenseValues.put(AMOUNT, "5000");
        incomeExpenseValues.put(DATE, "2024-07-07");
        incomeExpenseValues.put(NOTE, "Lương tháng 1");
        incomeExpenseValues.put(USER_ID, userId);
        incomeExpenseValues.put(CATEGORY_ID, 1); // Giả sử ID của category là 1
        long incomeExpenseId = db.insert(TABLE_INCOME_EXPENSE, null, incomeExpenseValues);

        ContentValues fixedIncomeExpenseValues = new ContentValues();
        fixedIncomeExpenseValues.put(DATE, "2024-01-01");
        fixedIncomeExpenseValues.put(INCOME_EXPENSE_ID, incomeExpenseId);
        db.insert(TABLE_FIXED_INCOME_EXPENSE, null, fixedIncomeExpenseValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIXED_INCOME_EXPENSE);
        onCreate(db);
    }

    public void printAllIncomeExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME_EXPENSE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(INCOME_EXPENSE_ID));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE));
                String amount = cursor.getString(cursor.getColumnIndexOrThrow(AMOUNT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(NOTE));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_ID));

                Log.d("IncomeExpense", "ID: " + id + ", Type: " + type + ", Amount: " + amount +
                        ", Date: " + date + ", Note: " + note + ", UserID: " + userId + ", CategoryID: " + categoryId);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    // Public static methods to access private fields
    public static String getTableIncomeExpense() {
        return TABLE_INCOME_EXPENSE;
    }

    public static String getAmount() {
        return AMOUNT;
    }

    public static String getDate() {
        return DATE;
    }

    public static String getType() {
        return TYPE;
    }

}
