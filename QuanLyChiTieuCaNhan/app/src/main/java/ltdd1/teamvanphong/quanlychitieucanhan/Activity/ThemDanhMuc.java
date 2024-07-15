package ltdd1.teamvanphong.quanlychitieucanhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Activity.Adapter.IconAdapter;
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