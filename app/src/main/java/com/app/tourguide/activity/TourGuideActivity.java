package com.app.tourguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.tourguide.R;
import com.app.tourguide.adapter.ItemAdapter;
import com.app.tourguide.adapter.TourItem;
import com.app.tourguide.model.Tour;
import com.app.tourguide.utils.TourSearch;

import java.util.ArrayList;
import java.util.List;

public class TourGuideActivity extends AppCompatActivity {

    ListView listView;
    private TourSearch tourSearch;
    private String selectedCategory1, selectedCategory2, selectedCategory3, selectedCategory4, selectedCategory5, selectedCategory6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_guide);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.back_button);

        // Handle back button click
        backButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("category1", selectedCategory1);
            returnIntent.putExtra("category2", selectedCategory2);
            returnIntent.putExtra("category3", selectedCategory3);
            returnIntent.putExtra("category4", selectedCategory4);
            returnIntent.putExtra("category5", selectedCategory5);
            returnIntent.putExtra("category6", selectedCategory6);
            setResult(RESULT_OK, returnIntent);
            finish(); // Close the current activity
        });

        listView = findViewById(R.id.listView);

        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        selectedCategory1 = intent.getStringExtra("category1");
        selectedCategory2 = intent.getStringExtra("category2");
        selectedCategory3 = intent.getStringExtra("category3");
        selectedCategory4 = intent.getStringExtra("category4");
        selectedCategory5 = intent.getStringExtra("category5");
        selectedCategory6 = intent.getStringExtra("category6");

        tourSearch = new TourSearch(this);
        List<Tour> filteredTours = tourSearch.searchTours(
                selectedCategory1, selectedCategory2, selectedCategory3, selectedCategory4, selectedCategory5, selectedCategory6
        );

        loadUI(filteredTours);
    }

    private void loadUI(List<Tour> filteredTours) {
        List<TourItem> items = new ArrayList<>();

        if (filteredTours.isEmpty()) {
            // Add an empty state message when no results are found
            items.add(new TourItem(
                    -1,  // Default ID for empty state
                    "No Tours Available",
                    "N/A",  // No specific region
                    "N/A",  // No specific season
                    "N/A",  // No duration
                    "Try adjusting your search filters.",
                    "N/A",  // No specific country
                    "",  // No flag URL
                    new ArrayList<>(), // Empty schedule list
                    new ArrayList<>(), // Empty cities list
                    "[]" // Empty famous locations JSON
            ));
        } else {
            for (Tour tour : filteredTours) {
                items.add(new TourItem(
                        tour.getId(),
                        tour.getTitle(),
                        tour.getRegion(),
                        tour.getSeason(),
                        tour.getDuration(),
                        tour.getDescription(),
                        tour.getCountry(),
                        tour.getFlag(),
                        tour.getSchedule(),
                        tour.getCities(),
                        tour.getFamousLocations()
                ));
            }
        }

        ItemAdapter adapter = new ItemAdapter(this, items);
        listView.setAdapter(adapter);
    }
}
