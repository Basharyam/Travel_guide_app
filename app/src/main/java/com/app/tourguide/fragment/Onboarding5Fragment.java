package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.tourguide.R;
import com.app.tourguide.enums.Category5Duration;
import com.google.android.material.imageview.ShapeableImageView;

public class Onboarding5Fragment extends Fragment {

    private ShapeableImageView button3ImageView, button5ImageView, button7ImageView;
    private OnCategory5SelectedListener categorySelectedListener;
    public interface OnCategory5SelectedListener {
        void onCategory5Selected(Category5Duration category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCategory5SelectedListener) {
            categorySelectedListener = (OnCategory5SelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCategorySelectedListener.");
        }
    }

    public Onboarding5Fragment() {
        // Required empty public constructor
    }

    public static Onboarding1Fragment newInstance(String param1, String param2) {
        return new Onboarding1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding5, container, false);

        button3ImageView = view.findViewById(R.id.button3);
        button5ImageView = view.findViewById(R.id.button5);
        button7ImageView = view.findViewById(R.id.button7);

        button3ImageView.setOnClickListener(v -> notifyCategorySelected(Category5Duration.THREEDAYS));
        button5ImageView.setOnClickListener(v -> notifyCategorySelected(Category5Duration.FIVEDAYS));
        button7ImageView.setOnClickListener(v -> notifyCategorySelected(Category5Duration.SEVENDAYS));

        return view;
    }

    private void notifyCategorySelected(Category5Duration category) {
        if (categorySelectedListener != null) {
            categorySelectedListener.onCategory5Selected(category);
        }
    }
}
