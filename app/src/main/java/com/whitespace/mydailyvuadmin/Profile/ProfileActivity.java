package com.whitespace.mydailyvuadmin.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.whitespace.mydailyvuadmin.R;

public class ProfileActivity extends AppCompatActivity {

    TextView tvSemester, tvSection, tvID, tvDesignation, tvOffice, tvCounselingHour, tvContact;
    TextView name, email, id, designation, office, counselingHour, contact, department, semester, section;
    View viewSemester, viewSection, viewDesignation, viewCounseling, viewOffice, viewContact, viewID;

    CheckBox teacherCheckbox, crCheckbox, studentCheckbox;

    private Toolbar toolbar;
    private TextView toolbarTitle;
    ProgressBar progressBar;

    SharedPreferences preferences;
    boolean statusT, statusC, statusS;

    FloatingActionButton fab;

    private CircularImageView imageView;

    private FirebaseAuth mAuth;
    private String userID;

    private FirebaseFirestore db;
    private DocumentReference document_reference;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.edit_cr_profile_toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Profile");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        progressBar = findViewById(R.id.progress_loading);

        tvSemester = findViewById(R.id.tvSemester);
        tvSection = findViewById(R.id.tvSection);
        tvID = findViewById(R.id.tvID);
        tvDesignation = findViewById(R.id.tvDesignation);
        tvOffice = findViewById(R.id.tvOffice);
        tvCounselingHour = findViewById(R.id.tvCounselingHour);
        tvContact = findViewById(R.id.tvContact);

        viewSemester = findViewById(R.id.viewSemester);
        viewSection = findViewById(R.id.viewSection);
        viewID = findViewById(R.id.viewID);
        viewDesignation = findViewById(R.id.viewDesignation);
        viewCounseling = findViewById(R.id.viewCounseling_hour);
        viewOffice = findViewById(R.id.viewOffice);
        viewContact = findViewById(R.id.viewContact);

        imageView = findViewById(R.id.add_image);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
            }
        });

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        department = findViewById(R.id.department);
        semester = findViewById(R.id.semester);
        section = findViewById(R.id.section);
        designation = findViewById(R.id.designation);
        office = findViewById(R.id.office);
        counselingHour = findViewById(R.id.counseling_hour);
        contact = findViewById(R.id.contact);

        teacherCheckbox = findViewById(R.id.teacher);
        crCheckbox = findViewById(R.id.cr);
        studentCheckbox = findViewById(R.id.student);

        teacherCheckbox.setEnabled(false);
        crCheckbox.setEnabled(false);
        studentCheckbox.setEnabled(false);

        progressBar.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        document_reference = db.collection("Profiles").document(userID);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profiles").child(userID);

        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    progressBar.setVisibility(View.VISIBLE);


                    String Name = documentSnapshot.getString("name");
                    String Email = documentSnapshot.getString("email");
                    String ID = documentSnapshot.getString("id");
                    String Department = documentSnapshot.getString("department");
                    String Semester = documentSnapshot.getString("semester");
                    String Section = documentSnapshot.getString("section");
                    String Contact = documentSnapshot.getString("contact");
                    String Designation = documentSnapshot.getString("designation");
                    String Office = documentSnapshot.getString("office");
                    String Counseling = documentSnapshot.getString("counseling_hour");
                    String STATUS = documentSnapshot.getString("status");
                    String url = documentSnapshot.getString("imageUrl");



                    if (STATUS.equals("Teacher")){
                        statusT = true;
                        name.setText(Name);
                        email.setText(Email);
                        department.setText(Department);
                        designation.setText(Designation);
                        office.setText(Office);
                        counselingHour.setText(Counseling);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);


                    }if (STATUS.equals("CR")){
                        statusC = true;
                        name.setText(Name);
                        email.setText(Email);
                        department.setText(Department);
                        semester.setText(Semester);
                        section.setText(Section);
                        id.setText(ID);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);

                    }if (STATUS.equals("Student")){
                        statusS = true;
                        name.setText(Name);
                        email.setText(Email);
                        department.setText(Department);
                        semester.setText(Semester);
                        section.setText(Section);
                        id.setText(ID);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);

                    }

                    if (statusT == true){
                        teacherCheckbox.setChecked(true);
                        teacherCheckbox.setEnabled(false);
                        tvDesignation.setVisibility(View.VISIBLE);
                        tvOffice.setVisibility(View.VISIBLE);
                        tvCounselingHour.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        designation.setVisibility(View.VISIBLE);
                        office.setVisibility(View.VISIBLE);
                        counselingHour.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

                        viewDesignation.setVisibility(View.VISIBLE);
                        viewOffice.setVisibility(View.VISIBLE);
                        viewCounseling.setVisibility(View.VISIBLE);
                        viewContact.setVisibility(View.VISIBLE);

                        crCheckbox.setVisibility(View.GONE);
                        studentCheckbox.setVisibility(View.GONE);

                    }if (statusC == true){
                        crCheckbox.setChecked(true);
                        crCheckbox.setEnabled(false);
                        tvSemester.setVisibility(View.VISIBLE);
                        tvSection.setVisibility(View.VISIBLE);
                        tvID.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        semester.setVisibility(View.VISIBLE);
                        section.setVisibility(View.VISIBLE);
                        id.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

                        viewSemester.setVisibility(View.VISIBLE);
                        viewSection.setVisibility(View.VISIBLE);
                        viewID.setVisibility(View.VISIBLE);
                        viewContact.setVisibility(View.VISIBLE);

                        teacherCheckbox.setVisibility(View.GONE);
                        studentCheckbox.setVisibility(View.GONE);
                    }if (statusS == true){
                        studentCheckbox.setChecked(true);
                        studentCheckbox.setEnabled(false);
                        tvSemester.setVisibility(View.VISIBLE);
                        tvSection.setVisibility(View.VISIBLE);
                        tvID.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        semester.setVisibility(View.VISIBLE);
                        section.setVisibility(View.VISIBLE);
                        id.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

                        viewSemester.setVisibility(View.VISIBLE);
                        viewSection.setVisibility(View.VISIBLE);
                        viewID.setVisibility(View.VISIBLE);
                        viewContact.setVisibility(View.VISIBLE);

                        teacherCheckbox.setVisibility(View.GONE);
                        crCheckbox.setVisibility(View.GONE);
                    }
                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_profile:
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Are you sure?")
                .setMessage("If you delete this, it  will no longer be available in My Daily VU's database!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        document_reference.delete();
                        mStorageRef.delete();
                        Picasso.get().load(R.drawable.user_default).into(imageView);
                        name.setText("");
                        email.setText("");
                        id.setText("");
                        department.setText("");
                        semester.setText("");
                        section.setText("");
                        counselingHour.setText("");
                        office.setText("");
                        contact.setText("");

                        teacherCheckbox.setChecked(false);
                        crCheckbox.setChecked(false);
                        studentCheckbox.setChecked(false);

                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

        @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
