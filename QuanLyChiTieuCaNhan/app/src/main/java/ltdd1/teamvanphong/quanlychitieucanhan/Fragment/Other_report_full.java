package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_tien;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.UserModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class Other_report_full extends Fragment {

    private TextView incomeTextView, expenseTextView, totalTextView;

    public Other_report_full() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_report_full, container, false);

        // Initialize the toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);  // Remove default title

        // Set click listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // Set the custom title
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Báo cáo toàn kì");

        // Initialize TextViews
        incomeTextView = view.findViewById(R.id.incomeTextView);
        expenseTextView = view.findViewById(R.id.expenseTextView);
        totalTextView = view.findViewById(R.id.totalTextView);

        // Get user data from session
        UserModel user = getUserFromSession();
        if (user != null) {
            // Fetch and display report data
            displayReportData(user.getUserId());
        } else {
            // Log error
            Log.e("Other_report_full", "User data not found in session");
        }

        return view;
    }

    private void displayReportData(int userId) {
        List<IncomeExpenseModel_tien> reportList = IncomeExpenseModel_tien.generateReportForUser(getContext(), userId);

        if (reportList != null && !reportList.isEmpty()) {
            incomeTextView.setText(reportList.get(0).getAmountText());
            expenseTextView.setText(reportList.get(1).getAmountText());
            totalTextView.setText(reportList.get(2).getAmountText());
        } else {
            // Log error
            Log.e("Other_report_full", "Report data is empty or null");
        }
    }

    private UserModel getUserFromSession() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        String username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);
        int gender = sharedPreferences.getInt("gender", -1);
        String phone = sharedPreferences.getString("phone", null);

        if (userId != -1 && username != null && email != null && password != null && gender != -1 && phone != null) {
            UserModel user = new UserModel();
            user.setUserId(userId);
            user.setUserName(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setGender(gender);
            user.setPhone(phone);
            return user;
        } else {
            return null;
        }
    }
}
