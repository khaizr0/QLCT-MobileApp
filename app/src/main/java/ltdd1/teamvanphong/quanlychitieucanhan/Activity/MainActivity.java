package ltdd1.teamvanphong.quanlychitieucanhan.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ltdd1.teamvanphong.quanlychitieucanhan.*;
import ltdd1.teamvanphong.quanlychitieucanhan.Fragment.AddFragment;
import ltdd1.teamvanphong.quanlychitieucanhan.Fragment.CalendarFragment;
import ltdd1.teamvanphong.quanlychitieucanhan.Fragment.OtherSettingFragment;
import ltdd1.teamvanphong.quanlychitieucanhan.Fragment.ReportChartFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }
    AddFragment firstFragment = new AddFragment();
    CalendarFragment secondFragment = new CalendarFragment();
    ReportChartFragment thirdFragment = new ReportChartFragment();
    OtherSettingFragment fourthFragment = new OtherSettingFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.bottom_nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, firstFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.bottom_nav_calendar) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, secondFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.bottom_nav_chartReport) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, thirdFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.bottom_nav_otherSetting) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fourthFragment)
                    .commit();
            return true;
        }
        return false;
    }

}