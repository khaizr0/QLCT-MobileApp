package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartMonthly extends Fragment {

    private LinearLayout layoutChiTieu;
    private LinearLayout layoutThuNhap;

    public ReportChartMonthly() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_chart_monthly, container, false);

        layoutChiTieu = view.findViewById(R.id.layout_chi_tieu);
        layoutThuNhap = view.findViewById(R.id.layout_thu_nhap);

        // Xử lý sự kiện khi chọn Chi tiêu
        layoutChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChiTieu.setSelected(true);
                layoutThuNhap.setSelected(false);
                Toast.makeText(getContext(), "Đã chọn Chi tiêu", Toast.LENGTH_SHORT).show();
                // Xử lý logic khi chọn Chi tiêu
            }
        });

        // Xử lý sự kiện khi chọn Thu nhập
        layoutThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutChiTieu.setSelected(false);
                layoutThuNhap.setSelected(true);
                Toast.makeText(getContext(), "Đã chọn Thu nhập", Toast.LENGTH_SHORT).show();
                // Xử lý logic khi chọn Thu nhập
            }
        });

        return view;
    }
}
