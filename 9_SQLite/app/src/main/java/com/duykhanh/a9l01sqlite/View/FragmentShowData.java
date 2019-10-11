package com.duykhanh.a9l01sqlite.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duykhanh.a9l01sqlite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShowData extends Fragment {


    public FragmentShowData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_show_data, container, false);
    }

}
