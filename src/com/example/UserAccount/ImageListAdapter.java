package com.example.UserAccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ImageListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ImageListAdapter(Context context) {
        super(context, R.layout.dashboard, Dashboard.features);
        this.context = context;
        this.values = Dashboard.features;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.dashboard, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("Emails")) {
            imageView.setImageResource(R.drawable.emails);
        } else if (s.equals("Contacts")) {
            imageView.setImageResource(R.drawable.contacts);
        } else {
            imageView.setImageResource(R.drawable.message);
        }

        return rowView;
    }
}