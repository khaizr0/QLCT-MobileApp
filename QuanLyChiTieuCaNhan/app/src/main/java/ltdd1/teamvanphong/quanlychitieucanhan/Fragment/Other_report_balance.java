package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Calendar;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class Other_report_balance extends Fragment {

    LineChart lineChart;
    TextView[] monthTextViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_report_balance, container, false);

        // Initialize the toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);  // Remove default title

        lineChart = view.findViewById(R.id.lineChart);

        monthTextViews = new TextView[]{
                view.findViewById(R.id.thang1_sodu),
                view.findViewById(R.id.thang2_sodu),
                view.findViewById(R.id.thang3_sodu),
                view.findViewById(R.id.thang4_sodu),
                view.findViewById(R.id.thang5_sodu),
                view.findViewById(R.id.thang6_sodu),
                view.findViewById(R.id.thang7_sodu),
                view.findViewById(R.id.thang8_sodu),
                view.findViewById(R.id.thang9_sodu),
                view.findViewById(R.id.thang10_sodu),
                view.findViewById(R.id.thang11_sodu),
                view.findViewById(R.id.thang12_sodu)
        };

        // Set click listener for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // Set the custom title
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Báo cáo thay đổi số dư");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner yearSpinner = view.findViewById(R.id.yearSpinner);
        ArrayList<String> years = new ArrayList<>();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear + 50; i >= 2000; i--) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setSelection(adapter.getPosition(String.valueOf(currentYear)));

    }
}