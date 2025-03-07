package com.app.tourguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.tourguide.R;
import com.app.tourguide.db.DataImporter;
import com.app.tourguide.enums.Category1Type;
import com.app.tourguide.enums.Category2Theme;
import com.app.tourguide.enums.Category3Region;
import com.app.tourguide.enums.Category4Season;
import com.app.tourguide.enums.Category5Duration;
import com.app.tourguide.fragment.Onboarding1Fragment;
import com.app.tourguide.fragment.Onboarding2Fragment;
import com.app.tourguide.fragment.Onboarding3Fragment;
import com.app.tourguide.fragment.Onboarding4Fragment;
import com.app.tourguide.fragment.Onboarding5Fragment;
import com.app.tourguide.fragment.Onboarding6Fragment;
import com.app.tourguide.utils.TourSearch;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        Onboarding1Fragment.OnCategory1SelectedListener,
        Onboarding2Fragment.OnCategory2SelectedListener,
        Onboarding3Fragment.OnCategory3SelectedListener,
        Onboarding4Fragment.OnCategory4SelectedListener,
        Onboarding5Fragment.OnCategory5SelectedListener,
        Onboarding6Fragment.OnCountrySelectedListener { // Handle country selection

    int currentPageIndex = 0;
    TextView txvTitle;
    private TourSearch tourSearch; // TourSearch instance

    private Category1Type selectedCategory1;
    private Category2Theme selectedCategory2;
    private Category3Region selectedCategory3;
    private Category4Season selectedCategory4;
    private Category5Duration selectedCategory5;
    private String selectedCategory6; // Holds the selected country

    private final ActivityResultLauncher<Intent> tourGuideLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    selectedCategory1 = Category1Type.valueOf(data.getStringExtra("category1"));
                    selectedCategory2 = Category2Theme.valueOf(data.getStringExtra("category2"));
                    selectedCategory3 = Category3Region.valueOf(data.getStringExtra("category3"));
                    selectedCategory4 = Category4Season.valueOf(data.getStringExtra("category4"));
                    selectedCategory5 = Category5Duration.valueOf(data.getStringExtra("category5"));
                    selectedCategory6 = data.getStringExtra("category6");

                    currentPageIndex = 5; // Restore progress
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txvTitle = findViewById(R.id.titleTextView);
        tourSearch = new TourSearch(this); // Initialize TourSearch

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            txvTitle.setText(getResources().getStringArray(R.array.trip_questions)[0]);
            loadFragment(new Onboarding1Fragment());
        }

        new DataImporter(this).importDataFromJson();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void updateUI() {
        String[] tripQuestions = getResources().getStringArray(R.array.trip_questions);
        if (currentPageIndex < tripQuestions.length) {
            txvTitle.setText(tripQuestions[currentPageIndex]);
        }
    }

    private void next() {
        Intent intent = new Intent(MainActivity.this, TourGuideActivity.class);
        intent.putExtra("category1", selectedCategory1.toString());
        intent.putExtra("category2", selectedCategory2.toString());
        intent.putExtra("category3", selectedCategory3.toString());
        intent.putExtra("category4", selectedCategory4.toString());
        intent.putExtra("category5", selectedCategory5.toString());
        intent.putExtra("category6", selectedCategory6); // Pass selected country
        tourGuideLauncher.launch(intent);
    }

    public void goToNextFragment() {
        currentPageIndex++;
        updateUI();

        Fragment nextFragment = null;
        switch (currentPageIndex) {
            case 1:
                nextFragment = new Onboarding2Fragment();
                break;
            case 2:
                nextFragment = new Onboarding3Fragment();
                break;
            case 3:
                nextFragment = new Onboarding4Fragment();
                break;
            case 4:
                nextFragment = new Onboarding5Fragment();
                break;
            case 5:
                // Fetch matching countries from DB
                List<String> matchingCountries = tourSearch.searchMatchingCountries(
                        selectedCategory1.toString(),
                        selectedCategory2.toString(),
                        selectedCategory3.toString(),
                        selectedCategory4.toString(),
                        selectedCategory5.toString()
                );

                Log.d("Database Query", "Matching Countries: " + matchingCountries);

                // If no countries found, skip Onboarding6Fragment and go to next()
                if (matchingCountries.isEmpty()) {
                    next(); // Go directly to TourGuideActivity
                    return;
                }

                // Load Onboarding6Fragment with actual results
                nextFragment = Onboarding6Fragment.newInstance(matchingCountries);
                break;
            case 6:
                next(); // Move to TourGuideActivity
                return;
        }

        if (nextFragment != null) {
            loadFragment(nextFragment);
        }
    }

    @Override
    public void onCategory1Selected(Category1Type category) {
        selectedCategory1 = category;
        goToNextFragment();
    }

    @Override
    public void onCategory2Selected(Category2Theme category) {
        selectedCategory2 = category;
        goToNextFragment();
       // Toast.makeText(this, "Selected Category2: " + category.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategory3Selected(Category3Region category) {
        selectedCategory3 = category;
        goToNextFragment();
      //  Toast.makeText(this, "Selected Category3: " + category.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategory4Selected(Category4Season category) {
        selectedCategory4 = category;
        goToNextFragment();
       // Toast.makeText(this, "Selected Category4: " + category.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategory5Selected(Category5Duration category) {
        selectedCategory5 = category;
        goToNextFragment();
      //  Toast.makeText(this, "Selected Category5: " + category.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCountrySelected(String country) {
        selectedCategory6 = country; // Store selected country
     //   Toast.makeText(this, "Selected Country: " + country, Toast.LENGTH_SHORT).show();
        goToNextFragment(); // Move to TourGuideActivity
    }

}
