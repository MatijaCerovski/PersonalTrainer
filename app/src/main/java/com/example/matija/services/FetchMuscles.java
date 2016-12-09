package com.example.matija.services;

import android.content.Context;

import com.example.matija.database.DatabaseHandler;
import com.example.matija.database.MuscleObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Matija on 12.3.2016..
 */
public class FetchMuscles {

    public static final String API_URL = "http://seminar123.xyz/";
    private List<Muscle> muscles;

    public  class Muscle{
        public int MuscleID;
        public String MuscleName;

        public Muscle(int muscleID, String muscleName) {
            this.MuscleID = muscleID;
            this.MuscleName = muscleName;
        }
    }

    public interface ExercisesServices {
        @GET("/scripts/getMuscles.php")
        Call<List<Muscle>> muscles();
    }

    public void fetchMuscles(final Context context, final boolean storeInDatabase){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ExercisesServices service = retrofit.create(ExercisesServices.class);
        Call<List<Muscle>> call = service.muscles();
        call.enqueue(new Callback<List<Muscle>>() {
            @Override
            public void onResponse(Call<List<Muscle>> call, Response<List<Muscle>> response) {
                muscles = response.body();
                if(storeInDatabase){
                    storeInDatabase(context);
                }
                //Log.v("Vjezbe Dohvacene", exercises.toString());


            }

            @Override
            public void onFailure(Call<List<Muscle>> call, Throwable t) {


            }
        });
    }

    private void storeInDatabase(Context context) {

        List<MuscleObject> internalMuscles = new ArrayList<>();

        //Convert from external to internal database
        //Log.v("Vjezbe Dohvacene", "Convert Pocinje");
        for(Muscle muscle : muscles){
            MuscleObject tmp = new MuscleObject(muscle.MuscleID, muscle.MuscleName);
            internalMuscles.add(tmp);
        }
        //Log.v("Vjezbe Dohvacene", "Convert Zavrsava");

        DatabaseHandler handleData = new DatabaseHandler(context);
        handleData.addMultipleMuscles(internalMuscles);
    }
}
