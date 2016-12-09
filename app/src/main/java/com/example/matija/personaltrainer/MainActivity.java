package com.example.matija.personaltrainer;




import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matija.database.DatabaseHandler;
import com.example.matija.fragments.ExerciseDetails;
import com.example.matija.fragments.ExercisesFragment;
import com.example.matija.fragments.MainFragment;
import com.example.matija.services.FetchExercises;
import com.example.matija.services.FetchMuscleGroup;
import com.example.matija.services.FetchMuscles;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String PREFS_NAME = "MyPrefsFile";
    DatabaseHandler download;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //First time run check
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            //Fetch initial data from Internet
            FetchExercises exercises = new FetchExercises();
            exercises.fetchExercises(getApplicationContext(), true);

            FetchMuscles muscles = new FetchMuscles();
            muscles.fetchMuscles(getApplicationContext(), true);

            FetchMuscleGroup groups = new FetchMuscleGroup();
            groups.fetchGroups(getApplicationContext(),true);



            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_start) {
            // Handle the start action
            fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
        } else if (id == R.id.nav_exercises) {
            fm.beginTransaction().replace(R.id.content_frame, new ExercisesFragment()).commit();

        } else if (id == R.id.nav_me) {

        } else if (id == R.id.nav_goals) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.action_settings){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openExerciseDescriptionFragment(String exerciseName){
        FragmentManager fm = getFragmentManager();
        ExerciseDetails detailsFragment = new ExerciseDetails();
        detailsFragment.setExerciseName(exerciseName);
        fm.beginTransaction().replace(R.id.content_frame, detailsFragment).commit();
        toolbar.setTitle(exerciseName);
    }

    public void startClicked(View view) {

        FragmentManager fm = getFragmentManager();
        MainFragment fragment = (MainFragment)fm.findFragmentById(R.id.content_frame);
         List<String> selected = fragment.getSelectedItems();

        Toast.makeText(getApplication(), Integer.toString(selected.size()),
                Toast.LENGTH_LONG).show();

    }

}
