package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ltdd1.teamvanphong.quanlychitieucanhan.Activity.DanhMuc;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class OtherSettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_setting, container, false);

        LinearLayout layoutInfo = view.findViewById(R.id.layout_info);
        LinearLayout layoutExpense = view.findViewById(R.id.layout_expense);
        LinearLayout layoutCategory = view.findViewById(R.id.layout_category);
        LinearLayout layoutReportFull = view.findViewById(R.id.layout_report_full);
        LinearLayout layoutReportYear = view.findViewById(R.id.layout_report_year);
        LinearLayout layoutReportBalance = view.findViewById(R.id.layout_report_balance);
        LinearLayout layoutExport = view.findViewById(R.id.layout_export);
        LinearLayout layoutLogout = view.findViewById(R.id.layout_logout);

        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new Other_info());
            }
        });
        layoutReportFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new Other_report_full());
            }
        });
        layoutReportBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new Other_report_balance());
            }
        });
        layoutReportYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new Other_report_inYear());
            }
        });
        layoutExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragment(new Other_xuat_data());
            }
        });
        layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhMuc.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}