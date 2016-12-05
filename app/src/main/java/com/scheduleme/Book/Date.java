package com.scheduleme.Book;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scheduleme.ItemAdapter;
import com.scheduleme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mauricio on 12/4/16.
 */

public class Date extends Fragment {
    private ListView listView = null;
    private ItemAdapter itemAdapter = null;
    private JSONArray data = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView subheader = (TextView) view.findViewById(R.id.subheader);
        subheader.setText("What Day were you thinking this week?");
        getActivity().setTitle("Book An Appointment");

        itemAdapter = new ItemAdapter(getActivity(), R.layout.entry_book);
        listView = (ListView) getActivity().findViewById(R.id.book);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // data is based on item clicked.
                Fragment f = new Time();
                String day = (String) itemAdapter.getItem(i);
                try {
                    Bundle b = new Bundle();
                    ArrayList<JSONObject> objs = new ArrayList<JSONObject>();
                    for (int index = 0; index < data.length(); index++) {
                        JSONObject jsonObject = data.getJSONObject(index);
                        JSONObject availability = jsonObject.getJSONObject("Availability");
                        JSONArray days = availability.getJSONArray("Days");
                        for (int j = 0; j < days.length(); j++) {
                            String currentDay = days.getString(i);
                            if (day.equals(currentDay) && !objs.contains(jsonObject)) {
                                objs.add(jsonObject);
                            }
                        }
                    }
                    JSONArray places = new JSONArray();
                    for (JSONObject j : objs) {
                        places.put(j);
                    }
                    b.putString("places", places.toString());
                    f.setArguments(b);
                } catch (Exception e) {

                }

                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.slide_in_right,
                                R.animator.slide_out_left,
                                R.animator.slide_in_left,
                                R.animator.slide_out_right)
                        .replace(R.id.fragment_container, f)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bundle b = getArguments();
        if (b != null) {
            try {
                data = new JSONArray(b.getString("places"));
                for (int index = 0; index < data.length(); index++) {
                    JSONObject jsonObject = data.getJSONObject(index);
                    JSONArray days = jsonObject.getJSONObject("Availability").getJSONArray("Days");
                    for (int j = 0; j < days.length(); j++) {
                        String day = days.getString(j);
                        if (!itemAdapter.contains(day)) {
                            itemAdapter.add(day);
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}
