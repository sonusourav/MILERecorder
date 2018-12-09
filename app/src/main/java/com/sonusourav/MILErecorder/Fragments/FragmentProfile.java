package com.sonusourav.MILErecorder.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonusourav.MILErecorder.R;

import java.util.Objects;

public class FragmentProfile extends Fragment {

    private EditText profileName, profileMobile, profileLang, profileRegion;
    MenuItem save;
    String name,mobile,lang,region;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static FragmentProfile newInstance() {
        return new FragmentProfile();
    }

    public static Fragment newInstance(Bundle savedInstanceState) {

        Fragment fragment = new FragmentProfile();
        if (savedInstanceState != null) {
            fragment.setArguments(savedInstanceState);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sharedPreferences=Objects.requireNonNull(getActivity()).getSharedPreferences("PREF_TITLE",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.apply();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_profile, null);
        profileName = homeView.findViewById(R.id.profile_name);
        profileMobile = homeView.findViewById(R.id.profile_mobile);
        profileLang = homeView.findViewById(R.id.profile_lang);
        profileRegion = homeView.findViewById(R.id.profile_region);
        Button editButton = homeView.findViewById(R.id.edit_button);


        name=sharedPreferences.getString("PREF_NAME",null);
        mobile=sharedPreferences.getString("PREF_MOBILE",null);
        lang=sharedPreferences.getString("PREF_LANG",null);
        region=sharedPreferences.getString("PREF_REGION",null);


        if(name!=null)
            profileName.setText(name);
        if(mobile!=null)
            profileMobile.setText(mobile);
        if(lang!=null)
            profileLang.setText(lang);
        if(region!=null)
            profileRegion.setText(region);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileName.setEnabled(true);
                profileMobile.setEnabled(true);
                profileLang.setEnabled(true);
                profileRegion.setEnabled(true);
                save.setEnabled(true);
                profileName.requestFocus();

            }
        });

        return homeView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_button_profile, menu);
        android.support.v7.app.ActionBar actionBar;
        actionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Profile");
        super.onCreateOptionsMenu(menu, inflater);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                return true;
            case R.id.profile_save:
                update();
                profileName.setEnabled(false);
                profileMobile.setEnabled(false);
                profileLang.setEnabled(false);
                profileRegion.setEnabled(false);
                Toast.makeText(getActivity(), "Successfully saved", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        save = menu.findItem(R.id.profile_save);
        save.setEnabled(false);

    }

    public void update() {
        name = profileName.getText().toString().trim();
        mobile = profileMobile.getText().toString().trim();
        lang = profileLang.getText().toString().trim();
        region = profileRegion.getText().toString().trim();

        if(isValid(name) && isValid(mobile) && isValid(lang) && isValid(region)){
            if(mobile.length()!=10){
                Toast.makeText(getActivity(),"Invalid Mobile no",Toast.LENGTH_SHORT).show();
            }else {
                editor.putString("PREF_NAME",name);
                editor.putString("PREF_MOBILE",mobile);
                editor.putString("PREF_LANG",lang);
                editor.putString("PREF_REGION",region);
                editor.putString("PREF_PROFILE_UPDATED","true");
                editor.commit();
                save.setEnabled(false);
            }

        }

    }

    public boolean isValid(String input) {

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(getActivity(), "Fill all the details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


}