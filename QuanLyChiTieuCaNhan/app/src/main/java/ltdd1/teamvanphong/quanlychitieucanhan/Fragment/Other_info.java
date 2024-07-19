package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ltdd1.teamvanphong.quanlychitieucanhan.Database.ExpenseDB;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class Other_info extends Fragment {
    private ImageButton icBack;  // Đổi từ ImageView thành ImageButton để khớp với XML

    public Other_info() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_other_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize database and print all income and expenses (hidden function)
        ExpenseDB db = new ExpenseDB(getContext());
//        db.printAllIncomeExpense();
//        db.printAllCategories();

        // Find the back button
        icBack = view.findViewById(R.id.btn_back);

        // Set up the back button click listener
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the fragment from the back stack
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
