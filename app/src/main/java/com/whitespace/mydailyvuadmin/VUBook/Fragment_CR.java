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
import com.whitespace.mydailyvuadmin.Models.CRAdapter;
import com.whitespace.mydailyvuadmin.Models.Profile;
import com.whitespace.mydailyvuadmin.R;


public class Fragment_CR extends Fragment {

    View view;
    RecyclerView recyclerView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference cr = db.collection("Profiles");

    private CRAdapter adapter;

    private String dept;

    public static final String EXTRA_IMAGE_URL_CR = "com.example.firebaseprofile.EXTRA_IMAGE_URL_CR";
    public static final String EXTRA_NAME_CR = "com.example.firebaseprofile.EXTRA_NAME_CR";
    public static final String EXTRA_EMAIL_CR = "com.example.firebaseprofile.EXTRA_EMAIL_CR";
    public static final String EXTRA_ID_CR = "com.example.firebaseprofile.EXTRA_ID_CR";
    public static final String EXTRA_DEPARTMENT_CR = "com.example.firebaseprofile.EXTRA_DEPARTMENT_CR";
    public static final String EXTRA_SEMESTER_CR = "com.example.firebaseprofile.EXTRA_SEMESTER_CR";
    public static final String EXTRA_SECTION_CR = "com.example.firebaseprofile.EXTRA_SECTION_CR";
    public static final String EXTRA_CONTACT_CR = "com.example.firebaseprofile.EXTRA_CONTACT_CR";

    private static final String PREF_ACCOUNT_TYPE = "pref_account";
    private static final String PREF_DEPARTMENT = "pref_dept";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cr,container,false);

        recyclerView = view.findViewById(R.id.recyclerviewCR);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        dept = sharedPreferences.getString(PREF_DEPARTMENT, "");

        Query query = cr.whereEqualTo("status","CR")
                .whereEqualTo("department",dept)
                .orderBy("semester", Query.Direction.DESCENDING)
                .orderBy("section", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Profile> options = new FirestoreRecyclerOptions.Builder<Profile>()
                .setQuery(query, Profile.class)
                .build();

        adapter = new CRAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new CRAdapter.OnItemClickListener()  {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                Profile cr = documentSnapshot.toObject(Profile.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(getContext(), DetailsActivityCR.class);

                String imageUrl = cr.getImageUrl();
                String name = cr.getName();
                String email = cr.getEmail();
                String student_id = cr.getId();
                String department = cr.getDepartment();
                String semester = cr.getSemester();
                String section = cr.getSection();
                String contact = cr.getContact();

                intent.putExtra(EXTRA_IMAGE_URL_CR,imageUrl);
                intent.putExtra(EXTRA_NAME_CR,name);
                intent.putExtra(EXTRA_EMAIL_CR,email);
                intent.putExtra(EXTRA_ID_CR,student_id);
                intent.putExtra(EXTRA_DEPARTMENT_CR,department);
                intent.putExtra(EXTRA_SEMESTER_CR,semester);
                intent.putExtra(EXTRA_SECTION_CR,section);
                intent.putExtra(EXTRA_CONTACT_CR,contact);

                startActivity(intent);
            }
        });

        return view;
    }
}
