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

/**
 * Created by mauricio on 12/4/16.
 */

public class BookDoctor extends Fragment {
    private ItemAdapter itemAdapter = null;
    private ListView listView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView subheader = (TextView) view.findViewById(R.id.subheader);
        subheader.setText("What maximum price were you thinking of?");
        getActivity().setTitle("Book An Appointment");

        itemAdapter = new ItemAdapter(getActivity(), R.layout.entry_book);
        listView = (ListView) getActivity().findViewById(R.id.book);
        listView.setAdapter(itemAdapter);

        Bundle b = getArguments();
        if (b != null) {
            String category = b.getString("category");
            try {
                JSONObject jsonObject = new JSONObject(category);
                JSONArray places = jsonObject.getJSONArray("Places");
                for (int i = 0; i < places.length(); i++) {
                    JSONObject place = places.getJSONObject(i);
                    String name = place.getString("Name");
                    itemAdapter.add(name);
                }
            } catch (Exception e) {

            }
        } else {

        }
    }
}
