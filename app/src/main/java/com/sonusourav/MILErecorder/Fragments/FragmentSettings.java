package com.sonusourav.MILErecorder.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sonusourav.MILErecorder.R;

import java.util.Objects;

public class FragmentSettings extends Fragment {
    private View homeView;
    private ImageButton heartRateImageBt;
    private ImageButton bloodPressureImageBt;
    private ImageButton historyImageBt;
    private ImageButton emergencyConImageBt;

    public static FragmentSettings newInstance() {
        FragmentSettings fragment = new FragmentSettings();
        return fragment;
    }

    public static Fragment newInstance(Bundle savedInstanceState) {

        Fragment fragment = new FragmentSettings();
        if (savedInstanceState != null) {
            fragment.setArguments(savedInstanceState);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_settings, null);
        
        return homeView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        android.support.v7.app.ActionBar actionBar;
        android.support.v7.app.ActionBar settingsActionBar;
        settingsActionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        assert settingsActionBar != null;
        settingsActionBar.setTitle("Settings");
        super.onCreateOptionsMenu(menu, inflater);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}