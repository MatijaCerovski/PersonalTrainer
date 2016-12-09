package com.example.matija.lists;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.matija.personaltrainer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matija on 11.2.2016..
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private List<MainRowInformation> data = Collections.emptyList();

    public MainAdapter(Context context, List<MainRowInformation> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_main, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MainRowInformation current = data.get(position);
        holder.title.setText(current.title);
        //mozda je krivo sljedeca linija
        holder.checkbox = current.checkBox;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        CheckBox checkbox;

        public MyViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.MainListText);
            checkbox = (CheckBox) itemView.findViewById(R.id.MainListCheckBox);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(checkbox.isChecked()){
                    checkbox.setChecked(false);
                }else{
                    checkbox.setChecked(true);
                }
                }
            });

        }
    }

    public List<String> getSelected(){
        List<String> selected = new ArrayList<>();
        for(MainRowInformation tmp : data)
        {
            if(tmp.checkBox.isChecked()){
                selected.add(tmp.title);
            }
        }
    return selected;
    }
}
