package com.whitespace.mydailyvuadmin.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.whitespace.mydailyvuadmin.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class EditProfileActivity extends AppCompatActivity {

    TextView tvSemester, tvSection, tvID, tvDesignation, tvOffice, tvCounselingHour, tvContact;
    Spinner department_spinner, semester_spinner, section_spinner;
    EditText name, email, id, designation, office, counselingHour, contact;
    String user_department, user_semester, user_section;

    CheckBox teacherCheckbox, crCheckbox, studentCheckbox;

    String Status;

    private Toolbar toolbar;
    private TextView toolbarTitle;
    ProgressBar progressBar;

    SharedPreferences preferences;
    boolean statusT, statusC, statusS;

    TextView add_image;

    private CircularImageView imageView;

    private FirebaseAuth mAuth;
    private String userID;
    private String image_link;

    Dialog popup;

    private FirebaseFirestore db;

    private StorageReference mStorageRef;
    private DocumentReference document_reference;
    private DocumentReference document_ref;
    private StorageReference imageName;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;
    private Uri resultUri;

    Bitmap bitmap;

    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.edit_cr_profile_toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Edit Profile");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        popup = new Dialog(this);

        progressBar = findViewById(R.id.progress_loading);

        tvSemester = findViewById(R.id.tvSemester);
        tvSection = findViewById(R.id.tvSection);
        tvID = findViewById(R.id.tvID);
        tvDesignation = findViewById(R.id.tvDesignation);
        tvOffice = findViewById(R.id.tvOffice);
        tvCounselingHour = findViewById(R.id.tvCounselingHour);
        tvContact = findViewById(R.id.tvContact);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        designation = findViewById(R.id.designation);
        office = findViewById(R.id.office);
        counselingHour = findViewById(R.id.counseling_hour);
        contact = findViewById(R.id.contact);

        teacherCheckbox = findViewById(R.id.teacher);
        crCheckbox = findViewById(R.id.cr);
        studentCheckbox = findViewById(R.id.student);

        add_image = findViewById(R.id.tv_add_image);
        imageView = findViewById(R.id.add_image);

        preferences = this.getSharedPreferences("Status", this.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        department_spinner = findViewById(R.id.department);
        final ArrayAdapter<CharSequence> departmentAdapted = ArrayAdapter.createFromResource(this,
                R.array.department, android.R.layout.simple_spinner_item);
        departmentAdapted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department_spinner.setAdapter(departmentAdapted);
        department_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        semester_spinner = findViewById(R.id.semester);
        final ArrayAdapter<CharSequence> semesterAdapted = ArrayAdapter.createFromResource(this,
                R.array.semester, android.R.layout.simple_spinner_item);
        semesterAdapted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester_spinner.setAdapter(semesterAdapted);
        semester_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_semester = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        section_spinner = findViewById(R.id.section);
        final ArrayAdapter<CharSequence> sectionAdapted = ArrayAdapter.createFromResource(this,
                R.array.section, android.R.layout.simple_spinner_item);
        sectionAdapted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section_spinner.setAdapter(sectionAdapted);
        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_section = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        SharedPreferences mypreferences = this.getSharedPreferences("Status", this.MODE_PRIVATE);
//
//        statusT = mypreferences.getBoolean("teacher", false);
//        statusC = mypreferences.getBoolean("cr", false);
//        statusS = mypreferences.getBoolean("student", false);
//        if (statusT == true) {
//            teacherCheckbox.setChecked(true);
//            tvDesignation.setVisibility(View.VISIBLE);
//            tvOffice.setVisibility(View.VISIBLE);
//            tvCounselingHour.setVisibility(View.VISIBLE);
//            tvContact.setVisibility(View.VISIBLE);
//
//            designation.setVisibility(View.VISIBLE);
//            office.setVisibility(View.VISIBLE);
//            counselingHour.setVisibility(View.VISIBLE);
//            contact.setVisibility(View.VISIBLE);
//
//            crCheckbox.setVisibility(View.GONE);
//            studentCheckbox.setVisibility(View.GONE);
//        }
//        if (statusC == true) {
//            crCheckbox.setChecked(true);
//            tvSemester.setVisibility(View.VISIBLE);
//            tvSection.setVisibility(View.VISIBLE);
//            tvID.setVisibility(View.VISIBLE);
//            tvContact.setVisibility(View.VISIBLE);
//
//            semester_spinner.setVisibility(View.VISIBLE);
//            section_spinner.setVisibility(View.VISIBLE);
//            id.setVisibility(View.VISIBLE);
//            contact.setVisibility(View.VISIBLE);
//
//            teacherCheckbox.setVisibility(View.GONE);
//            studentCheckbox.setVisibility(View.GONE);
//        }
//        if (statusS == true) {
//            studentCheckbox.setChecked(true);
//            tvSemester.setVisibility(View.VISIBLE);
//            tvSection.setVisibility(View.VISIBLE);
//            tvID.setVisibility(View.VISIBLE);
//            tvContact.setVisibility(View.VISIBLE);
//
//            semester_spinner.setVisibility(View.VISIBLE);
//            section_spinner.setVisibility(View.VISIBLE);
//            id.setVisibility(View.VISIBLE);
//            contact.setVisibility(View.VISIBLE);
//
//            teacherCheckbox.setVisibility(View.GONE);
//            crCheckbox.setVisibility(View.GONE);
//        }

        teacherCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (teacherCheckbox.isChecked()) {

                    String Designation = designation.getText().toString();

                    if (Designation.equals("")) {
                        popup.setContentView(R.layout.popup_profile_info);
                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        LinearLayout select_routine = popup.findViewById(R.id.select_routine);
                        select_routine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });
                        ImageView close = popup.findViewById(R.id.close_popup);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });
                        TextView message = popup.findViewById(R.id.textView2);
                        message.setText("If you create your profile under this section, everyone will be able to see your profile under 'CR's Panel'");
                        popup.show();
                    }

                    tvDesignation.setVisibility(View.VISIBLE);
                    tvOffice.setVisibility(View.VISIBLE);
                    tvCounselingHour.setVisibility(View.VISIBLE);
                    tvContact.setVisibility(View.VISIBLE);

                    designation.setVisibility(View.VISIBLE);
                    office.setVisibility(View.VISIBLE);
                    counselingHour.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.VISIBLE);

                    crCheckbox.setVisibility(View.GONE);
                    studentCheckbox.setVisibility(View.GONE);

                    semester_spinner.setSelection(0);
                    section_spinner.setSelection(0);
                    id.setText("");

                    Status = "Teacher";
                } else {
                    tvDesignation.setVisibility(View.GONE);
                    tvOffice.setVisibility(View.GONE);
                    tvCounselingHour.setVisibility(View.GONE);
                    tvContact.setVisibility(View.GONE);

                    designation.setVisibility(View.GONE);
                    office.setVisibility(View.GONE);
                    counselingHour.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);

                    crCheckbox.setVisibility(View.VISIBLE);
                    studentCheckbox.setVisibility(View.VISIBLE);

                    semester_spinner.setSelection(0);
                    section_spinner.setSelection(0);
                    id.setText("");

                    designation.setText("");
                    office.setText("");
                    counselingHour.setText("");
                    contact.setText("");

                    Status = "";
                }

            }
        });

        crCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (crCheckbox.isChecked()) {

                    String ID = id.getText().toString();

                    if (ID.equals("")) {
                        popup.setContentView(R.layout.popup_profile_info);
                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        LinearLayout select_routine = popup.findViewById(R.id.select_routine);
                        select_routine.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });
                        ImageView close = popup.findViewById(R.id.close_popup);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });
                        TextView message = popup.findViewById(R.id.textView2);
                        message.setText("If you create your profile under this section, everyone will be able to see your profile under 'CR's Panel'");
                        popup.show();
                    }

                    tvSemester.setVisibility(View.VISIBLE);
                    tvSection.setVisibility(View.VISIBLE);
                    tvID.setVisibility(View.VISIBLE);
                    tvContact.setVisibility(View.VISIBLE);

                    semester_spinner.setVisibility(View.VISIBLE);
                    section_spinner.setVisibility(View.VISIBLE);
                    id.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.VISIBLE);

                    teacherCheckbox.setVisibility(View.GONE);
                    studentCheckbox.setVisibility(View.GONE);

                    designation.setText("");
                    counselingHour.setText("");
                    office.setText("");

                    Status = "CR";
                } else {
                    tvSemester.setVisibility(View.GONE);
                    tvSection.setVisibility(View.GONE);
                    tvID.setVisibility(View.GONE);
                    tvContact.setVisibility(View.GONE);

                    semester_spinner.setVisibility(View.GONE);
                    section_spinner.setVisibility(View.GONE);
                    id.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);

                    teacherCheckbox.setVisibility(View.VISIBLE);
                    studentCheckbox.setVisibility(View.VISIBLE);

                    designation.setText("");
                    counselingHour.setText("");
                    office.setText("");

                    semester_spinner.setSelection(0);
                    section_spinner.setSelection(0);
                    id.setText("");
                    contact.setText("");

                    Status = "";
                }
            }
        });

        studentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (studentCheckbox.isChecked()) {
                    tvSemester.setVisibility(View.VISIBLE);
                    tvSection.setVisibility(View.VISIBLE);
                    tvID.setVisibility(View.VISIBLE);
                    tvContact.setVisibility(View.VISIBLE);

                    semester_spinner.setVisibility(View.VISIBLE);
                    section_spinner.setVisibility(View.VISIBLE);
                    id.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.VISIBLE);

                    teacherCheckbox.setVisibility(View.GONE);
                    crCheckbox.setVisibility(View.GONE);

                    designation.setText("");
                    counselingHour.setText("");
                    office.setText("");

                    Status = "Student";
                } else {
                    tvSemester.setVisibility(View.GONE);
                    tvSection.setVisibility(View.GONE);
                    tvID.setVisibility(View.GONE);
                    tvContact.setVisibility(View.GONE);

                    semester_spinner.setVisibility(View.GONE);
                    section_spinner.setVisibility(View.GONE);
                    id.setVisibility(View.GONE);
                    contact.setVisibility(View.GONE);

                    teacherCheckbox.setVisibility(View.VISIBLE);
                    crCheckbox.setVisibility(View.VISIBLE);

                    designation.setText("");
                    counselingHour.setText("");
                    office.setText("");

                    semester_spinner.setSelection(0);
                    section_spinner.setSelection(0);
                    id.setText("");
                    contact.setText("");

                    Status = "";
                }

            }
        });


        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userImage();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userImage();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profiles");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        progressBar.setVisibility(View.GONE);

        document_reference = db.collection("Profiles").document(userID);

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

                    if (STATUS.equals("Teacher")) {
                        statusT = true;
                        name.setText(Name);
                        email.setText(Email);
                        designation.setText(Designation);
                        office.setText(Office);
                        counselingHour.setText(Counseling);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);

                        if (Department != null) {
                            int spinnerPosition = departmentAdapted.getPosition(Department);
                            department_spinner.setSelection(spinnerPosition);
                        }

                    }
                    if (STATUS.equals("CR")) {
                        statusC = true;
                        name.setText(Name);
                        email.setText(Email);
                        id.setText(ID);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);

                        if (Department != null) {
                            int spinnerPosition = departmentAdapted.getPosition(Department);
                            department_spinner.setSelection(spinnerPosition);
                        }
                        if (Semester != null) {
                            int spinnerPosition = semesterAdapted.getPosition(Semester);
                            semester_spinner.setSelection(spinnerPosition);
                        }
                        if (Section != null) {
                            int spinnerPosition = sectionAdapted.getPosition(Section);
                            section_spinner.setSelection(spinnerPosition);
                        }

                    }
                    if (STATUS.equals("Student")) {
                        statusS = true;
                        name.setText(Name);
                        email.setText(Email);
                        id.setText(ID);
                        contact.setText(Contact);
                        Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageView);

                        if (Department != null) {
                            int spinnerPosition = departmentAdapted.getPosition(Department);
                            department_spinner.setSelection(spinnerPosition);
                        }
                        if (Semester != null) {
                            int spinnerPosition = semesterAdapted.getPosition(Semester);
                            semester_spinner.setSelection(spinnerPosition);
                        }
                        if (Section != null) {
                            int spinnerPosition = sectionAdapted.getPosition(Section);
                            section_spinner.setSelection(spinnerPosition);
                        }
                    }

                    if (statusT == true) {
                        teacherCheckbox.setEnabled(true);
                        teacherCheckbox.setChecked(true);
                        tvDesignation.setVisibility(View.VISIBLE);
                        tvOffice.setVisibility(View.VISIBLE);
                        tvCounselingHour.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        designation.setVisibility(View.VISIBLE);
                        office.setVisibility(View.VISIBLE);
                        counselingHour.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

                        crCheckbox.setVisibility(View.GONE);
                        studentCheckbox.setVisibility(View.GONE);
                    }
                    if (statusC == true) {
                        crCheckbox.setChecked(true);
                        tvSemester.setVisibility(View.VISIBLE);
                        tvSection.setVisibility(View.VISIBLE);
                        tvID.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        semester_spinner.setVisibility(View.VISIBLE);
                        section_spinner.setVisibility(View.VISIBLE);
                        id.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

                        teacherCheckbox.setVisibility(View.GONE);
                        studentCheckbox.setVisibility(View.GONE);
                    }
                    if (statusS == true) {
                        studentCheckbox.setChecked(true);
                        tvSemester.setVisibility(View.VISIBLE);
                        tvSection.setVisibility(View.VISIBLE);
                        tvID.setVisibility(View.VISIBLE);
                        tvContact.setVisibility(View.VISIBLE);

                        semester_spinner.setVisibility(View.VISIBLE);
                        section_spinner.setVisibility(View.VISIBLE);
                        id.setVisibility(View.VISIBLE);
                        contact.setVisibility(View.VISIBLE);

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

    private void userImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                resultUri = result.getUri();

                Picasso.get().load(resultUri).error(R.drawable.add_user_pic).into(imageView);

                File compressed = new File(resultUri.getPath());

                try {
                    bitmap = new Compressor(this
                    )
                            .setQuality(10)
                            .compressToBitmap(compressed);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                final byte[] image_byte = byteArrayOutputStream.toByteArray();


                imageName = mStorageRef.child(userID);

                uploadTask = imageName.putBytes(image_byte);

            }

        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = croppedImage.getError();
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }


    private void updateDetails() {

        final String Name = name.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Department = user_department;
        final String Semester = user_semester;
        final String Section = user_section;
        final String ID = id.getText().toString().trim();
        final String Designation = designation.getText().toString().trim();
        final String Counseling = counselingHour.getText().toString().trim();
        final String Office = office.getText().toString().trim();
        final String Contact = contact.getText().toString().trim();

        if (teacherCheckbox.isChecked()) {
            if (Name.isEmpty()) {
                name.setError("Field must be filled");
                return;
            }
            if (Email.isEmpty()) {
                email.setError("Field must be filled");
                return;
            }
            if (Designation.isEmpty()) {
                designation.setError("Field must be filled");
                return;
            }
            if (Counseling.isEmpty()) {
                counselingHour.setError("Field must be filled");
                return;
            }
            if (Office.isEmpty()) {
                office.setError("Field must be filled");
                return;
            }
            if (Contact.isEmpty()) {
                contact.setError("Field must be filled");
                return;
            }
             else {

                document_ref = db.collection("Profiles").document(userID);

                if (uploadTask != null) {

                    final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                    progressDialog.setTitle("Uploading Photo");
                    progressDialog.setMessage("Please wait a few seconds!");
                    progressDialog.show();

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    image_link = uri.toString();

                                    progressDialog.setTitle("Updating");
                                    progressDialog.setMessage("Please wait a few seconds!");

                                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                    document_ref.update("imageUrl", image_link,
                                                            "name", Name,
                                                            "email", Email,
                                                            "department", Department,
                                                            "designation", Designation,
                                                            "office", Office,
                                                            "counseling_hour", Counseling,
                                                            "contact", Contact,
                                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                } else {
                                                    Map<String, Object> userMap = new HashMap<>();

                                                    userMap.put("imageUrl", image_link);
                                                    userMap.put("name", Name);
                                                    userMap.put("email", Email);
                                                    userMap.put("department", Department);
                                                    userMap.put("designation", Designation);
                                                    userMap.put("office", Office);
                                                    userMap.put("counseling_hour", Counseling);
                                                    userMap.put("contact", Contact);
                                                    userMap.put("status", Status);

                                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                }
                                            }


                                        }
                                    });

                                }
                            });
                        }
                    });
                } else {
                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    document_ref.update("name", Name,
                                            "email", Email,
                                            "department", Department,
                                            "designation", Designation,
                                            "office", Office,
                                            "counseling_hour", Counseling,
                                            "contact", Contact,
                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                } else {
                                    Map<String, Object> userMap = new HashMap<>();

                                    userMap.put("name", Name);
                                    userMap.put("email", Email);
                                    userMap.put("department", Department);
                                    userMap.put("designation", Designation);
                                    userMap.put("office", Office);
                                    userMap.put("counseling_hour", Counseling);
                                    userMap.put("contact", Contact);
                                    userMap.put("status", Status);

                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                }
                            }


                        }
                    });
                }

            }
        } else if (crCheckbox.isChecked()) {
            if (Name.isEmpty()) {
                name.setError("Field must be filled");
                return;
            }
            if (Email.isEmpty()) {
                email.setError("Field must be filled");
                return;
            }
            if (Section.equals("Select section")) {
                Toast.makeText(this, "Select your section", Toast.LENGTH_SHORT).show();
            }
            if (Semester.equals("Select semester")) {
                Toast.makeText(this, "Select your semester", Toast.LENGTH_SHORT).show();
            }
            if (ID.isEmpty()) {
                id.setError("Field must be filled");
                return;
            }
            if (Contact.isEmpty()) {
                contact.setError("Field must be filled");
                return;
            }
             else {

                document_ref = db.collection("Profiles").document(userID);

                if (uploadTask != null) {

                    final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                    progressDialog.setTitle("Uploading Photo");
                    progressDialog.setMessage("Please wait a few seconds!");
                    progressDialog.show();

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    image_link = uri.toString();

                                    progressDialog.setTitle("Updating");
                                    progressDialog.setMessage("Please wait a few seconds!");

                                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                    document_ref.update("imageUrl", image_link,
                                                            "name", Name,
                                                            "email", Email,
                                                            "department", Department,
                                                            "semester", Semester,
                                                            "section", Section,
                                                            "id", ID,
                                                            "contact", Contact,
                                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                } else {
                                                    Map<String, Object> userMap = new HashMap<>();

                                                    userMap.put("imageUrl", image_link);
                                                    userMap.put("name", Name);
                                                    userMap.put("email", Email);
                                                    userMap.put("department", Department);
                                                    userMap.put("semester", Semester);
                                                    userMap.put("section", Section);
                                                    userMap.put("id", ID);
                                                    userMap.put("contact", Contact);
                                                    userMap.put("status", Status);

                                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                }
                                            }


                                        }
                                    });

                                }
                            });
                        }
                    });
                } else {
                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    document_ref.update("name", Name,
                                            "email", Email,
                                            "department", Department,
                                            "semester", Semester,
                                            "section", Section,
                                            "id", ID,
                                            "contact", Contact,
                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                } else {
                                    Map<String, Object> userMap = new HashMap<>();

                                    userMap.put("name", Name);
                                    userMap.put("email", Email);
                                    userMap.put("department", Department);
                                    userMap.put("semester", Semester);
                                    userMap.put("section", Section);
                                    userMap.put("id", ID);
                                    userMap.put("contact", Contact);
                                    userMap.put("status", Status);

                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                }
                            }


                        }
                    });
                }

            }
        } else if (studentCheckbox.isChecked()) {
            if (Name.isEmpty()) {
                name.setError("Field must be filled");
                return;
            }
            if (Email.isEmpty()) {
                email.setError("Field must be filled");
                return;
            }
            if (Section.equals("Select section")) {
                Toast.makeText(this, "Select your section", Toast.LENGTH_SHORT).show();
            }
            if (Semester.equals("Select semester")) {
                Toast.makeText(this, "Select your semester", Toast.LENGTH_SHORT).show();
            }
            if (ID.isEmpty()) {
                id.setError("Field must be filled");
                return;
            }
            if (Contact.isEmpty()) {
                contact.setError("Field must be filled");
                return;
            } else {

                document_ref = db.collection("Profiles").document(userID);

                if (uploadTask != null) {

                    final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                    progressDialog.setTitle("Uploading Photo");
                    progressDialog.setMessage("Please wait a few seconds!");
                    progressDialog.show();

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    image_link = uri.toString();

                                    progressDialog.setTitle("Updating");
                                    progressDialog.setMessage("Please wait a few seconds!");

                                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                                    document_ref.update("imageUrl", image_link,
                                                            "name", Name,
                                                            "email", Email,
                                                            "department", Department,
                                                            "semester", Semester,
                                                            "section", Section,
                                                            "id", ID,
                                                            "contact", Contact,
                                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                } else {
                                                    Map<String, Object> userMap = new HashMap<>();

                                                    userMap.put("imageUrl", image_link);
                                                    userMap.put("name", Name);
                                                    userMap.put("email", Email);
                                                    userMap.put("department", Department);
                                                    userMap.put("semester", Semester);
                                                    userMap.put("section", Section);
                                                    userMap.put("id", ID);
                                                    userMap.put("contact", Contact);
                                                    userMap.put("status", Status);

                                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                }
                                            }


                                        }
                                    });

                                }
                            });
                        }
                    });
                } else {
                    document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot != null && documentSnapshot.exists()) {

                                    document_ref.update("name", Name,
                                            "email", Email,
                                            "department", Department,
                                            "semester", Semester,
                                            "section", Section,
                                            "id", ID,
                                            "contact", Contact,
                                            "status", Status).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                } else {
                                    Map<String, Object> userMap = new HashMap<>();

                                    userMap.put("name", Name);
                                    userMap.put("email", Email);
                                    userMap.put("department", Department);
                                    userMap.put("semester", Semester);
                                    userMap.put("section", Section);
                                    userMap.put("id", ID);
                                    userMap.put("contact", Contact);
                                    userMap.put("status", Status);

                                    document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    Intent save = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                    startActivity(save);
                                    finish();
                                }
                            }


                        }
                    });
                }

            }
        } else {
            Toast.makeText(this, "Please choose a status!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_details:
                updateDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
