package com.example.matija.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.example.matija.lists.MainAdapter;
import com.example.matija.lists.MainRowInformation;
import com.example.matija.personaltrainer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 9.2.2016..
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerMain;
    private MainAdapter adapter;
    private static String[] titles;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Resources res = getResources();
        titles = res.getStringArray(R.array.muscle_titles);

        recyclerMain = (RecyclerView) rootView.findViewById(R.id.MainList);
        adapter = new MainAdapter(getActivity(), getData(getActivity()));
        recyclerMain.setAdapter(adapter);
        recyclerMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    public static List<MainRowInformation> getData(Context context){
        List<MainRowInformation> data = new ArrayList<>();


        for(int i=0;i<titles.length;i++){

            final MainRowInformation current = new MainRowInformation();
            current.title = titles[i];
            current.checkBox = new CheckBox(context);
            current.checkBox.setChecked(false);


            data.add(current);
        }

        return data;
    }

    public List<String> getSelectedItems(){
        return adapter.getSelected();
    }

}
