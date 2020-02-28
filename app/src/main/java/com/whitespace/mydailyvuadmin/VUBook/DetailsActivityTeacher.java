package com.whitespace.mydailyvuadmin.VUBook;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.whitespace.mydailyvuadmin.R;

import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_CONTACT;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_COUNSELING_HOUR;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_DEPARTMENT;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_DESIGNATION;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_EMAIL;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_IMAGE_URL;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_NAME;
import static com.whitespace.mydailyvuadmin.VUBook.Fragment_Teacher.EXTRA_OFFICE;

public class DetailsActivityTeacher extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private TextView user_name,user_email,user_designation,user_department,user_office,user_counseling_hour,user_contact;
    private CircularImageView circularImageView;

    ProgressBar progressBar;
    ImageView copy,call, mail;

    ClipboardManager clipboardManager;
    ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        user_name = findViewById(R.id.name);
        user_email = findViewById(R.id.email);
        user_designation = findViewById(R.id.designation);
        user_department = findViewById(R.id.department);
        user_office = findViewById(R.id.office);
        user_counseling_hour = findViewById(R.id.counseling_hour);
        user_contact = findViewById(R.id.contact);
        progressBar = findViewById(R.id.progress_loading);
        circularImageView = findViewById(R.id.user_image);

//        copy = findViewById(R.id.copy);
        call = findViewById(R.id.call);
        mail = findViewById(R.id.send_email);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Details");

        final Intent intent = getIntent();

        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String name = intent.getStringExtra(EXTRA_NAME);
        final String email = intent.getStringExtra(EXTRA_EMAIL);
        String designation = intent.getStringExtra(EXTRA_DESIGNATION);
        String department = intent.getStringExtra(EXTRA_DEPARTMENT);
        String office = intent.getStringExtra(EXTRA_OFFICE);
        String counseling_hour = intent.getStringExtra(EXTRA_COUNSELING_HOUR);
        final String contact = intent.getStringExtra(EXTRA_CONTACT);

        Picasso.get().load(imageUrl).error(R.drawable.user_default).into(circularImageView);
        user_name.setText(name);
        user_email.setText(email);
        user_designation.setText(designation);
        user_department.setText(department);
        user_office.setText(office);
        user_counseling_hour.setText(counseling_hour);
        user_contact.setText(contact);

        clipboardManager = (ClipboardManager) DetailsActivityTeacher.this.getSystemService(Context.CLIPBOARD_SERVICE);

//        copy.setVisibility(View.GONE);

//        copy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                clipData = ClipData.newPlainText("contact",Contact);
//                clipboardManager.setPrimaryClip(clipData);
//
//                Toast.makeText(DetailsActivityTeacher.this,"copied: "+Contact, Toast.LENGTH_SHORT).show();
//            }
//        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+contact));

                if (ActivityCompat.checkSelfPermission(DetailsActivityTeacher.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(DetailsActivityTeacher.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }else {     //have got permission
                    try{
                        startActivity(callIntent);  //call activity and make phone call
                    }
                    catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }
                }

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedback = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));

                try {
                    startActivity(Intent.createChooser(feedback, "Choose an e-mail client"));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(DetailsActivityTeacher.this, "There is no e-mail clint installed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
