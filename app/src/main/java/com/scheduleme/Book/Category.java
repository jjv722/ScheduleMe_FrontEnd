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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.9:8000/")
                .build();
        PartnerCalls service = retrofit.create(PartnerCalls.class);
        Call<ResponseBody> myPartners = service.getAll(
                Authentication.load(getActivity()).getToken()
        );
        myPartners.enqueue(this);

//        ia.add("Dentristry");
//        ia.add("General Practice");
//        ia.add("Optometry");
//        ia.add("Pediatrics");
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // data is based on item clicked.
//                getFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(
//                                R.animator.slide_in_right,
//                                R.animator.slide_out_left,
//                                R.animator.slide_in_left,
//                                R.animator.slide_out_right)
//                        .replace(R.id.fragment_container, new BookDoctor())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d("onResponse", "code: " + response.code());
        if (response.code() == 200) {
            try {
                JSONArray jsonArray = new JSONArray(response.body().string());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
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
