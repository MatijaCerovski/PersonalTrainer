package com.example.matija.lists;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



import com.example.matija.personaltrainer.MainActivity;
import com.example.matija.personaltrainer.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Matija on 11.2.2016..
 */
public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<ExercisesRowInformation> data = Collections.emptyList();

    public List<ExercisesRowInformation> getData() {
        return data;
    }

    public void setData(List<ExercisesRowInformation> data) {
        this.data = data;
    }
    public void getDataByPosition(){

    }

    public ExercisesAdapter(Context context, List<ExercisesRowInformation> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_exercises, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ExercisesRowInformation current = data.get(position);
        holder.title.setText(current.name);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.ExercisesListText);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            MainActivity activity = (MainActivity)v.getContext();
            activity.openExerciseDescriptionFragment(title.getText().toString());


        }
    }
}


