package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CalendarDay;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<CalendarDay> days = new ArrayList<>();

    public CalendarAdapter(List<CalendarDay> days) {
        this.days = days;
    }

    public void setDays(List<CalendarDay> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarDay day = days.get(position);
        holder.dayText.setText(day.getDay());
        holder.data1Text.setText(day.getIncome());
        holder.data2Text.setText(day.getExpense());

        if (!day.getDay().isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.getDay()));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == Calendar.SATURDAY) {
                holder.dayText.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_dark));
            } else if (dayOfWeek == Calendar.SUNDAY) {
                holder.dayText.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
            } else {
                holder.dayText.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, data1Text, data2Text;

        CalendarViewHolder(View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            data1Text = itemView.findViewById(R.id.data1Text);
            data2Text = itemView.findViewById(R.id.data2Text);
        }
    }
}
