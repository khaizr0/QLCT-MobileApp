package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.IconAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ThemDanhMuc extends AppCompatActivity {
    private RecyclerView iconRecyclerView;
    private IconAdapter iconAdapter;
    private List<Integer> iconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_muc);

        iconRecyclerView = findViewById(R.id.icon_recycler_view);
        
        iconList = new ArrayList<>();
        iconList.add(R.drawable.ic_next);
        iconList.add(R.drawable.ic_calendar);

        iconAdapter = new IconAdapter(iconList);
        iconRecyclerView.setAdapter(iconAdapter);
    }
}