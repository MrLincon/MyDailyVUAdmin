package com.example.mydailyvuadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddClass extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    Spinner spinnerSemester, spinnerSection, spinnerDepartment, spinnerDay, spinnerSubject, spinnerTeacher, spinnerTeacher2, spinnerRoutine, spinnerRoom;

    public String semester, section, department, day, subject, teacher, teacher2, routine, room;

    Button StartBtn, EndBtn, SaveBtn;
    TextView startTime, endTime;

    CheckBox T2;

    private int CalendarHour, CalendarMinute;
    String format, am_pm;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    public String orderInHour, orderInMinute;

    private DocumentReference document_ref;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        toolbar = findViewById(R.id.toolbarAddClass);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Add Class");

        StartBtn = findViewById(R.id.startTime);
        EndBtn = findViewById(R.id.endTime);
        SaveBtn = findViewById(R.id.save);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);
        T2 = findViewById(R.id.teacherCheckbox);

        db = FirebaseFirestore.getInstance();
        document_ref = db.collection("CSE").document();

        loadData();
    }


    private void loadData() {

//       String[] s = {"1","2","3","4","5"};
        String[] s = getResources().getStringArray(R.array.semester);

        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSemester.setGravity(Gravity.CENTER);
//        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(this,
//                R.layout.spinner_item, R.id.spinnerItem, s);
        final ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(semesterAdapter);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSection = findViewById(R.id.spinnerSection);
        final ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this,
                R.array.section, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);
        spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        final ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(departmentAdapter);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDay = findViewById(R.id.spinnerDay);
        final ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSubject = findViewById(R.id.spinnerSubject);
        final ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(this,
                R.array.subject, android.R.layout.simple_spinner_item);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(subjectAdapter);
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTeacher = findViewById(R.id.spinnerTeacher);
        final ArrayAdapter<CharSequence> teacherAdapter = ArrayAdapter.createFromResource(this,
                R.array.teacher, android.R.layout.simple_spinner_item);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher.setAdapter(teacherAdapter);
        spinnerTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacher = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTeacher2 = findViewById(R.id.spinnerTeacher2);
        spinnerTeacher2.setEnabled(false);
        T2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spinnerTeacher2.setEnabled(true);
                } else {
                    spinnerTeacher2.setEnabled(false);
                }
            }
        });

        final ArrayAdapter<CharSequence> teacher2Adapter = ArrayAdapter.createFromResource(this,
                R.array.teacher, android.R.layout.simple_spinner_item);
        teacher2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher2.setAdapter(teacher2Adapter);
        spinnerTeacher2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacher2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerRoutine = findViewById(R.id.spinnerRoutine);


        String[] str = getResources().getStringArray(R.array.routine);

        final ArrayAdapter<CharSequence> routineAdapter = new ArrayAdapter<CharSequence>(this, 
                android.R.layout.simple_spinner_item, str);
        routineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoutine.setAdapter(routineAdapter);
        spinnerRoutine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                routine = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerRoom = findViewById(R.id.spinnerRoom);
        final ArrayAdapter<CharSequence> roomAdapter = ArrayAdapter.createFromResource(this,
                R.array.room, android.R.layout.simple_spinner_item);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(AddClass.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                orderInHour = String.valueOf(hourOfDay);
                                orderInMinute = String.valueOf(minute);
                                if (hourOfDay == 0) {
                                    hourOfDay = hourOfDay + 12;
                                    format = "AM";
                                    am_pm = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                    am_pm = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                    format = "PM";
                                    am_pm = "PM";
                                } else {
                                    format = "AM";
                                    am_pm = "AM";
                                }
                                startTime.setText(String.format("%02d:%02d", hourOfDay, minute) + " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

        EndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(AddClass.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay = hourOfDay + 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay = hourOfDay - 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                endTime.setText(String.format("%02d:%02d", hourOfDay, minute) + " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Semester = semester;
                final String Section = section;
                final String Department = department;
                final String Day = day;
                final String StartTime = startTime.getText().toString();
                final String EndTime = endTime.getText().toString();
                final String Subject = subject;
                final String Teacher;
                if (T2.isChecked()) {
                    Teacher = teacher + " + " + teacher2;
                } else {
                    Teacher = teacher;
                }
                final String Routine = routine;
                final String Room = room;
                final String OrderHour = orderInHour;
                final String OrderMinute = orderInMinute;
                final String OrderAmPm = am_pm;

                if (StartTime.equals("-- : -- --") || EndTime.equals("-- : -- --")) {
                    Toast.makeText(getApplicationContext(), "You must enter a class time", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    final String ad_id = document_ref.getId();
                    Map<String, Object> userMap = new HashMap<>();

                    userMap.put("semester", Semester);
                    userMap.put("section", Section);
                    userMap.put("department", Department);
                    userMap.put("day", Day);
                    userMap.put("startTime", StartTime);
                    userMap.put("endTime", EndTime);
                    userMap.put("subject", Subject);
                    userMap.put("teacher", Teacher);
                    userMap.put("routine", Routine);
                    userMap.put("room", Room);
                    userMap.put("orderHour", OrderHour);
                    userMap.put("orderMinute", OrderMinute);
                    userMap.put("am_pm", OrderAmPm);
//                    userMap.put("user_id", userID);
                    userMap.put("ad_id", ad_id);
                    userMap.put("timestamp", FieldValue.serverTimestamp());
                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            spinnerSemester.setSelection(0);
                            spinnerSection.setSelection(0);
                            spinnerDay.setSelection(0);
                            spinnerSubject.setSelection(0);
                            spinnerTeacher.setSelection(0);
                            spinnerRoutine.setSelection(0);
                            spinnerRoom.setSelection(0);
                            startTime.setText("-- : -- --");
                            endTime.setText("-- : -- --");
                            Toast.makeText(AddClass.this, "Class Added", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddClass.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent save = new Intent(AddClass.this, AddClass.class);
                    startActivity(save);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            }
        });

    }

}