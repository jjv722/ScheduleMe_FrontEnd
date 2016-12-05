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

import com.scheduleme.Authentication;
import com.scheduleme.ItemAdapter;
import com.scheduleme.Network.Network;
import com.scheduleme.Network.PartnerCalls;
import com.scheduleme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mauricio on 12/4/16.
 */

public class Category extends Fragment implements Callback<ResponseBody> {
    private ListView list = null;
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
        subheader.setText("What kind of appointment are you trying to make?");
        getActivity().setTitle("Book An Appointment");

        itemAdapter = new ItemAdapter(getActivity(), R.layout.entry_book);
        list = (ListView) getActivity().findViewById(R.id.book);
        list.setAdapter(itemAdapter);

        Network.getInstance().getPartners(getActivity()).enqueue(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // data is based on item clicked.
                String category = (String) itemAdapter.getItem(i);
                for (int index = 0; index < data.length(); index++) {
                    try {
                        JSONObject jsonObject = (JSONObject) data.get(index);
                        String searchCategory = jsonObject.getString("Category");
                        if (category.equals(searchCategory)) {
                            Fragment f = new BookDoctor();
                            Bundle b = new Bundle();
                            b.putString("category", jsonObject.toString());
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
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d("onResponse", "code: " + response.code());
        if (response.code() == 200) {
            try {
                data = new JSONArray(response.body().string());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = (JSONObject) data.get(i);
                    String category = jsonObject.getString("Category");
                    itemAdapter.add(category);
                }
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
        } else {

        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }
}
