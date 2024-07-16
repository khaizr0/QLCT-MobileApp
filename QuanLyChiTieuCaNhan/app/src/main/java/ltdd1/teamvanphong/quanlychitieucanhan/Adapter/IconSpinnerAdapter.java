package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Activity.IconItem;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class IconSpinnerAdapter extends ArrayAdapter<IconItem> {

    public IconSpinnerAdapter(Context context, List<IconItem> icons) {
        super(context, 0, icons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.icon);
        TextView textViewName = convertView.findViewById(R.id.text);

        IconItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewIcon.setImageResource(currentItem.getIconResId());
            textViewName.setText(currentItem.getName());
        }

        return convertView;
    }
}

