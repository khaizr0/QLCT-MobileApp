package ltdd1.teamvanphong.quanlychitieucanhan.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ltdd1.teamvanphong.quanlychitieucanhan.Adapter.CalendarAdapter;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CalendarDay;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class CalendarFragment extends Fragment {

    private Spinner monthSpinner;
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;
    private Calendar calendar = Calendar.getInstance();
    private int currentYear = calendar.get(Calendar.YEAR);
    private int currentMonth = calendar.get(Calendar.MONTH) + 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        monthSpinner = view.findViewById(R.id.monthSpinner);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);

        setupMonthSpinner();
        setupCalendarRecyclerView();

        updateCalendar();

        return view;
    }

    private void setupMonthSpinner() {

        List<String> dateOptions = new ArrayList<>();
        for (int year = 2000; year <= currentYear + 50; year++) {
            for (int month = 1; month <= 12; month++) {
                dateOptions.add(String.format("%02d/%04d", month + 1, year));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dateOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        String currentMonthYear = String.format("%02d/%04d", currentMonth , currentYear);
        int currentIndex = dateOptions.indexOf(currentMonthYear);
        if (currentIndex != -1) {
            monthSpinner.setSelection(currentIndex);
        }
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupCalendarRecyclerView() {
        calendarAdapter = new CalendarAdapter(new ArrayList<>());
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void updateCalendar() {
        String selectedDate = (String) monthSpinner.getSelectedItem();
        String[] parts = selectedDate.split("/");
        int month = Integer.parseInt(parts[0]) - 1; // Month is 0-based in Calendar
        int year = Integer.parseInt(parts[1]);

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<CalendarDay> days = new ArrayList<>();
        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add(new CalendarDay("", "", ""));
        }
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(new CalendarDay(String.valueOf(i), "Data 1", "Data 2"));
        }

        calendarAdapter.setDays(days);
    }
}
