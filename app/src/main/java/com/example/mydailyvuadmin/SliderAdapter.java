package com.example.mydailyvuadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slider_images = {
            R.drawable.eat,
            R.drawable.sleep,
            R.drawable.code
    };

    public String[] slider_headings = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slider_desc = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry"
    };

    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slider_layout,container,false);

        ImageView imageView = view.findViewById(R.id.imageViewOnboarding);
        TextView header = view.findViewById(R.id.textViewHeader);
        TextView desc = view.findViewById(R.id.textViewDescription);

        imageView.setImageResource(slider_images[position]);
        header.setText(slider_headings[position]);
        desc.setText(slider_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
