package com.sonusourav.MILErecorder.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.sonusourav.MILErecorder.CallAdapter;
import com.sonusourav.MILErecorder.ListItem;
import com.sonusourav.MILErecorder.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentHome extends Fragment {


    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    public static Fragment newInstance(Bundle savedInstanceState) {

        Fragment fragment = new FragmentHome();
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

        View view=inflater.inflate(R.layout.fragment_home,null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        List<ListItem> callList = new ArrayList<>();
        CallAdapter callAdapter = new CallAdapter(callList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(callAdapter);


        String path = Environment.getExternalStoragePublicDirectory("MILE Recorder").toString();
        if(!path.isEmpty()){
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            if(directory.exists()){
                File[] files = directory.listFiles();

                if(files!=null){

                    for (File file : files) {
                        Log.d("Files", "FileName:" + file.getName());
                        String[] callItem=file.getName().split("_");
                        String callName=file.getName();
                        String callPhone=callItem[0];
                        String callTime=callItem[1].replace(".mp3","");
                        ListItem callItems=new ListItem(callName,callPhone,callTime);
                        callList.add(callItems);

                    }
                }


            }

        }


        callAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        android.support.v7.app.ActionBar actionBar;
        actionBar=((AppCompatActivity)Objects.requireNonNull(getActivity())).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Home");
        super.onCreateOptionsMenu(menu,inflater);
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