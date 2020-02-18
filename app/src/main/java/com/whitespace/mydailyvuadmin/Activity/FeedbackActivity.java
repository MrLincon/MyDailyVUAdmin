package com.whitespace.mydailyvuadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.whitespace.mydailyvuadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText message;
    Button send;

    private FirebaseFirestore database;
    private DocumentReference doc_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        toolbar = findViewById(R.id.toolbar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        message = findViewById(R.id.feedback_message);
        send = findViewById(R.id.feedback_send);

        database = FirebaseFirestore.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String feedback =  message.getText().toString().trim();

                if (feedback.isEmpty()) {
                    message.setError("Field must be filled");
                    return;
                } else {
                    long docID = System.currentTimeMillis();

                    doc_reference = database.collection("Feedback").document(String.valueOf(docID));

                    Map<String, String> userMap = new HashMap<>();

                    userMap.put("message", feedback);

                    doc_reference.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            message.setText("");
                            Toast.makeText(FeedbackActivity.this, "Feedback sent!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FeedbackActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent save = new Intent(FeedbackActivity.this, RoutineActivity.class);
                    startActivity(save);
                    finish();

                }
            }
        });
    }
}
