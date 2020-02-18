package com.whitespace.mydailyvuadmin.Modify_Routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitespace.mydailyvuadmin.R;
import com.whitespace.mydailyvuadmin.Routine.Fragment_Sunday;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    TextView cardStartTime, cardEndTime, cardSubject, cardTeacher, cardRoutine, cardRoom, cardSemester, cardSection;

    LinearLayout back, edit;

    public static final String EXTRA_ID = "com.example.mydailyvuadmin.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Class Details");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cardStartTime = findViewById(R.id.startTime);
        cardEndTime = findViewById(R.id.endTime);
        cardSubject = findViewById(R.id.subject);
        cardTeacher = findViewById(R.id.teacher);
        cardRoutine = findViewById(R.id.routine);
        cardRoom = findViewById(R.id.room);
        cardSemester = findViewById(R.id.semester);
        cardSection = findViewById(R.id.section);

        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);

        final Intent intent = getIntent();

        final String id = intent.getStringExtra(Fragment_Sunday.EXTRA_ID);
        String startTime = intent.getStringExtra(Fragment_Sunday.EXTRA_START_TIME);
        String endTime = intent.getStringExtra(Fragment_Sunday.EXTRA_END_TIME);
        String semester = intent.getStringExtra(Fragment_Sunday.EXTRA_SEMESTER);
        String section = intent.getStringExtra(Fragment_Sunday.EXTRA_SECTION);
        String department = intent.getStringExtra(Fragment_Sunday.EXTRA_DEPARTMENT);
        String day = intent.getStringExtra(Fragment_Sunday.EXTRA_DAY);
        String subject = intent.getStringExtra(Fragment_Sunday.EXTRA_SUBJECT);
        String teacher = intent.getStringExtra(Fragment_Sunday.EXTRA_TEACHER);
        String routine = intent.getStringExtra(Fragment_Sunday.EXTRA_ROUTINE);
        String room = intent.getStringExtra(Fragment_Sunday.EXTRA_ROOM);

        cardStartTime.setText(startTime);
        cardEndTime.setText(endTime);
        cardSubject.setText(subject);
        cardTeacher.setText(teacher);
        cardRoutine.setText(routine);
        cardRoom.setText(room);
        cardSemester.setText(semester);
        cardSection.setText(section);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailsActivity.this, EditRoutine.class);
                intent.putExtra(EXTRA_ID, id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}
