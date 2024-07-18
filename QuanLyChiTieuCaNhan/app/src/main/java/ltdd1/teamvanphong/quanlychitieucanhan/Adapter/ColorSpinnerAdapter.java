package ltdd1.teamvanphong.quanlychitieucanhan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ltdd1.teamvanphong.quanlychitieucanhan.Model.ColorItem;
import ltdd1.teamvanphong.quanlychitieucanhan.R;

public class ColorSpinnerAdapter extends ArrayAdapter<ColorItem> {

    public ColorSpinnerAdapter(Context context, List<ColorItem> colors) {
        super(context, 0, colors);
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

        ImageView imageViewColor = convertView.findViewById(R.id.icon);
        TextView textViewName = convertView.findViewById(R.id.text);

        ColorItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewColor.setBackgroundColor(Color.parseColor(currentItem.getColorHex()));
            textViewName.setText(currentItem.getName());
        }

        return convertView;
    }
}

