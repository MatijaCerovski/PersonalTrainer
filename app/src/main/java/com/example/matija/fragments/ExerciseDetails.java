package com.example.matija.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;

import android.text.method.LinkMovementMethod;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.matija.database.AllExerciseData;
import com.example.matija.database.DatabaseHandler;
import com.example.matija.database.ExerciseObject;
import com.example.matija.database.Percentage;
import com.example.matija.personaltrainer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 8.3.2016..
 */
public class ExerciseDetails extends Fragment {
    private String exerciseName;
    private AllExerciseData exercise;
    private Percentage percentage;
    private TextView textDescription;
    private TextView youtubeLink;
    private PieChart pieChart;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textDescription = new TextView(getActivity());
        textDescription = (TextView) getView().findViewById(R.id.details_description);
        //textDescription.append(exercise.get_Description());
        textDescription.append(exercise.getExerciseObject().get_Description());

        //youtube link

        youtubeLink= (TextView) getView().findViewById(R.id.description_link);
        String htmltext = "<a href=\""+exercise.getExerciseObject().get_VideoURL()+"\">Link to Youtube</a>";
        youtubeLink.setText(Html.fromHtml(htmltext));
        youtubeLink.setMovementMethod(LinkMovementMethod.getInstance());
        //Log.d("POSTOTAK", );

        //creating chart
        pieChart = (PieChart) getView().findViewById(R.id.chart);

        //costumize chart
        pieChart.setDescription("Exercise Percentage");
        pieChart.setNoDataText("No data for this exercise!");
        pieChart.setUsePercentValues(true);

        //enable hole and configure
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);


        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        //data
        addData();
        Log.d("JEBENI GRAF", Integer.toString(percentage.getName().size()));



    }

    private void addData() {

        ArrayList<Entry> yValues = new ArrayList<Entry>();
        ArrayList<String> xValues = new ArrayList<String>();

        for(int i = 0; i < percentage.getPercentage().size(); i++)
            yValues.add(new Entry(percentage.getPercentage().get(i), i));

        for(int j = 0 ; j<percentage.getName().size(); j++)
            xValues.add(percentage.getName().get(j));

        //dataset
        PieDataSet dataset = new PieDataSet(yValues, "Muscle Worked");
        dataset.setSliceSpace(3);
        dataset.setSelectionShift(5);

        //add Colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataset.setColors(colors);

        //instanciate pie data object
        PieData data= new PieData(xValues, dataset);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_details, container, false);
        exercise = new DatabaseHandler(getActivity()).getSingleExerciseDetails(exerciseName);
        percentage = new DatabaseHandler(getActivity()).getPercentages(exerciseName);


        return rootView;
    }

    private void fillWithData() {
        textDescription.setText(exercise.getExerciseObject().get_Description());

    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }
}
