package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_nguyen;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ExpenseListAdapter extends BaseAdapter {

    private Context context;
    private List<IncomeExpenseModel_nguyen> expenses;
    private Map<Integer, CategoriesModel> categoryMap;

    public ExpenseListAdapter(Context context, List<IncomeExpenseModel_nguyen> expenses, Map<Integer, CategoriesModel> categoryMap) {
        this.context = context;
        this.expenses = expenses;
        this.categoryMap = categoryMap;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return expenses.get(position).getIncomeExpenseId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_expense, parent, false);
        }

        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);
        TextView tvCategoryName = convertView.findViewById(R.id.tvCategoryName);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);

        IncomeExpenseModel_nguyen expense = expenses.get(position);
        CategoriesModel category = categoryMap.get(expense.getCategoryId());

        if (category != null) {
            int iconResId = context.getResources().getIdentifier(category.getIconName(), "drawable", context.getPackageName());
            Drawable iconDrawable = ContextCompat.getDrawable(context, iconResId);
            if (iconDrawable != null) {
                iconDrawable.setColorFilter(Color.parseColor(category.getColor()), PorterDuff.Mode.SRC_IN);
                imgIcon.setImageDrawable(iconDrawable);
            }
            tvCategoryName.setText(category.getCategoryName());
        }

        tvAmount.setText(expense.getAmount() + "đ");

        return convertView;
    }
}
