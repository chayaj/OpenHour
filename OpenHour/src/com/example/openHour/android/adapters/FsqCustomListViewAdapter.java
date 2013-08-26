package com.example.openHour.android.adapters;

/**
 * Created with IntelliJ IDEA.
 * User: JessicaC
 * Date: 8/15/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.openHour.android.R;
import com.example.openHour.android.components.Business;
import com.example.openHour.android.components.FsqVenue;

import java.util.List;

public class FsqCustomListViewAdapter extends ArrayAdapter<FsqVenue> {

    Context context;

    public FsqCustomListViewAdapter(Context context, int resourceId,
                                    List<FsqVenue> items) {
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
        FsqVenue rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtDesc.setText(rowItem.getAddress());
        holder.txtTitle.setText(rowItem.getName());
        //holder.imageView.setImageResource(Uri.parse(rowItem.getImageUrl()));
        holder.imageView.setImageResource(R.drawable.foursquare_logo);

        return convertView;
    }
}