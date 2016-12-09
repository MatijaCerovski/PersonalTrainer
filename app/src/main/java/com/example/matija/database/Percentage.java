package com.example.matija.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 29.3.2016..
 */
public class Percentage {
    private List<Integer> percentage;
    private List<String> name;

    public Percentage() {
        percentage = new ArrayList<>();
        name = new ArrayList<>();
    }

    public Percentage(List<Integer> percentage, List<String> name) {
        this.percentage = percentage;
        this.name = name;
    }

    public List<String> getName() {
        return name;
    }

    public List<Integer> getPercentage() {
        return percentage;
    }

    public void setPercentage(List<Integer> percentage) {
        this.percentage = percentage;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public void insertData(Integer percentage, String name){
        this.percentage.add(percentage);
        this.name.add(name);

    }
}
