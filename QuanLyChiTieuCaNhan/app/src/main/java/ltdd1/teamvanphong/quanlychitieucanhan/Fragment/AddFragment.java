package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class AddFragment extends Fragment {

    private EditText editTextDate;
    private LinearLayout linearChiTieu;
    private LinearLayout linearThuNhap;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        editTextDate = view.findViewById(R.id.editTextDate);
        linearChiTieu = view.findViewById(R.id.linearChiTieu);
        linearThuNhap = view.findViewById(R.id.linearThuNhap);

        // Mặc định chọn "Chi tiêu"
        setLinearLayoutSelected(linearChiTieu);
        setLinearLayoutUnselected(linearThuNhap);

        linearChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLinearLayoutSelected(linearChiTieu);
                setLinearLayoutUnselected(linearThuNhap);
            }
        });

        linearThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLinearLayoutSelected(linearThuNhap);
                setLinearLayoutUnselected(linearChiTieu);
            }
        });

        loadEditTextDate();
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

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
}
