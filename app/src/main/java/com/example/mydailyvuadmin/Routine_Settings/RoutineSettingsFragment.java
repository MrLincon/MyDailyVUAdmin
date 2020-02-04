package com.example.mydailyvuadmin.Routine_Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import com.example.mydailyvuadmin.R;

public class RoutineSettingsFragment extends PreferenceFragment {

    //    private static final String PREF_NAME = "pref_name";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";
    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_STUDENT = "pref_student";
    private static final String PREF_TEACHER = "pref_teacher";
    private static final String PREF_DARK_MODE = "pref_dark_mode";
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                if (key.equals(PREF_NAME)) {
//                    Preference namePref = findPreference(key);
//                    namePref.setSummary(sharedPreferences.getString(key, ""));
//                }
                if (key.equals(PREF_SEC)) {
                    Preference secPref = findPreference(key);
                    secPref.setSummary(sharedPreferences.getString(key, ""));
                }
                if (key.equals(PREF_ROUTINE_TYPE)) {
                    Preference routineTypePref = findPreference(key);
                    routineTypePref.setSummary(sharedPreferences.getString(key, ""));
                }
                if (key.equals(PREF_TEACHERS_NAME)) {
                    Preference teacherPref = findPreference(key);
                    teacherPref.setSummary(sharedPreferences.getString(key, ""));
                }
                if (key.equals(PREF_SEMESTER)) {
                    Preference semPref = findPreference(key);
                    semPref.setSummary(sharedPreferences.getString(key, ""));
                }
                if (key.equals(PREF_DEPT)) {
                    Preference deptPref = findPreference(key);
                    deptPref.setSummary(sharedPreferences.getString(key, ""));
                }

                SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String RoutineType = sharedPreferences2.getString(PREF_ROUTINE_TYPE, "");
                switch (RoutineType) {
                    case "Teacher":
                        getPreferenceScreen().findPreference(PREF_TEACHER).setEnabled(true);
                        getPreferenceScreen().findPreference(PREF_STUDENT).setEnabled(false);
                        break;
                    case "Student":
                        getPreferenceScreen().findPreference(PREF_TEACHER).setEnabled(false);
                        getPreferenceScreen().findPreference(PREF_STUDENT).setEnabled(true);
                        break;
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        Preference secPref = findPreference(PREF_SEC);
        secPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_SEC, "Select Your Section"));

        Preference routineTypePref = findPreference(PREF_ROUTINE_TYPE);
        routineTypePref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_ROUTINE_TYPE, "Teacher or Student"));

        Preference teachersNamePref = findPreference(PREF_TEACHERS_NAME);
        teachersNamePref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_TEACHERS_NAME, "Teacher or Student"));

        Preference semesterPref = findPreference(PREF_SEMESTER);
        semesterPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_SEMESTER, "Teacher or Student"));

        Preference departmentPref = findPreference(PREF_DEPT);
        departmentPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_DEPT, "Select your department"));


        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String RoutineType = sharedPreferences2.getString(PREF_ROUTINE_TYPE, "");
        switch (RoutineType) {
            case "Teacher":
                getPreferenceScreen().findPreference(PREF_TEACHER).setEnabled(true);
                getPreferenceScreen().findPreference(PREF_STUDENT).setEnabled(false);
                break;
            case "Student":
                getPreferenceScreen().findPreference(PREF_TEACHER).setEnabled(false);
                getPreferenceScreen().findPreference(PREF_STUDENT).setEnabled(true);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
