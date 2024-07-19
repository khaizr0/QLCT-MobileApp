package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.ExpenseItem;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ReportChartMonthListViewAdapter extends ArrayAdapter<ExpenseItem> {

    private Context mContext;
    private List<ExpenseItem> expenseList;
    private Map<String, CategoriesModel> categoryMap;

    public ReportChartMonthListViewAdapter(@NonNull Context context, @NonNull List<ExpenseItem> objects, @NonNull Map<String, CategoriesModel> categoryMap) {
        super(context, 0, objects);
        this.mContext = context;
        this.expenseList = objects;
        this.categoryMap = categoryMap;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_expense, parent, false);
        }

        ExpenseItem expenseItem = expenseList.get(position);

        ImageView categoryIcon = convertView.findViewById(R.id.imgIcon);
        TextView categoryName = convertView.findViewById(R.id.tvCategoryName);
        TextView amountSpent = convertView.findViewById(R.id.tvAmount);

        // Lấy CategoriesModel dựa trên tên danh mục của ExpenseItem
        CategoriesModel category = categoryMap.get(expenseItem.getCategoryName());

        if (category != null) {
            int iconResId = mContext.getResources().getIdentifier(category.getIconName(), "drawable", mContext.getPackageName());
            Drawable iconDrawable = ContextCompat.getDrawable(mContext, iconResId);
            if (iconDrawable != null) {
                int color = Color.parseColor(category.getColor());
                iconDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                categoryIcon.setImageDrawable(iconDrawable);
            }

            categoryName.setText(category.getCategoryName());
        } else {
            // Xử lý trường hợp không tìm thấy category tương ứng
            categoryIcon.setImageResource(R.drawable.baseline_dehaze_24); // Set icon mặc định nếu cần
            categoryName.setText("name not found"); // Set text mặc định nếu cần
        }

        amountSpent.setText(expenseItem.getAmountSpent());

        return convertView;
    }
}
