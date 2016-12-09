package com.example.matija.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.matija.lists.ExercisesRowInformation;
import com.example.matija.services.FetchMuscleGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matija on 2.3.2016..
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //table exercises
    private static final String DATABASE_NAME = "trenerbaza";
    private static final String TABLE_EXERCISES = "exercises";
    private static final int DATABASE_VERSION = 1;
    private static final String EXERCISE_ID = "_ExerciseID";
    private static final String EXERCISE_NAME = "_ExerciseName";
    private static final String DESCRIPTION = "_Description";
    private static final String VIDEO_URL = "_VideoURL";
    private static final String SELECT_ALL_EXERCISES = "SELECT * FROM " + TABLE_EXERCISES + " WHERE 1;";
    private static final String CREATE_TABLE_EXERCISES = "CREATE TABLE "+ TABLE_EXERCISES +
            " (" +
            EXERCISE_ID+" INTEGER , " +
            EXERCISE_NAME + " VARCHAR(40), " +
            DESCRIPTION + " TEXT , " +
            VIDEO_URL + " VARCHAR(2000) " +
            ");";

    //table muscle group
    private static final String TABLE_MUSCLE_GROUP = "muscleGroup";
    private static final String MUSCLE_GROUP_ID = "_MuscleGroupID";
    private static final String MUSCLE_ID_FOREIGN = "_MuscleID";
    private static final String MUSCLE_PERCENTAGE = "_MusclePercentage";
    private static final String CREATE_TABLE_MUSCLE_GROUP = "CREATE TABLE "+ TABLE_MUSCLE_GROUP +
            " (" +
            MUSCLE_GROUP_ID+" INTEGER , " +
            MUSCLE_ID_FOREIGN + " INTEGER , " +
            MUSCLE_PERCENTAGE + " INTEGER " +
            ");";


    //table muscle
    private static final String TABLE_MUSCLE = "muscle";
    private static final String MUSCLE_ID = "_MuscleID";
    private static final String MUSCLE_NAME = "_MuscleName";
    private static final String CREATE_TABLE_MUSCLE = "CREATE TABLE "+ TABLE_MUSCLE +
            " (" +
            MUSCLE_ID+" INTEGER PRIMARY KEY , " +
            MUSCLE_NAME + " VARCHAR(40) " +
            ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_EXERCISES);
            db.execSQL(CREATE_TABLE_MUSCLE_GROUP);
            db.execSQL(CREATE_TABLE_MUSCLE);
            Log.d("ONCREATE", "Database Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSCLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSCLE);
        onCreate(db);
    }

    //Add new row to exercises
    public void addExercise(ExerciseObject exercise){
        ContentValues values = new ContentValues();
        values.put(EXERCISE_NAME, exercise.get_ExerciseName());
        values.put(DESCRIPTION, exercise.get_Description());
        values.put(VIDEO_URL, exercise.get_VideoURL());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EXERCISES, null, values);
        db.close();
    }

    //Add Multiple row to exercises
    public void addMultipleExercise(List<ExerciseObject> exercises){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        Log.v("POCETAK", "POCETAK");

        try {
            db.beginTransaction();
            for(ExerciseObject tmp : exercises){
                value.put(EXERCISE_ID, tmp.get_ExerciseID());
                value.put(EXERCISE_NAME, tmp.get_ExerciseName());
                value.put(DESCRIPTION, tmp.get_Description());
                value.put(VIDEO_URL, tmp.get_VideoURL());
                db.insert(TABLE_EXERCISES, null, value);
                value.clear();

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        db.close();
        Log.v("KRAJ", "KRAJ");

    }

    //Get All Exercises
    public List<ExercisesRowInformation> getAllExercises(){
        List<ExercisesRowInformation> data = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(SELECT_ALL_EXERCISES, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(EXERCISE_NAME)) != null){
                ExercisesRowInformation tmp = new ExercisesRowInformation();
                tmp.name = c.getString(c.getColumnIndex(EXERCISE_NAME));
                data.add(tmp);
                c.moveToNext();

            }


        }
        c.close();
        db.close();
        return data;
    }

    public Percentage getPercentages(String name){
        Percentage percentage = new Percentage();
        Integer tmpInt = 0;
        String tmpString = "";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("select muscleGroup._MusclePercentage, muscle._MuscleName from exercises\n" +
                "join muscleGroup on muscleGroup._MuscleGroupID = exercises._ExerciseID\n" +
                "join muscle on muscle._MuscleID = muscleGroup._MuscleID\n" +
                "where exercises._ExerciseName =?", new String[]{name});

        try{

            if(cur.getCount() > 0) {
                while (cur.moveToNext()) {

                    tmpInt = cur.getInt(cur.getColumnIndex("_MusclePercentage"));
                    tmpString = cur.getString(cur.getColumnIndex("_MuscleName"));
                    percentage.insertData(tmpInt, tmpString);
                }
            }

        }finally {

            cur.close();
        }
        return percentage;

    }
    public AllExerciseData getSingleExerciseDetails(String name){
        AllExerciseData fullExercise = new AllExerciseData();
        ExerciseObject exercise = new ExerciseObject();
        MuscleGroupObject muscleGroupObject = new MuscleGroupObject();
        List<MuscleGroupObject> muscleGroupObjects = new ArrayList<MuscleGroupObject>();
        MuscleObject muscleObject = new MuscleObject();
        List<MuscleObject> muscleObjects = new ArrayList<MuscleObject>();
        String selectionArgs = "";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_EXERCISES+" WHERE  "+EXERCISE_NAME+" =?", new String[]{name});
        try{

            if(c.getCount() > 0) {

                c.moveToFirst();
                exercise.set_ExerciseID(c.getString(c.getColumnIndex(EXERCISE_ID)));
                exercise.set_ExerciseName(c.getString(c.getColumnIndex(EXERCISE_NAME)));
                exercise.set_Description(c.getString(c.getColumnIndex(DESCRIPTION)));
                exercise.set_VideoURL(c.getString(c.getColumnIndex(VIDEO_URL)));
            }

        }finally {
            fullExercise.setExerciseObject(exercise);
            c.close();
        }


        Cursor d = db.rawQuery("SELECT * FROM "+TABLE_MUSCLE_GROUP+" WHERE "+MUSCLE_GROUP_ID+" =?", new String[]{exercise.get_ExerciseID()});

        try{
            while(d.moveToNext()) {
                muscleGroupObject.set_MuscleGroupID(d.getInt(d.getColumnIndex(MUSCLE_GROUP_ID)));
                muscleGroupObject.set_MuscleID(d.getInt(d.getColumnIndex(MUSCLE_ID_FOREIGN)));
                if (selectionArgs != null && !selectionArgs.isEmpty()) {
                    selectionArgs += d.getString(d.getColumnIndex(MUSCLE_ID_FOREIGN));
                } else {
                    selectionArgs += ", " + d.getString(d.getColumnIndex(MUSCLE_ID_FOREIGN));
                }
                selectionArgs += d.getString(d.getColumnIndex(MUSCLE_ID_FOREIGN));
                muscleGroupObject.set_MusclePercentage(d.getInt(d.getColumnIndex(MUSCLE_PERCENTAGE)));

                muscleGroupObjects.add(muscleGroupObject);

            }
        }finally{
            fullExercise.setMuscleGroupObjects(muscleGroupObjects);
            d.close();
        }







        Cursor e = db.rawQuery("SELECT * FROM " + TABLE_MUSCLE + " WHERE " + MUSCLE_ID + " IN (?)", new String[]{selectionArgs});

        try{
            while (e.moveToNext()) {
                muscleObject.set_MuscleID(e.getInt(e.getColumnIndex(MUSCLE_ID)));
                muscleObject.set_MuscleName(e.getString(e.getColumnIndex(MUSCLE_NAME)));
                muscleObjects.add(muscleObject);
            }
        }finally{
                fullExercise.setMuscleObjects(muscleObjects);
                e.close();
            }


        return fullExercise;
    }

    public void addMultipleMuscles(List<MuscleObject> muscles) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        Log.v("POCETAK", "POCETAK");

        try {
            db.beginTransaction();
            for(MuscleObject tmp : muscles){
                value.put(MUSCLE_ID, tmp.get_MuscleID());
                value.put(MUSCLE_NAME, tmp.get_MuscleName());
                db.insert(TABLE_MUSCLE, null, value);
                value.clear();

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        db.close();
    }

    public void addMultipleMuscleGroups(List<MuscleGroupObject> internalMuscleGroups) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        Log.v("POCETAK", "POCETAK");

        try {
            db.beginTransaction();
            for(MuscleGroupObject tmp : internalMuscleGroups){
                value.put(MUSCLE_GROUP_ID, tmp.get_MuscleGroupID());
                value.put(MUSCLE_ID_FOREIGN, tmp.get_MuscleID());
                value.put(MUSCLE_PERCENTAGE, tmp.get_MusclePercentage());
                db.insert(TABLE_MUSCLE_GROUP, null, value);
                value.clear();

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        db.close();
    }
}
