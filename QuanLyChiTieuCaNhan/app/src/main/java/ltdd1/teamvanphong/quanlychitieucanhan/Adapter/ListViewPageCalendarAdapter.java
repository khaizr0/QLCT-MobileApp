package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.IncomeExpenseModel_nguyen;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ListViewPageCalendarAdapter extends BaseAdapter {

    private Context context;
    private List<IncomeExpenseModel_nguyen> transactions;
    private Map<Integer, CategoriesModel> categoryMap;

    public ListViewPageCalendarAdapter(Context context, List<IncomeExpenseModel_nguyen> transactions, Map<Integer, CategoriesModel> categoryMap) {
        this.context = context;
        this.transactions = transactions;
        this.categoryMap = categoryMap;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transactions.get(position).getIncomeExpenseId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_calendar, parent, false);
        }

        ImageView iconCategory = convertView.findViewById(R.id.iconCategory);
        TextView txtCategoryName = convertView.findViewById(R.id.txtCategoryName);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtAmount = convertView.findViewById(R.id.txtAmount);

        IncomeExpenseModel_nguyen transaction = transactions.get(position);
        CategoriesModel category = categoryMap.get(transaction.getCategoryId());

        if (category != null) {
            int resourceId = context.getResources().getIdentifier(category.getIconName(), "drawable", context.getPackageName());
            iconCategory.setImageResource(resourceId);
            txtCategoryName.setText(category.getCategoryName());
        }

        txtDate.setText(transaction.getDate());
        txtAmount.setText(transaction.getAmount() + "đ");

        return convertView;
    }
}

