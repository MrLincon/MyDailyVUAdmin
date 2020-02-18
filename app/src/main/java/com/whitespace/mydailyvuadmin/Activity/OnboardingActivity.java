package com.whitespace.mydailyvuadmin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.whitespace.mydailyvuadmin.R;
import com.whitespace.mydailyvuadmin.SliderAdapter;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager onboarding;
    private LinearLayout dots;
    private TextView[] mdots;
    private ImageView back, next;
    private Button exit;

    private int currentPage;

    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        onboarding = findViewById(R.id.viewPagerOnboarding);
        dots = findViewById(R.id.dots);

        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        exit = findViewById(R.id.finish_onboarding);

        sliderAdapter = new SliderAdapter(this);

        onboarding.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        onboarding.addOnPageChangeListener(viweListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onboarding.setCurrentItem(currentPage + 1);
                if (next.equals("Finish")) {
                    onboarding.setCurrentItem(currentPage - 1);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onboarding.setCurrentItem(currentPage - 1);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();

                Intent intent = new Intent(OnboardingActivity.this,RoutineActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addDotsIndicator(int position) {
        mdots = new TextView[3];
        dots.removeAllViews();
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.colorView));

            dots.addView(mdots[i]);
        }

        if (mdots.length > 0) {
            mdots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viweListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if (position == 0) {
                back.setEnabled(false);
                next.setEnabled(true);
                back.setVisibility(View.INVISIBLE);

            } else if (position == mdots.length - 1) {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
            } else {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
