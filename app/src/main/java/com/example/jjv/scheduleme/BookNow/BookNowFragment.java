package com.example.jjv.scheduleme.BookNow;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjv.scheduleme.Async_API;
import com.example.jjv.scheduleme.Auth.LogMe;
import com.example.jjv.scheduleme.Auth.RegisterFragment;
import com.example.jjv.scheduleme.R;

import org.json.JSONObject;

/**
 * Created by jjv on 11/9/16.
 */

public class BookNowFragment extends android.support.v4.app.Fragment {
    final String[] cost = {"$", "$$", "$$$", "$$$$"};
    final String[] ranges = {"5 miles", "10 miles", "15 miles", "20 miles"};
    final String[] categories = {"General Practice", "Optometry", "Dentistry", "Pediatrics"};
    Spinner maxCost;
    Spinner maxRange;
    Spinner category;
    EditText address;
    EditText date;

    Button book;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_now, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maxCost = (Spinner) view.findViewById(R.id.priceRange);
        maxRange= (Spinner) view.findViewById(R.id.distanceRange);
        category= (Spinner) view.findViewById(R.id.category);
        address = (EditText) view.findViewById(R.id.address);
        date = (EditText) view.findViewById(R.id.date);
        book = (Button) view.findViewById(R.id.book);

        ArrayAdapter<String> maxCostAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,cost);
        maxCost.setAdapter(maxCostAdapter);

        ArrayAdapter<String> maxRangeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,ranges);
        maxRange.setAdapter(maxRangeAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,categories);
        category.setAdapter(categoryAdapter);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
        });


    }


}
