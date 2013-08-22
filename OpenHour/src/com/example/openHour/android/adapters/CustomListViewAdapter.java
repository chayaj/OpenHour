package com.example.openHour.android.adapters;

/**
 * Created with IntelliJ IDEA.
 * User: JessicaC
 * Date: 8/15/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

import com.example.openHour.android.R;
import com.example.openHour.android.components.Business;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<Business> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<Business> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Business rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getPhone());
        holder.txtTitle.setText(rowItem.getName());
        //holder.imageView.setImageResource(Uri.parse(rowItem.getImageUrl()));
        holder.imageView.setImageResource(R.drawable.yelp_logo);

        return convertView;
    }
}