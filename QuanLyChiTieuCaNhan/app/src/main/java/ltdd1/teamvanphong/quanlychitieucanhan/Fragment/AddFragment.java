package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.CategoryAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_khai;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class AddFragment extends Fragment {

    private EditText editTextDate, editTextNote, editTextAmount;
    private LinearLayout linearChiTieu, linearThuNhap;
    private RecyclerView recyclerViewCategory;
    private CategoryAdapter categoryAdapter;
    private int selectedType = 0; // 0: Chi tiêu, 1: Thu nhập
    private int selectedCategoryId = -1;
    private int userId;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        UserModel session = UserModel.getSessionUser();
        userId = session.getUserId();

        editTextDate = view.findViewById(R.id.editTextDate);
        editTextNote = view.findViewById(R.id.editTextNote);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        linearChiTieu = view.findViewById(R.id.linearChiTieu);
        linearThuNhap = view.findViewById(R.id.linearThuNhap);
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);

        setLinearLayoutSelected(linearChiTieu);
        setLinearLayoutUnselected(linearThuNhap);

        linearChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedType = 0;
                setLinearLayoutSelected(linearChiTieu);
                setLinearLayoutUnselected(linearThuNhap);
                loadCategories();
            }
        });

        linearThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedType = 1;
                setLinearLayoutSelected(linearThuNhap);
                setLinearLayoutUnselected(linearChiTieu);
                loadCategories();
            }
        });

        loadEditTextDate();
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        view.findViewById(R.id.buttonEnterExpense).setOnClickListener(v -> saveIncomeExpense());

        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        loadCategories();

        return view;
    }

    private void setLinearLayoutSelected(LinearLayout linearLayout) {
        linearLayout.setBackgroundTintList(getResources().getColorStateList(R.color.selectedColor, null));
    }

    private void setLinearLayoutUnselected(LinearLayout linearLayout) {
        linearLayout.setBackgroundTintList(getResources().getColorStateList(R.color.unselectedColor, null));
    }

    private void loadEditTextDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        editTextDate.setText(currentDate);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void loadCategories() {
        List<CategoriesModel> categories = CategoriesModel.getCategoriesByTypeAndUserId(getContext(), selectedType);
        categoryAdapter = new CategoryAdapter(categories, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoriesModel category) {
                selectedCategoryId = category.getCategoryId();
            }
        });
        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void saveIncomeExpense() {
        String date = editTextDate.getText().toString();
        String note = editTextNote.getText().toString();
        String amount = editTextAmount.getText().toString();

        if (note.isEmpty()) {
            note = "";
        }

        if (date.isEmpty() || amount.isEmpty() || selectedCategoryId == -1) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        IncomeExpenseModel_khai incomeExpense = new IncomeExpenseModel_khai();
        incomeExpense.setType(selectedType);
        incomeExpense.setDate(date);
        incomeExpense.setNote(note);
        incomeExpense.setAmount(amount);
        incomeExpense.setUserId(userId);
        incomeExpense.setCategoryId(selectedCategoryId);

        saveIncomeExpenseToDatabase(incomeExpense);

        Toast.makeText(getContext(), "Đã lưu thông tin", Toast.LENGTH_SHORT).show();
    }

    private void saveIncomeExpenseToDatabase(IncomeExpenseModel_khai incomeExpense) {
        ExpenseDB dbHelper = new ExpenseDB(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Type", incomeExpense.getType());
        values.put("Amount", incomeExpense.getAmount());
        values.put("Date", incomeExpense.getDate());
        values.put("Note", incomeExpense.getNote());
        values.put("UserId", incomeExpense.getUserId());
        values.put("CategoryId", incomeExpense.getCategoryId());

        // Insert into database
        long newRowId = db.insert("IncomeExpense", null, values);

        // Debug
        Log.d("SaveExpense", "New row ID: " + newRowId);
        Log.d("SaveExpense", "Type: " + incomeExpense.getType());
        Log.d("SaveExpense", "Amount: " + incomeExpense.getAmount());
        Log.d("SaveExpense", "Date: " + incomeExpense.getDate());
        Log.d("SaveExpense", "Note: " + incomeExpense.getNote());
        Log.d("SaveExpense", "UserId: " + incomeExpense.getUserId());
        Log.d("SaveExpense", "CategoryId: " + incomeExpense.getCategoryId());

        db.close();
    }
}
