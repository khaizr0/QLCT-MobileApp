package ltdd1.teamvanphong.quanlychitieucanhan.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                + TYPE + " TEXT NOT NULL, "
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

        String[] categoryNames = {"baseline_add_box_24", "baseline_calendar_month_24", "baseline_currency_exchange_24"};
        String[] colors = {"#FFFFFF", "#FF0000", "#00FF00"};
        int[] types = {0, 1, 0};

        for (int i = 0; i < categoryNames.length; i++) {
            ContentValues categoryValues = new ContentValues();
            categoryValues.put(CATEGORY_NAME, categoryNames[i]);
            categoryValues.put(COLOR, colors[i]);
            categoryValues.put(ICON_NAME, categoryNames[i]);
            categoryValues.put(TYPE, types[i]);
            categoryValues.put(USER_ID, userId);
            db.insert(TABLE_CATEGORIES, null, categoryValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIXED_INCOME_EXPENSE);
        onCreate(db);
    }
}
