package com.example.matija.services;

import android.content.Context;

import com.example.matija.database.DatabaseHandler;
import com.example.matija.database.MuscleGroupObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Matija on 15.3.2016..
 */
public class FetchMuscleGroup {

    public static final String API_URL = "http://seminar123.xyz/";
    private List<MuscleGroup> muscleGroups;

    public class MuscleGroup{

        public int MuscleGroupID;
        public int MuscleID;
        public int MusclePercentage;

        public MuscleGroup(int MuscleGroupID, int MuscleID, int MusclePercentage) {
            this.MuscleGroupID = MuscleGroupID;
            this.MuscleID = MuscleID;
            this.MusclePercentage = MusclePercentage;
        }

    }

    public interface ExercisesServices {
        @GET("/scripts/getGroups.php")
        Call<List<MuscleGroup>> muscleGroups();

    }

    public void fetchGroups(final Context context, final boolean storeInDatabase){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesServices service = retrofit.create(ExercisesServices.class);
        Call<List<MuscleGroup>> call = service.muscleGroups();
        call.enqueue(new Callback<List<MuscleGroup>>() {
            @Override
            public void onResponse(Call<List<MuscleGroup>> call, Response<List<MuscleGroup>> response) {
                muscleGroups = response.body();
                if(storeInDatabase){
                    storeInDatabase(context);
                }
                //Log.v("Vjezbe Dohvacene", exercises.toString());


            }

            @Override
            public void onFailure(Call<List<MuscleGroup>> call, Throwable t) {


            }
        });
    }

    private void storeInDatabase(Context context) {
        List<MuscleGroupObject> internalMuscleGroups = new ArrayList<>();

        //Convert from external to internal database
        //Log.v("Vjezbe Dohvacene", "Convert Pocinje");
        for(MuscleGroup groups : muscleGroups){
            MuscleGroupObject tmp = new MuscleGroupObject(groups.MuscleGroupID, groups.MuscleID, groups.MusclePercentage);
            internalMuscleGroups.add(tmp);
        }
        //Log.v("Vjezbe Dohvacene", "Convert Zavrsava");

        DatabaseHandler handleData = new DatabaseHandler(context);
        handleData.addMultipleMuscleGroups(internalMuscleGroups);
    }


    public List<MuscleGroup> getExercises() {
        return muscleGroups;
    }

    public void setExercises(List<MuscleGroup> exercises) {
        this.muscleGroups = exercises;
    }
}
