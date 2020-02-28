package com.whitespace.mydailyvuadmin.VUBook;

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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.whitespace.mydailyvuadmin.Models.Profile;
import com.whitespace.mydailyvuadmin.Models.TeacherAdapter;
import com.whitespace.mydailyvuadmin.R;


public class Fragment_Teacher extends Fragment {

    View view;
    RecyclerView recyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference teacher = db.collection("Profiles");

    private TeacherAdapter adapter;

    private String dept;

    public static final String EXTRA_IMAGE_URL = "com.example.firebaseprofile.EXTRA_IMAGE_URL";
    public static final String EXTRA_NAME = "com.example.firebaseprofile.EXTRA_NAME";
    public static final String EXTRA_EMAIL = "com.example.firebaseprofile.EXTRA_EMAIL";
    public static final String EXTRA_DESIGNATION = "com.example.firebaseprofile.EXTRA_DESIGNATION";
    public static final String EXTRA_DEPARTMENT = "com.example.firebaseprofile.EXTRA_DEPARTMENT";
    public static final String EXTRA_OFFICE = "com.example.firebaseprofile.EXTRA_OFFICE";
    public static final String EXTRA_COUNSELING_HOUR = "com.example.firebaseprofile.EXTRA_COUNSELING_HOUR";
    public static final String EXTRA_CONTACT = "com.example.firebaseprofile.EXTRA_CONTACT";

    private static final String PREF_ACCOUNT_TYPE = "pref_account";
    private static final String PREF_DEPARTMENT = "pref_dept";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher,container,false);

        recyclerView = view.findViewById(R.id.recyclerviewTeacher);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        dept = sharedPreferences.getString(PREF_DEPARTMENT, "");

        Query query = teacher.whereEqualTo("status","Teacher")
                .whereEqualTo("department",dept)
                .orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Profile> options = new FirestoreRecyclerOptions.Builder<Profile>()
                .setQuery(query, Profile.class)
                .build();

        adapter = new TeacherAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener()  {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                Profile teacher = documentSnapshot.toObject(Profile.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(getContext(), DetailsActivityTeacher.class);

                String imageUrl = teacher.getImageUrl();
                String name = teacher.getName();
                String email = teacher.getEmail();
                String designation = teacher.getDesignation();
                String department = teacher.getDepartment();
                String office = teacher.getOffice();
                String counseling_hour = teacher.getCounseling_hour();
                String contact = teacher.getContact();

                intent.putExtra(EXTRA_IMAGE_URL,imageUrl);
                intent.putExtra(EXTRA_NAME,name);
                intent.putExtra(EXTRA_EMAIL,email);
                intent.putExtra(EXTRA_DESIGNATION,designation);
                intent.putExtra(EXTRA_DEPARTMENT,department);
                intent.putExtra(EXTRA_OFFICE,office);
                intent.putExtra(EXTRA_COUNSELING_HOUR,counseling_hour);
                intent.putExtra(EXTRA_CONTACT,contact);

                startActivity(intent);
            }
        });

        return view;
    }
}
