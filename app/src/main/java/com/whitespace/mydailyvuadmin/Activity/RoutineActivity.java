package com.whitespace.mydailyvuadmin.Activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.whitespace.mydailyvuadmin.Authentication.LoginActivity;
import com.whitespace.mydailyvuadmin.R;
import com.whitespace.mydailyvuadmin.Routine.ViewPagerAdapter;
import com.whitespace.mydailyvuadmin.Routine_Settings.RoutineSettingsActivity;
import com.whitespace.mydailyvuadmin.Class.ThemeSettings;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class RoutineActivity extends AppCompatActivity {


    private static final String TAG = "RoutineActivity";
    TextView department, details;
    LinearLayout changeBtn;

    //    private static final String PREF_NAME = "pref_name";
    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    private AlertDialog.Builder alertdialogbuilder;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    private TextView toolbarTitle;

    ThemeSettings themeSettings;
    private Switch darkModeSwitch;

    Dialog popupChangeRoutine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme Settings
        themeSettings = new ThemeSettings(this);
        if (themeSettings.loadNightModeState() == false) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //...............
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tabLayout = findViewById(R.id.tabLayoutID);
        viewPager = findViewById(R.id.viewPager);
        department = findViewById(R.id.departmentTextview);
        details = findViewById(R.id.detailsTextview);

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        user = mAuth.getCurrentUser();

        popupChangeRoutine = new Dialog(this);

        changeBtn = findViewById(R.id.changeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                startActivity(intent_profile);
            }
        });

//        For Action Bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Routine");

//        For Tab Layout
        tabLayout.setupWithViewPager(viewPager);


//        For Navigation Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_view_left);

        Menu menu = navigationView.getMenu();
        MenuItem logOut = menu.findItem(R.id.log_out);

        if (user == null) {
            // set new title to the MenuItem
            logOut.setTitle("Log in");
        } else {
            logOut.setTitle("Log out");
        }

//        if (savedInstanceState == null) {
//            navigationView.setCheckedItem(R.id.routine);
//        }

//        For Calling Navigation Item Class

        NavigationItems();


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0, true);
        CurrentDay();

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.dark_mode_toggle); // This is the menu item that contains your switch
        Switch drawerSwitch = (Switch) menuItem.getActionView().findViewById(R.id.darkModeSwitch);
        drawerSwitch.setClickable(false);
        if (themeSettings.loadNightModeState() == false) {
            drawerSwitch.setChecked(false);
        } else {
            drawerSwitch.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.routine_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.notification:
                Intent notification = new Intent(RoutineActivity.this, NotificationActivity.class);
                startActivity(notification);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void AlertDialog() {
        alertdialogbuilder = new AlertDialog.Builder(RoutineActivity.this, R.style.AlertDialog);
        alertdialogbuilder.setCancelable(false);
        alertdialogbuilder.setMessage("Do you want to exit?");
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoutineActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                RoutineActivity.this.finish();
            }
        });

        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoutineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertdialogbuilder.show();
    }

    public void CurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SUNDAY == dayOfWeek) {
            viewPager.setCurrentItem(0, true);
        } else if (Calendar.MONDAY == dayOfWeek) {
            viewPager.setCurrentItem(1, true);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            viewPager.setCurrentItem(2, true);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            viewPager.setCurrentItem(3, true);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            viewPager.setCurrentItem(4, true);
        }
    }

    public void NavigationItems() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (menuItem.getItemId()) {
                            case R.id.routine:
                                break;
                            case R.id.change_routine:
                                Intent changeRoutine = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                                startActivity(changeRoutine);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case R.id.dark_mode_toggle:
                                if (themeSettings.loadNightModeState() == false) {
                                    themeSettings.setNightModeState(true);
                                    restartApp();   //Recreate activity
                                } else {
                                    themeSettings.setNightModeState(false);
                                    restartApp();   //Recreate activity
                                }
                                break;
                            case R.id.feedback:
                                Intent feedback_intent = new Intent(RoutineActivity.this, FeedbackActivity.class);
                                startActivity(feedback_intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case R.id.about:
                                Intent about_intent = new Intent(RoutineActivity.this, AboutActivity.class);
                                startActivity(about_intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                            case R.id.log_out:
                                if (user == null) {
                                    Intent log_in = new Intent(RoutineActivity.this, LoginActivity.class);
                                    startActivity(log_in);
                                } else {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent log_out = new Intent(RoutineActivity.this, RoutineActivity.class);
                                    startActivity(log_out);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                                break;
                        }
                    }
                }, 300);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    //Recreate activity

    private void restartApp() {
        Intent i = new Intent(RoutineActivity.this, RoutineActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //...............

    private void loadPopUp() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        department.setText(sharedPreferences.getString(PREF_DEPT, ""));

        String RoutineType = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String Department = sharedPreferences.getString(PREF_DEPT, "");
        String Semester = sharedPreferences.getString(PREF_SEMESTER, "");
        String Section = sharedPreferences.getString(PREF_SEC, "");
        String TeachersName = sharedPreferences.getString(PREF_TEACHERS_NAME, "");

        if (RoutineType == "" || Department == "") {
            popupChangeRoutine.setContentView(R.layout.popup_select_routine);
            popupChangeRoutine.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout select_routine = popupChangeRoutine.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popupChangeRoutine.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupChangeRoutine.dismiss();
                }
            });
            popupChangeRoutine.show();
        }
        if (RoutineType.equals("Teacher") && TeachersName == "") {
            popupChangeRoutine.setContentView(R.layout.popup_select_routine);
            popupChangeRoutine.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout select_routine = popupChangeRoutine.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popupChangeRoutine.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupChangeRoutine.dismiss();
                }
            });
            popupChangeRoutine.show();
        }
        if (RoutineType.equals("Student") && (Semester == "" || Section == "")) {
            popupChangeRoutine.setContentView(R.layout.popup_select_routine);
            popupChangeRoutine.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            LinearLayout select_routine = popupChangeRoutine.findViewById(R.id.select_routine);
            select_routine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_profile = new Intent(RoutineActivity.this, RoutineSettingsActivity.class);
                    startActivity(intent_profile);
                }
            });
            ImageView close = popupChangeRoutine.findViewById(R.id.close_popup);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupChangeRoutine.dismiss();
                }
            });
            popupChangeRoutine.show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            Intent intent = new Intent(RoutineActivity.this,OnboardingActivity.class);
            startActivity(intent);
            finish();
        }

        navigationView.setCheckedItem(R.id.routine);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        department.setText(sharedPreferences.getString(PREF_DEPT, ""));

        String RoutineType = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");

        switch (RoutineType) {
            case "Teacher":
                details.setText("Routine: " + sharedPreferences.getString(PREF_TEACHERS_NAME, ""));
                break;
            case "Student":
                details.setText("Routine: " + sharedPreferences.getString(PREF_SEMESTER, "") +
                        " - " + sharedPreferences.getString(PREF_SEC, ""));
                break;
        }

        loadPopUp();
    }
}
