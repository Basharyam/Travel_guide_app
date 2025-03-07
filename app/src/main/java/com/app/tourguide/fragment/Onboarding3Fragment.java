package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.tourguide.R;
import com.app.tourguide.enums.Category3Region;
import com.google.android.material.imageview.ShapeableImageView;

public class Onboarding3Fragment extends Fragment {

    private ShapeableImageView asiaImageView, europeImageView, africaImageView, northAmericaImageView, southAmericaImageView;
    private OnCategory3SelectedListener categorySelectedListener;
    public interface OnCategory3SelectedListener {
        void onCategory3Selected(Category3Region category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategory3SelectedListener) {
            categorySelectedListener = (OnCategory3SelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCategorySelectedListener.");
        }
    }

    public Onboarding3Fragment() {
        // Required empty public constructor
    }

    public static Onboarding1Fragment newInstance(String param1, String param2) {
        return new Onboarding1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding3, container, false);

        asiaImageView = view.findViewById(R.id.asia);
        europeImageView = view.findViewById(R.id.europe);
        africaImageView = view.findViewById(R.id.africa);
        northAmericaImageView = view.findViewById(R.id.northamerica);
        southAmericaImageView = view.findViewById(R.id.southamerica);

        asiaImageView.setOnClickListener(v -> notifyCategorySelected(Category3Region.ASIA));
        europeImageView.setOnClickListener(v -> notifyCategorySelected(Category3Region.EUROPE));
        africaImageView.setOnClickListener(v -> notifyCategorySelected(Category3Region.AFRICA));
        northAmericaImageView.setOnClickListener(v -> notifyCategorySelected(Category3Region.NORTHAMERICA));
        southAmericaImageView.setOnClickListener(v -> notifyCategorySelected(Category3Region.SOUTHAMERICA));

        return view;
    }

    private void notifyCategorySelected(Category3Region category) {
        if (categorySelectedListener != null) {
            categorySelectedListener.onCategory3Selected(category);
        }
    }
}
