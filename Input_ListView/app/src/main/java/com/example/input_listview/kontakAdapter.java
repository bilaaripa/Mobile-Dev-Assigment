package com.example.input_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class kontakAdapter extends ArrayAdapter<kontak> {

    private static class ViewHolder {
        TextView nama;
        TextView nohp;
        Button btn;
        ImageView img;
    }

    public kontakAdapter(Context context, int resource, List<kontak> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        kontak dtkontak = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            viewHolder.nama = convertView.findViewById(R.id.tNama);
            viewHolder.nohp = convertView.findViewById(R.id.tnoHp);
            viewHolder.btn = convertView.findViewById(R.id.btn);
            viewHolder.img = convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nama.setText(dtkontak.getNama());
        viewHolder.nohp.setText(dtkontak.getNoHp());
        viewHolder.btn.setTag(position);
        viewHolder.btn.setOnClickListener(op);

        // Set image resource for ImageView
        viewHolder.img.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    private final View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Handle button click here
        }
    };
}
