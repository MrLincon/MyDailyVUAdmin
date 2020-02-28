package com.whitespace.mydailyvuadmin.Modify_Routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.whitespace.mydailyvuadmin.Activity.RoutineActivity;
import com.whitespace.mydailyvuadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditRoutine extends AppCompatActivity {


    private static final String TAG = "EditRoutine";
    FloatingActionButton saveClass;

    private Toolbar toolbar;
    private TextView toolbarTitle;

    Spinner spinnerSemester, spinnerSection, spinnerDepartment, spinnerDay, spinnerSubject, spinnerTeacher, spinnerTeacher2, spinnerRoutine, spinnerRoom;

    public String semester, section, department, day, subject, teacher1, teacher2, routine, room;

    LinearLayout btnStartTime, btnEndTime;
    TextView startTime, endTime;

    TextView cardStartTime, cardEndTime, cardSubject, cardTeacher, cardRoutine, cardRoom;

    String startTimeData;
    String endTimeData;
    String subjectData;
    String teacher1Data;
    String teacher2Data;
    String routineData;
    String roomData;
    String semesterData;
    String sectionData;
    String dayData;
    String departmentData;
    int orderHourData;
    int orderMinuteData;
    String am_pm_Data;

    CheckBox checkBoxT;
    public String T;
    private String id;

    private int CalendarHour, CalendarMinute;
    String format, am_pm;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private int orderInHour = 99;
    private int orderInMinute = 99;

    private DocumentReference document_ref;
    private FirebaseFirestore db;

    private static final String PREF_TEACHERS_NAME = "pref_teacherName";
    private static final String PREF_SEMESTER = "pref_semester";
    private static final String PREF_SEC = "pref_sec";
    private static final String PREF_ROUTINE_TYPE = "pref_routineType";
    private static final String PREF_DEPT = "pref_dept";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_routine);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Edit Class");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStartTime = findViewById(R.id.btnStartTime);
        btnEndTime = findViewById(R.id.btnEndTime);
        saveClass = findViewById(R.id.fabSunday);
        checkBoxT = findViewById(R.id.teacherCheckbox);

        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);

        cardStartTime = findViewById(R.id.startTime);
        cardEndTime = findViewById(R.id.endTime);
        cardSubject = findViewById(R.id.tvSubject);
        cardTeacher = findViewById(R.id.tvTeacher);
        cardRoutine = findViewById(R.id.tvRoutine);
        cardRoom = findViewById(R.id.tvRoom);

        final Intent intent = getIntent();
        id = intent.getStringExtra(DetailsActivity.EXTRA_ID);

        db = FirebaseFirestore.getInstance();
        document_ref = db.collection("Routine").document(id);

        loadData();
    }

    private void loadData() {

        spinnerSemester = findViewById(R.id.spinnerSemester);
        spinnerSemester.setGravity(Gravity.CENTER);
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
                cardSubject.setText(subject);
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
                teacher1 = parent.getItemAtPosition(position).toString();
                cardTeacher.setText(teacher1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTeacher2 = findViewById(R.id.spinnerTeacher2);
        spinnerTeacher2.setEnabled(false);
        final ArrayAdapter<CharSequence> teacher2Adapter = ArrayAdapter.createFromResource(this,
                R.array.teacher, android.R.layout.simple_spinner_item);
        teacher2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacher2.setAdapter(teacher2Adapter);
        spinnerTeacher2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacher2 = parent.getItemAtPosition(position).toString();
                if (teacher2.equals("Select teacher")) {
                    teacher2 = parent.getItemAtPosition(position).toString();
                } else {
                    cardTeacher.setText(teacher1 + " + " + teacher2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBoxT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    T = teacher1 + " + " + teacher2;
                    cardTeacher.setText(T);
                    spinnerTeacher2.setEnabled(true);
                } else {
                    T = teacher1;
                    cardTeacher.setText(T);
                    spinnerTeacher2.setSelection(0);
                    spinnerTeacher2.setEnabled(false);
                }
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
                cardRoutine.setText(routine);
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
                cardRoom.setText(room);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(EditRoutine.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                orderInHour = hourOfDay;
                                orderInMinute = minute;
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
                                cardStartTime.setText(startTime.getText().toString());
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });


        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);

                timepickerdialog = new TimePickerDialog(EditRoutine.this,
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
                                cardEndTime.setText(endTime.getText().toString());
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

        document_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    startTimeData = documentSnapshot.getString("startTime");
                    endTimeData = documentSnapshot.getString("endTime");
                    semesterData = documentSnapshot.getString("semester");
                    sectionData = documentSnapshot.getString("section");
                    departmentData = documentSnapshot.getString("department");
                    dayData = documentSnapshot.getString("day");
                    subjectData = documentSnapshot.getString("subject");
                    routineData = documentSnapshot.getString("routine");
                    roomData = documentSnapshot.getString("room");
                    orderHourData = documentSnapshot.getLong("orderHour").intValue();
                    orderMinuteData = documentSnapshot.getLong("orderMinute").intValue();
                    am_pm_Data = documentSnapshot.getString("am_pm");

                    List<String> teachersData = (List<String>) documentSnapshot.get("teachers");
                    ArrayList<String> TeachersData = (ArrayList<String>) teachersData;

                    try {
                        teacher1Data = TeachersData.get(0);
                        teacher2Data = TeachersData.get(1);
                    } catch (Exception e) {

                    }

                    if (startTimeData != null) {
                        startTime.setText(startTimeData);
                        cardStartTime.setText(startTimeData);
                    }
                    if (endTimeData != null) {
                        endTime.setText(endTimeData);
                        cardEndTime.setText(endTimeData);
                    }
                    if (semesterData != null) {
                        int spinnerPositionSem = semesterAdapter.getPosition(semesterData);
                        spinnerSemester.setSelection(spinnerPositionSem);
                    }
                    if (sectionData != null) {
                        int spinnerPositionSec = sectionAdapter.getPosition(sectionData);
                        spinnerSection.setSelection(spinnerPositionSec);
                    }
                    if (departmentData != null) {
                        int spinnerPositionDept = departmentAdapter.getPosition(departmentData);
                        spinnerDepartment.setSelection(spinnerPositionDept);
                    }
                    if (dayData != null) {
                        int spinnerPositionDay = dayAdapter.getPosition(dayData);
                        spinnerDay.setSelection(spinnerPositionDay);
                    }
                    if (subjectData != null) {
                        int spinnerPositionSub = subjectAdapter.getPosition(subjectData);
                        spinnerSubject.setSelection(spinnerPositionSub);
                    }
                    if (routineData != null) {
                        int spinnerPositionRoutine = routineAdapter.getPosition(routineData);
                        spinnerRoutine.setSelection(spinnerPositionRoutine);
                    }
                    if (roomData != null) {
                        int spinnerPositionRoom = roomAdapter.getPosition(roomData);
                        spinnerRoom.setSelection(spinnerPositionRoom);
                    }
                    if (teacher1Data != null) {
                        int spinnerPositionTeacher1 = teacherAdapter.getPosition(teacher1Data);
                        spinnerTeacher.setSelection(spinnerPositionTeacher1);
                    }
                    if (teacher2Data != null) {
                        int spinnerPositionTeacher2 = teacher2Adapter.getPosition(teacher2Data);
                        spinnerTeacher2.setSelection(spinnerPositionTeacher2);
                        checkBoxT.setChecked(true);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        saveClass.setOnClickListener(new View.OnClickListener() {
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
                if (checkBoxT.isChecked()) {
                    Teacher = teacher1 + " + " + teacher2;
                } else {
                    Teacher = teacher1;
                }
                final String Routine = routine;
                final String Room = room;
                int OrderHour = orderInHour;
                int OrderMinute = orderInMinute;
                String OrderAmPm = am_pm;

                if (OrderHour == 99) {
                    OrderHour = orderHourData;
                }
                if (OrderMinute == 99) {
                    OrderMinute = orderMinuteData;
                }
                if (OrderAmPm == null) {
                    OrderAmPm = am_pm_Data;
                }

                List<String> Teachers;
                if (teacher2.equals("Select teacher")) {
                    Teachers = Arrays.asList(teacher1);
                } else {
                    Teachers = Arrays.asList(teacher1, teacher2);
                }


                String card_start = cardStartTime.getText().toString();
                String card_end = cardEndTime.getText().toString();
                String card_teacher = cardTeacher.getText().toString();
                String card_subject = cardSubject.getText().toString();
                String card_room = cardRoom.getText().toString();
                String card_routine = cardRoutine.getText().toString();

                if (card_start.equals("-- : --")) {
                    Toast.makeText(getApplicationContext(), "You must enter class starting time", Toast.LENGTH_SHORT).show();
                    return;
                } else if (card_end.equals("-- : --")) {
                    Toast.makeText(getApplicationContext(), "You must enter class ending time", Toast.LENGTH_SHORT).show();
                    return;
                } else if (semester.equals("Select semester")) {
                    Toast.makeText(getApplicationContext(), "You must select a semester", Toast.LENGTH_SHORT).show();
                    return;
                } else if (section.equals("Select section")) {
                    Toast.makeText(getApplicationContext(), "You must select a section", Toast.LENGTH_SHORT).show();
                    return;
                } else if (card_routine.equals("Select routine")) {
                    Toast.makeText(getApplicationContext(), "You must select a routine", Toast.LENGTH_SHORT).show();
                    return;
                } else if (card_subject.equals("Select subject")) {
                    Toast.makeText(getApplicationContext(), "You must select a subject", Toast.LENGTH_SHORT).show();
                    return;
                } else if (card_teacher.equals("Select teacher")) {
                    Toast.makeText(getApplicationContext(), "You must select a teacher", Toast.LENGTH_SHORT).show();
                    return;
                } else if (card_room.equals("Select room")) {
                    Toast.makeText(getApplicationContext(), "You must select a room", Toast.LENGTH_SHORT).show();
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
                    userMap.put("teachers", Teachers);
                    userMap.put("routine", Routine);
                    userMap.put("room", Room);
                    userMap.put("orderHour", OrderHour);
                    userMap.put("orderMinute", OrderMinute);
                    userMap.put("am_pm", OrderAmPm);
                    userMap.put("ad_id", ad_id);
                    userMap.put("timestamp", FieldValue.serverTimestamp());
                    document_ref.update(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            spinnerSubject.setSelection(0);
                            spinnerTeacher.setSelection(0);
                            spinnerRoutine.setSelection(0);
                            spinnerRoom.setSelection(0);
                            startTime.setText("-- : -- --");
                            endTime.setText("-- : -- --");
                            Toast.makeText(EditRoutine.this, "Class Added", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditRoutine.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(EditRoutine.this, RoutineActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_class_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_post:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditRoutine.this);
                builder.setTitle("Are you sure?")
                        .setMessage("If you delete this, this routine will no longer be shown in the home page!")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                document_ref.delete();
                                Intent intent = new Intent(EditRoutine.this,RoutineActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
