package com.example.matija.database;

/**
 * Created by Matija on 2.3.2016..
 */
public class ExerciseObject {
    private  String _ExerciseID;
    private   String _ExerciseName;
    private   String _Description;
    private   String _VideoURL;

    public ExerciseObject() {
    }

    public ExerciseObject(String _Id, String _Description, String _ExerciseName, String _VideoURL) {
        this._ExerciseID = _Id;
        this._Description = _Description;
        this._ExerciseName = _ExerciseName;
        this._VideoURL = _VideoURL;
    }

    public String get_ExerciseID() {
        return _ExerciseID;
    }

    public String get_ExerciseName() {
        return _ExerciseName;
    }

    public String get_Description() {
        return _Description;
    }

    public String get_VideoURL() {
        return _VideoURL;
    }

    public void set_ExerciseID(String _ExerciseID) {
        this._ExerciseID = _ExerciseID;
    }

    public void set_VideoURL(String _VideoURL) {
        this._VideoURL = _VideoURL;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

    public void set_ExerciseName(String _ExerciseName) {
        this._ExerciseName = _ExerciseName;
    }
}
