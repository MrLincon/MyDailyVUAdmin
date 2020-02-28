package com.whitespace.mydailyvuadmin.Routine;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whitespace.mydailyvuadmin.Modify_Routine.AddClassThursday;
import com.whitespace.mydailyvuadmin.Modify_Routine.DetailsActivity;
import com.whitespace.mydailyvuadmin.Models.Routine;
import com.whitespace.mydailyvuadmin.Models.RoutineAdapter;
import com.whitespace.mydailyvuadmin.Models.RoutineRecyclerDecoration;
import com.whitespace.mydailyvuadmin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Fragment_Thursday extends Fragment {

    View view;

    RecyclerView recyclerView;
    FloatingActionButton addClass;

    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference routine = db.collection("Routine");
    private RoutineAdapter adapter;

    public static final String EXTRA_ID = "com.example.mydailyvuadmin.EXTRA_ID";
    public static final String EXTRA_START_TIME = "com.example.mydailyvuadmin.EXTRA_START_TIME";
    public static final String EXTRA_END_TIME = "com.example.mydailyvuadmin.EXTRA_END_TIME";
    public static final String EXTRA_SEMESTER = "com.example.mydailyvuadmin.EXTRA_SEMESTER";
    public static final String EXTRA_SECTION = "com.example.mydailyvuadmin.EXTRA_SECTION";
    public static final String EXTRA_DEPARTMENT = "com.example.mydailyvuadmin.EXTRA_DEPARTMENT";
    public static final String EXTRA_ROUTINE = "com.example.mydailyvuadmin.EXTRA_ROUTINE";
    public static final String EXTRA_SUBJECT = "com.example.mydailyvuadmin.EXTRA_SUBJECT";
    public static final String EXTRA_DAY = "com.example.mydailyvuadmin.EXTRA_DAY";
    public static final String EXTRA_TEACHER = "com.example.mydailyvuadmin.EXTRA_TEACHER";
    public static final String EXTRA_ROOM = "com.example.mydailyvuadmin.EXTRA_ROOM";

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thursday,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new RoutineRecyclerDecoration(topPadding,sidePadding,bottomPadding));

        addClass = view.findViewById(R.id.fab);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");
        String TEACHERS_NAME = sharedPreferences.getString(PREF_TEACHERS_NAME, "");

        if (ROUTINE != null && DEPARTMENT != null) {
            if (TEACHERS_NAME != null || (SEMESTER != null && SECTION != null)) {
                addClass.setVisibility(View.VISIBLE);
            } else {
                addClass.setVisibility(View.GONE);
            }
        } else {
            addClass.setVisibility(View.GONE);
        }

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addClass = new Intent(getActivity(), AddClassThursday.class);
                String DAY = "Thursday";
                addClass.putExtra(EXTRA_DAY, DAY);
                startActivity(addClass);
            }
        });

        getRoutine();

        return view;
    }

    private void getRoutine() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String ROUTINE = sharedPreferences.getString(PREF_ROUTINE_TYPE, "");
        final String DEPARTMENT = sharedPreferences.getString(PREF_DEPT, "");
        String SEMESTER = sharedPreferences.getString(PREF_SEMESTER, "");
        String SECTION = sharedPreferences.getString(PREF_SEC, "");
        final String TEACHERS_NAME = sharedPreferences.getString(PREF_TEACHERS_NAME, "");

        String SEM = null;

        if (SEMESTER.equals("1st")) {
            SEM = "1";
        } else if (SEMESTER.equals("2nd")) {
            SEM = "2";
        } else if (SEMESTER.equals("3rd")) {
            SEM = "3";
        } else if (SEMESTER.equals("4th")) {
            SEM = "4";
        } else if (SEMESTER.equals("5th")) {
            SEM = "5";
        } else if (SEMESTER.equals("6th")) {
            SEM = "6";
        } else if (SEMESTER.equals("7th")) {
            SEM = "7";
        } else if (SEMESTER.equals("8th")) {
            SEM = "8";
        } else if (SEMESTER.equals("9th")) {
            SEM = "9";
        } else if (SEMESTER.equals("10th")) {
            SEM = "10";
        } else if (SEMESTER.equals("11th")) {
            SEM = "11";
        } else if (SEMESTER.equals("12th")) {
            SEM = "12";
        }

        if (ROUTINE.equals("Student")) {
            Query query = routine.whereEqualTo("semester", SEM)
                    .whereEqualTo("section", SECTION)
                    .whereEqualTo("department", DEPARTMENT)
                    .whereEqualTo("day", "Thursday")
                    .orderBy("orderHour", Query.Direction.ASCENDING)
                    .orderBy("orderMinute", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.startListening();

            adapter.setOnItemClickListener(new RoutineAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot) {
                    Routine routine = documentSnapshot.toObject(Routine.class);
                    String id = documentSnapshot.getId();

                    Intent intent = new Intent(getActivity(), DetailsActivity.class);

                    String semester = routine.getSemester();
                    String section = routine.getSection();
                    String department = routine.getDepartment();
                    String day = routine.getDay();
                    String teacher = routine.getTeacher();
                    String subject = routine.getSubject();
                    String routineFor = routine.getRoutine();
                    String room = routine.getRoom();
                    String sTime = routine.getStartTime();
                    String eTime = routine.getEndTime();

                    intent.putExtra(EXTRA_ID, id);
                    intent.putExtra(EXTRA_SEMESTER, semester);
                    intent.putExtra(EXTRA_SECTION, section);
                    intent.putExtra(EXTRA_DEPARTMENT, department);
                    intent.putExtra(EXTRA_DAY, day);
                    intent.putExtra(EXTRA_TEACHER, teacher);
                    intent.putExtra(EXTRA_SUBJECT, subject);
                    intent.putExtra(EXTRA_ROUTINE, routineFor);
                    intent.putExtra(EXTRA_ROOM, room);
                    intent.putExtra(EXTRA_START_TIME, sTime);
                    intent.putExtra(EXTRA_END_TIME, eTime);

                    startActivity(intent);
                }
            });
        } else if (ROUTINE.equals("Teacher")) {

            Query query = routine.whereArrayContains("teachers",TEACHERS_NAME)
                    .whereEqualTo("day", "Thursday")
                    .whereEqualTo("department", DEPARTMENT)
                    .orderBy("orderHour", Query.Direction.ASCENDING)
                    .orderBy("orderMinute", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                    .setQuery(query, Routine.class)
                    .build();

            adapter = new RoutineAdapter(options);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.startListening();

            adapter.setOnItemClickListener(new RoutineAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot) {
                    Routine routine = documentSnapshot.toObject(Routine.class);
                    String id = documentSnapshot.getId();

                    Intent intent = new Intent(getActivity(), DetailsActivity.class);

                    String semester = routine.getSemester();
                    String section = routine.getSection();
                    String department = routine.getDepartment();
                    String day = routine.getDay();
                    String teacher = routine.getTeacher();
                    String subject = routine.getSubject();
                    String routineFor = routine.getRoutine();
                    String room = routine.getRoom();
                    String sTime = routine.getStartTime();
                    String eTime = routine.getEndTime();

                    intent.putExtra(EXTRA_ID, id);
                    intent.putExtra(EXTRA_SEMESTER, semester);
                    intent.putExtra(EXTRA_SECTION, section);
                    intent.putExtra(EXTRA_DEPARTMENT, department);
                    intent.putExtra(EXTRA_DAY, day);
                    intent.putExtra(EXTRA_TEACHER, teacher);
                    intent.putExtra(EXTRA_SUBJECT, subject);
                    intent.putExtra(EXTRA_ROUTINE, routineFor);
                    intent.putExtra(EXTRA_ROOM, room);
                    intent.putExtra(EXTRA_START_TIME, sTime);
                    intent.putExtra(EXTRA_END_TIME, eTime);

                    startActivity(intent);
                }
            });

        } else {
            try {
                adapter.stopListening();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !addClass.isShown())
                    addClass.show();
                else if (dy > 0 && addClass.isShown())
                    addClass.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getRoutine();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            adapter.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
