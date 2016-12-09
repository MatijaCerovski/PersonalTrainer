package com.example.matija.database;

import java.util.List;

/**
 * Created by Matija on 17.3.2016..
 */
public class AllExerciseData {
    private ExerciseObject exerciseObject;
    private List<MuscleGroupObject> muscleGroupObjects;
    private List<MuscleObject> muscleObjects;

    public AllExerciseData() {

    }

    public ExerciseObject getExerciseObject() {
        return exerciseObject;
    }

    public List<MuscleGroupObject> getMuscleGroupObjects() {
        return muscleGroupObjects;
    }

    public List<MuscleObject> getMuscleObjects() {
        return muscleObjects;
    }




    public void setExerciseObject(ExerciseObject exerciseObject) {
        this.exerciseObject = exerciseObject;
    }

    public void setMuscleGroupObjects(List<MuscleGroupObject> muscleGroupObjects) {
        this.muscleGroupObjects = muscleGroupObjects;
    }

    public void setMuscleObjects(List<MuscleObject> muscleObjects) {
        this.muscleObjects = muscleObjects;
    }

    public void getPercentages(){

    }


}
