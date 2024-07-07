package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartMonthly extends Fragment {
    public ReportChartMonthly() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_chart_monthly, container, false);
    }
}