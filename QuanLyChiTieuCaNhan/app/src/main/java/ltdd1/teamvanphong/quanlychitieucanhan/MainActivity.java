package ltdd1.teamvanphong.quanlychitieucanhan;


import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;
import ltdd1.teamvanphong.quanlychitieucanhan.*;

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

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, firstFragment)
                        .commit();
                return true;

            case R.id.bottom_nav_calendar:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, secondFragment)
                        .commit();
                return true;

            case R.id.bottom_nav_chartReport:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, thirdFragment)
                        .commit();
                return true;

            case R.id.bottom_nav_otherSetting:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, fourthFragment)
                        .commit();
                return true;


        }
        return false;
    }
}