package com.example.matija.database;

/**
 * Created by Matija on 15.3.2016..
 */
public class MuscleGroupObject {

    private int _MuscleGroupID;
    private int _MuscleID;
    private int _MusclePercentage;

    public MuscleGroupObject() {
    }

    public MuscleGroupObject(int _MuscleGroupID, int _MuscleID, int _MusclePercentage) {
        this._MuscleGroupID = _MuscleGroupID;
        this._MuscleID = _MuscleID;
        this._MusclePercentage = _MusclePercentage;
    }

    public void set_MuscleGroupID(int _MuscleGroupID) {
        this._MuscleGroupID = _MuscleGroupID;
    }

    public void set_MuscleID(int _MuscleID) {
        this._MuscleID = _MuscleID;
    }

    public void set_MusclePercentage(int _MusclePercentage) {
        this._MusclePercentage = _MusclePercentage;
    }

    public int get_MuscleGroupID() {
        return _MuscleGroupID;
    }

    public int get_MuscleID() {
        return _MuscleID;
    }

    public int get_MusclePercentage() {
        return _MusclePercentage;
    }

}
