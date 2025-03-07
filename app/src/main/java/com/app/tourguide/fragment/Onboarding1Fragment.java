package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.tourguide.R;
import com.app.tourguide.enums.Category1Type;
import com.google.android.material.imageview.ShapeableImageView;

public class Onboarding1Fragment extends Fragment {

    private ShapeableImageView familyImageView, youthImageView, romanticImageView;
    private OnCategory1SelectedListener categorySelectedListener;
    public interface OnCategory1SelectedListener {
        void onCategory1Selected(Category1Type category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategory1SelectedListener) {
            categorySelectedListener = (OnCategory1SelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCategorySelectedListener.");
        }
    }

    public Onboarding1Fragment() {
        // Required empty public constructor
    }

    public static Onboarding1Fragment newInstance(String param1, String param2) {
        return new Onboarding1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding1, container, false);

        familyImageView = view.findViewById(R.id.family);
        youthImageView = view.findViewById(R.id.youth);
        romanticImageView = view.findViewById(R.id.romantic);

        familyImageView.setOnClickListener(v -> notifyCategorySelected(Category1Type.FAMILY));
        youthImageView.setOnClickListener(v -> notifyCategorySelected(Category1Type.YOUTH));
        romanticImageView.setOnClickListener(v -> notifyCategorySelected(Category1Type.ROMANTIC));

        return view;
    }

    private void notifyCategorySelected(Category1Type category) {
        if (categorySelectedListener != null) {
            categorySelectedListener.onCategory1Selected(category);
        }
    }
}
