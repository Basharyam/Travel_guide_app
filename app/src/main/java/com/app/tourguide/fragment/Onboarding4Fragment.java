package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.tourguide.R;
import com.app.tourguide.enums.Category4Season;
import com.google.android.material.imageview.ShapeableImageView;

public class Onboarding4Fragment extends Fragment {

    private ShapeableImageView springImageView, summerImageView, autumnImageView, winterImageView;
    private OnCategory4SelectedListener categorySelectedListener;
    public interface OnCategory4SelectedListener {
        void onCategory4Selected(Category4Season category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategory4SelectedListener) {
            categorySelectedListener = (OnCategory4SelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCategorySelectedListener.");
        }
    }

    public Onboarding4Fragment() {
        // Required empty public constructor
    }

    public static Onboarding1Fragment newInstance(String param1, String param2) {
        return new Onboarding1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding4, container, false);

        springImageView = view.findViewById(R.id.spring);
        summerImageView = view.findViewById(R.id.summer);
        autumnImageView = view.findViewById(R.id.autumn);
        winterImageView = view.findViewById(R.id.winter);

        springImageView.setOnClickListener(v -> notifyCategorySelected(Category4Season.SPRING));
        summerImageView.setOnClickListener(v -> notifyCategorySelected(Category4Season.SUMMER));
        autumnImageView.setOnClickListener(v -> notifyCategorySelected(Category4Season.AUTUMN));
        winterImageView.setOnClickListener(v -> notifyCategorySelected(Category4Season.WINTER));

        return view;
    }

    private void notifyCategorySelected(Category4Season category) {
        if (categorySelectedListener != null) {
            categorySelectedListener.onCategory4Selected(category);
        }
    }
}
