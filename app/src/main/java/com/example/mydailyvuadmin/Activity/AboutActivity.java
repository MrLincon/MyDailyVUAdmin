package com.example.mydailyvuadmin.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydailyvuadmin.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class AboutActivity extends AppCompatActivity {

    ImageView close;
    CircularImageView circularImageView;
    TextView facebook,github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        close = findViewById(R.id.close_btn);
        circularImageView = findViewById(R.id.circularImageView2);
        facebook = findViewById(R.id.facebook);
        github = findViewById(R.id.github);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/AhamedLinc0n"));
                startActivity(facebook);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent github = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/MrLincon"));
                startActivity(github);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//    }

}
