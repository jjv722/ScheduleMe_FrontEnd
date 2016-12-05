package com.scheduleme.Book;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.scheduleme.ItemAdapter;
import com.scheduleme.R;

/**
 * Created by mauricio on 12/4/16.
 */

public class BookDoctor extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView subheader = (TextView) view.findViewById(R.id.subheader);
        subheader.setText("Which Doctor do you prefer?");
        getActivity().setTitle("Book An Appointment");

        final ItemAdapter ia = new ItemAdapter(getActivity(), R.layout.entry_book);
        ListView list = (ListView) getActivity().findViewById(R.id.book);
        list.setAdapter(ia);

        ia.add("Dr. One");
        ia.add("Dr. Two");
        ia.add("Dr. Three");
        ia.add("Dr. Four");
    }
}
