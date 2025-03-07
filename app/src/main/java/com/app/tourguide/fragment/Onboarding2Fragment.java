package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.tourguide.R;
import com.app.tourguide.enums.Category2Theme;
import com.google.android.material.imageview.ShapeableImageView;

public class Onboarding2Fragment extends Fragment {

    private ShapeableImageView culturalImageView, natureImageView, natcultureImageView, natventureImageView, adventureImageView;
    private OnCategory2SelectedListener categorySelectedListener;
    public interface OnCategory2SelectedListener {
        void onCategory2Selected(Category2Theme category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategory2SelectedListener) {
            categorySelectedListener = (OnCategory2SelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCategorySelectedListener.");
        }
    }

    public Onboarding2Fragment() {
        // Required empty public constructor
    }

    public static Onboarding1Fragment newInstance(String param1, String param2) {
        return new Onboarding1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding2, container, false);

        culturalImageView = view.findViewById(R.id.cultural);
        natureImageView = view.findViewById(R.id.nature);
        natcultureImageView = view.findViewById(R.id.natculture);
        natventureImageView = view.findViewById(R.id.natventure);
        adventureImageView = view.findViewById(R.id.adventure);

        culturalImageView.setOnClickListener(v -> notifyCategorySelected(Category2Theme.CULTURAL));
        natureImageView.setOnClickListener(v -> notifyCategorySelected(Category2Theme.NATURE));
        natcultureImageView.setOnClickListener(v -> notifyCategorySelected(Category2Theme.NATCULTURE));
        natventureImageView.setOnClickListener(v -> notifyCategorySelected(Category2Theme.NATVENTURE));
        adventureImageView.setOnClickListener(v -> notifyCategorySelected(Category2Theme.ADVENTURE));

        return view;
    }

    private void notifyCategorySelected(Category2Theme category) {
        if (categorySelectedListener != null) {
            categorySelectedListener.onCategory2Selected(category);
        }
    }
}
