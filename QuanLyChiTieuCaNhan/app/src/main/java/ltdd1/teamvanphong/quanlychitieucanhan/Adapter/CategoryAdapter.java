package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ltdd1.teamvanphong.quanlychitieucanhan.Model.CategoriesModel;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoriesModel> categories;
    private OnItemClickListener listener;
    private int selectedPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(CategoriesModel category);
    }

    public CategoryAdapter(List<CategoriesModel> categories, OnItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_add, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoriesModel category = categories.get(position);
        holder.textViewCategoryName.setText(category.getCategoryName());

        // Set icon with color background
        try {
            int color = Color.parseColor(category.getColor());
            holder.imageViewIcon.setColorFilter(color);
        } catch (IllegalArgumentException e) {
            Log.e("CategoryAdapter", "Invalid color code: " + category.getColor());
        }

        // Load drawable resource
        int iconResId = holder.itemView.getContext().getResources().getIdentifier(category.getIconName(), "drawable", holder.itemView.getContext().getPackageName());
        Drawable iconDrawable = ContextCompat.getDrawable(holder.itemView.getContext(), iconResId);
        if (iconDrawable != null) {
            holder.imageViewIcon.setImageDrawable(iconDrawable);
        } else {
            Log.e("CategoryAdapter", "Icon resource not found for: " + category.getIconName());
        }

        // Set background color for selected item
        if (selectedPosition == position) {
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.selected_border));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onItemClick(category);
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoryName;
        ImageView imageViewIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }
    }
}
