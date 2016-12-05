package com.scheduleme.History;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scheduleme.ItemAdapter;
import com.scheduleme.R;

/**
 * Created by mauricio on 12/4/16.
 */

public class History extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("UserProfile History");
        ItemAdapter hia = new ItemAdapter(getActivity(), R.layout.entry_history);
        ListView list = (ListView) getActivity().findViewById(R.id.history);
        list.setAdapter(hia);
        hia.add("one");
        hia.add("two");
        hia.add("three");
        hia.add("four");
        hia.add("five");
        hia.add("six");
        hia.add("seven");
        hia.add("eight");
    }
}
