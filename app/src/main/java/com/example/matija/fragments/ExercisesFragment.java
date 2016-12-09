package com.example.matija.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matija.lists.ExercisesAdapter;
import com.example.matija.lists.ExercisesRowInformation;
import com.example.matija.personaltrainer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import com.example.matija.database.DatabaseHandler;

/**
 * Created by Matija on 9.2.2016..
 */
public class ExercisesFragment extends Fragment {

    public static final String API_URL = "http://seminar.cu.cc/";

    private class Exercise{

        public String ExerciseID;
        public String ExerciseName;
        public String Description;
        public String VideoURL;

        public Exercise(String exerciseID, String exerciseName, String description, String videoURL) {
            this.ExerciseID = exerciseID;
            this.ExerciseName = exerciseName;
            this.Description = description;
            this.VideoURL = videoURL;
        }

        @Override
        public String toString() {
            return "Exercise{" +
                    "ExerciseID='" + ExerciseID + '\'' +
                    ", ExerciseName='" + ExerciseName + '\'' +
                    ", Description='" + Description + '\'' +
                    ", VideoURL='" + VideoURL + '\'' +
                    '}';
        }
    }

    public interface ExercisesServices {
        @GET("/scripts/getAllExercises.php")
        Call<List<Exercise>> exercises();

    }

    private RecyclerView recyclerExercises;
    private ExercisesAdapter adapter;
    private List<Exercise> exercises;
    List<ExercisesRowInformation> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercises, container, false);

        data = new ArrayList<>();
        recyclerExercises = (RecyclerView) rootView.findViewById(R.id.ExercisesList);
        adapter = new ExercisesAdapter(getActivity(), data);
        recyclerExercises.setAdapter(adapter);
        recyclerExercises.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesServices service = retrofit.create(ExercisesServices.class);
        Call<List<Exercise>> call = service.exercises();
        call.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                exercises = response.body();
                adapter.setData(getData());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                DatabaseHandler handler = new DatabaseHandler(getActivity());

                adapter.setData(handler.getAllExercises());
                adapter.notifyDataSetChanged();

            }
        });


        return rootView;
    }


    public List<ExercisesRowInformation> getData(){
        List<ExercisesRowInformation> data = new ArrayList<>();


        for(Exercise exercise : this.exercises){

            ExercisesRowInformation current = new ExercisesRowInformation();
            current.name = exercise.ExerciseName;
            //current.checkBox.setChecked(false);

            data.add(current);
        }

        return data;
    }

}
