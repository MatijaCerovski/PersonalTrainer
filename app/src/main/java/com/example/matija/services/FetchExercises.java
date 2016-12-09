package com.example.matija.services;

import android.content.Context;

import com.example.matija.database.DatabaseHandler;
import com.example.matija.database.ExerciseObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Matija on 6.3.2016..
 */
public class FetchExercises {

    public static final String API_URL = "http://seminar123.xyz/";
    private List<Exercise> exercises;

    public class Exercise{

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
        @GET("/scripts/getExercises.php")
        Call<List<Exercise>> exercises();

    }

    public void fetchExercises(final Context context, final boolean storeInDatabase){
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
                if(storeInDatabase){
                    storeInDatabase(context);
                }
                //Log.v("Vjezbe Dohvacene", exercises.toString());


            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {


            }
        });
    }

    private void storeInDatabase(Context context) {
        List<ExerciseObject> internalExercises = new ArrayList<>();

        //Convert from external to internal database
        //Log.v("Vjezbe Dohvacene", "Convert Pocinje");
        for(Exercise exercise : exercises){
            ExerciseObject tmp = new ExerciseObject(exercise.ExerciseID, exercise.Description, exercise.ExerciseName, exercise.VideoURL);
            internalExercises.add(tmp);
        }
        //Log.v("Vjezbe Dohvacene", "Convert Zavrsava");

        DatabaseHandler handleData = new DatabaseHandler(context);
        handleData.addMultipleExercise(internalExercises);
    }


    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
