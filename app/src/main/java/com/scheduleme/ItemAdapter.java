package com.scheduleme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mauricio on 12/4/16.
 */

public class ItemAdapter extends BaseAdapter {
    private ArrayList<String> container = null;
    private Context context = null;
    private int layout = 0;
    public ItemAdapter(Context context, int layout) {
        container = new ArrayList<>();
        this.context = context;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return container.size();
    }

    @Override
    public Object getItem(int i) {
        return container.get(i);
    }

    @Override
    public long getItemId(int i) {
        return container.get(i).hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        String contents = container.get(i);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, viewGroup, false);
        }
        TextView viewText = (TextView) convertView.findViewById(R.id.line);
        viewText.setText(contents);
        return convertView;
    }

    public void add(String element) {
        container.add(element);
        notifyDataSetChanged();
    }

    public boolean contains (String element) {
        return container.contains(element);
    }

    public void sort () {
        Collections.sort(container);
        notifyDataSetChanged();
    }
}
