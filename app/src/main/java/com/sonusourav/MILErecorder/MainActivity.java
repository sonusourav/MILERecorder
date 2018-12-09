package com.sonusourav.MILErecorder;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sonusourav.MILErecorder.Fragments.FragmentHome;
import com.sonusourav.MILErecorder.Fragments.FragmentProfile;
import com.sonusourav.MILErecorder.Fragments.FragmentSettings;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private static long back_pressed;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("PREF_TITLE",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.apply();
        if(!Objects.requireNonNull(sharedPreferences.getString("PREF_PROFILE_UPDATED", "false")).equalsIgnoreCase("true")){


            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.dialog_profile, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
            alertDialogBuilder.setTitle("Profile");
            alertDialogBuilder.setMessage("Update your profile");
            alertDialogBuilder.setView(promptView);

            final EditText dialogName =  promptView.findViewById(R.id.dialog_name);
            final EditText dialogMobile =  promptView.findViewById(R.id.dialog_mobile);
            final EditText dialogLang =  promptView.findViewById(R.id.dialog_lang);
            final EditText dialogRegion =  promptView.findViewById(R.id.dialog_region);


            // setup a dialog window
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });

            alertDialogBuilder.setView(promptView);
            final Dialog dialog=alertDialogBuilder.create();
            dialog.show();

            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String userName=dialogName.getText().toString().trim();
                    final String userMobile=dialogMobile.getText().toString().trim();
                    final String userLang=dialogLang.getText().toString().trim();
                    final String userRegion=dialogRegion.getText().toString().trim();

                    // get user input and set it to result
                    if(isValid(userName) && isValid(userMobile) && isValid(userLang) && isValid(userRegion)){
                        if(userMobile.length()!=10){
                            Toast.makeText(getApplicationContext(),"Invalid Mobile no",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d("dismiss","Reaching");
                            editor.putString("PREF_NAME",userName);
                            editor.putString("PREF_MOBILE",userMobile);
                            editor.putString("PREF_LANG",userLang);
                            editor.putString("PREF_REGION",userRegion);
                            editor.putString("PREF_PROFILE_UPDATED","true");
                            editor.commit();
                            dialog.dismiss();
                            checkAndroidVersion();

                        }


                    }

                }
            });

        }else
            checkAndroidVersion();


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationbar);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new FragmentHome();
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_home:
                        fragment = FragmentHome.newInstance(null);
                        break;
                    case R.id.action_profile:
                        fragment = FragmentProfile.newInstance(null);
                        break;
                    case R.id.action_settings:
                        fragment = FragmentSettings.newInstance(null);
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, FragmentHome.newInstance());
        transaction.commit();

    }


    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkPermission();

        } else {
            Toast.makeText(getApplicationContext(),"Call recording is not supported by your device",Toast.LENGTH_SHORT).show();
        }

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) +
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)+
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.PROCESS_OUTGOING_CALLS)) {


                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please grant all app permissions ",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                            new String[]{
                                                    Manifest.permission.RECORD_AUDIO,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.PROCESS_OUTGOING_CALLS},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            }
                        }).show();
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.PROCESS_OUTGOING_CALLS},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean micPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storagePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean processPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if(micPermission && storagePermission && processPermission)
                    {
                        Toast.makeText(getApplicationContext(),"All permissions granted",Toast.LENGTH_SHORT).show();
                    } else {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.PROCESS_OUTGOING_CALLS)) {

                            Snackbar.make(this.findViewById(android.R.id.content),
                                    "Please grant all the app permissions  ",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(
                                                        new String[]{
                                                                Manifest.permission.RECORD_AUDIO,
                                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                                Manifest.permission.PROCESS_OUTGOING_CALLS},
                                                        PERMISSIONS_MULTIPLE_REQUEST);
                                            }
                                        }
                                    }).show();
                        }
                    }
                }
                break;
        }
    }

    public boolean isValid(String input) {

        if (TextUtils.isEmpty(input)) {
            Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_SHORT).show();
            return false;
        }else
        return true;

    }




    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            moveTaskToBack(true);
        } else
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
