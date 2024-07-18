package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Activity.SuaDanhMuc;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoriesModel> categoryList;

    public CategoriesAdapter(Context context, List<CategoriesModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoriesModel category = categoryList.get(position);
        holder.categoryName.setText(category.getCategoryName());

        // Set icon with color background
        int color = android.graphics.Color.parseColor(category.getColor());
        holder.icon.setColorFilter(color);

        // Load drawable resource
        int iconResId = context.getResources().getIdentifier(category.getIconName(), "drawable", context.getPackageName());
        Drawable iconDrawable = ContextCompat.getDrawable(context, iconResId);
        holder.icon.setImageDrawable(iconDrawable);

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to send data to SuaDanhMuc activity
                Intent intent = new Intent(context, SuaDanhMuc.class);
                intent.putExtra("CATEGORY_ID", category.getCategoryId());
                intent.putExtra("CATEGORY_NAME", category.getCategoryName());
                intent.putExtra("CATEGORY_ICON", category.getIconName());
                intent.putExtra("CATEGORY_COLOR", category.getColor());
                intent.putExtra("CATEGORY_TYPE", category.getType());
                intent.putExtra("USER_ID", category.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategoryList(List<CategoriesModel> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView icon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
