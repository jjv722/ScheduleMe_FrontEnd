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

public class Price extends Fragment {
    private ItemAdapter itemAdapter = null;
    private ListView listView = null;
    private JSONArray data = null;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String price = (String) itemAdapter.getItem(i);
                Log.d("click", "this is a click on an itme.");
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (int index = 0; index < data.length(); index++) {
                        JSONObject jsonObject = data.getJSONObject(index);
                        if (jsonObject.getString("Price").length() <= price.length()) {
                            jsonArray.put(jsonObject);
                        }
                    }
                    Fragment f = new Date();
                    Bundle b = new Bundle();
                    b.putString("places", jsonArray.toString());
                    f.setArguments(b);

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
                } catch (Exception e) {
                    Log.d("catch", e.toString());
                }

            }
        });
        Bundle b = getArguments();
        if (b != null) {
            String category = b.getString("category");
            try {
                JSONObject jsonObject = new JSONObject(category);
                Log.d("places", jsonObject.toString());
                data = jsonObject.getJSONArray("Places");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject place = data.getJSONObject(i);
                    String price = place.getString("Price");
                    if (!itemAdapter.contains(price)) {
                        itemAdapter.add(price);
                    }
                }
                itemAdapter.sort();
            } catch (Exception e) {

            }
        } else {

        }
    }
}
