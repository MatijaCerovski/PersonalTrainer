package com.example.matija.database;

/**
 * Created by Matija on 12.3.2016..
 */
public class MuscleObject {
    private  int _MuscleID;
    private   String _MuscleName;

    public MuscleObject(int _MuscleID, String _MuscleName) {
        this._MuscleID = _MuscleID;
        this._MuscleName = _MuscleName;
    }

    public MuscleObject() {
    }

    public void set_MuscleID(int _MuscleID) {
        this._MuscleID = _MuscleID;
    }

    public void set_MuscleName(String _MuscleName) {
        this._MuscleName = _MuscleName;
    }

    public int get_MuscleID() {
        return _MuscleID;
    }

    public String get_MuscleName() {
        return _MuscleName;
    }
}
