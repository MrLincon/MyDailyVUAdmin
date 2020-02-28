package com.whitespace.mydailyvuadmin.VUBook;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.whitespace.mydailyvuadmin.R;

public class VUBookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vubook);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("VU Book");

        tabLayout = findViewById(R.id.tabLayoutID);
        viewPager = findViewById(R.id.viewPager);

//  Tab Layout

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapterVUBook viewPagerAdapter = new ViewPagerAdapterVUBook(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }
}