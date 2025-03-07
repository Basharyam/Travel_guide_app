package com.app.tourguide.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.app.tourguide.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Onboarding6Fragment extends Fragment {

    private static final String ARG_COUNTRIES = "countries";

    private List<String> countries;
    private ShapeableImageView country1ImageView, country2ImageView, country3ImageView;
    private OnCountrySelectedListener countrySelectedListener;

    public interface OnCountrySelectedListener {
        void onCountrySelected(String country);
    }

    public static Onboarding6Fragment newInstance(List<String> countries) {
        Onboarding6Fragment fragment = new Onboarding6Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_COUNTRIES, new ArrayList<>(countries));
        fragment.setArguments(args);
        return fragment;
    }

    public Onboarding6Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCountrySelectedListener) {
            countrySelectedListener = (OnCountrySelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnCountrySelectedListener.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countries = getArguments().getStringArrayList(ARG_COUNTRIES);
        }
        if (countries == null) {
            countries = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding6, container, false);

        country1ImageView = view.findViewById(R.id.country1);
        country2ImageView = view.findViewById(R.id.country2);
        country3ImageView = view.findViewById(R.id.country3);

        // Dynamically set country flags and visibility based on available data
        setupCountryViews();

        return view;
    }

    private void setupCountryViews() {
        // Hide all icons initially
        country1ImageView.setVisibility(View.GONE);
        country2ImageView.setVisibility(View.GONE);
        country3ImageView.setVisibility(View.GONE);

        if (countries.size() > 0) {
            setFlagImage(country1ImageView, countries.get(0));
            country1ImageView.setVisibility(View.VISIBLE);
            country1ImageView.setOnClickListener(v -> notifyCountrySelected(countries.get(0)));
        }
        if (countries.size() > 1) {
            setFlagImage(country2ImageView, countries.get(1));
            country2ImageView.setVisibility(View.VISIBLE);
            country2ImageView.setOnClickListener(v -> notifyCountrySelected(countries.get(1)));
        }
        if (countries.size() > 2) {
            setFlagImage(country3ImageView, countries.get(2));
            country3ImageView.setVisibility(View.VISIBLE);
            country3ImageView.setOnClickListener(v -> notifyCountrySelected(countries.get(2)));
        }
    }

    private void notifyCountrySelected(String country) {
        if (countrySelectedListener != null) {
            countrySelectedListener.onCountrySelected(country);
        }
    }

    private void setFlagImage(ShapeableImageView imageView, String country) {
        if (country == null || country.isEmpty()) {
            return;
        }

        String flagUrl = getFlagUrlForCountry(country);
        Log.w("ABCD Country", flagUrl);
        if (!flagUrl.isEmpty()) {
            Picasso.get().load(flagUrl).into(imageView);
        }
    }

    private String getFlagUrlForCountry(String country) {
        String countryCode = getCountryCode(country.toLowerCase());
        if (!countryCode.isEmpty()) {
            return "https://flagcdn.com/w320/" + countryCode + ".png";
        }
        return "";
    }

    private String getCountryCode(String country) {
        switch (country) {
            case "afghanistan": return "af";
            case "albania": return "al";
            case "algeria": return "dz";
            case "andorra": return "ad";
            case "angola": return "ao";
            case "argentina": return "ar";
            case "armenia": return "am";
            case "australia": return "au";
            case "austria": return "at";
            case "azerbaijan": return "az";
            case "bahamas": return "bs";
            case "bahrain": return "bh";
            case "bangladesh": return "bd";
            case "barbados": return "bb";
            case "belarus": return "by";
            case "belgium": return "be";
            case "belize": return "bz";
            case "benin": return "bj";
            case "bhutan": return "bt";
            case "bolivia": return "bo";
            case "bosnia and herzegovina": return "ba";
            case "botswana": return "bw";
            case "brazil": return "br";
            case "brunei": return "bn";
            case "bulgaria": return "bg";
            case "burkina faso": return "bf";
            case "burundi": return "bi";
            case "cambodia": return "kh";
            case "cameroon": return "cm";
            case "canada": return "ca";
            case "cape verde": return "cv";
            case "central african republic": return "cf";
            case "chad": return "td";
            case "chile": return "cl";
            case "china": return "cn";
            case "colombia": return "co";
            case "comoros": return "km";
            case "congo": return "cg";
            case "costa rica": return "cr";
            case "croatia": return "hr";
            case "cuba": return "cu";
            case "cyprus": return "cy";
            case "czech republic": return "cz";
            case "denmark": return "dk";
            case "djibouti": return "dj";
            case "dominican republic": return "do";
            case "ecuador": return "ec";
            case "egypt": return "eg";
            case "el salvador": return "sv";
            case "estonia": return "ee";
            case "eswatini": return "sz";
            case "ethiopia": return "et";
            case "fiji": return "fj";
            case "finland": return "fi";
            case "france": return "fr";
            case "gabon": return "ga";
            case "gambia": return "gm";
            case "germany": return "de";
            case "ghana": return "gh";
            case "greece": return "gr";
            case "india": return "in";
            case "indonesia": return "id";
            case "iran": return "ir";
            case "iraq": return "iq";
            case "ireland": return "ie";
            case "israel": return "il";
            case "italy": return "it";
            case "japan": return "jp";
            case "kuwait": return "kw";
            case "kenya": return "ke";
            case "malaysia": return "my";
            case "mexico": return "mx";
            case "nepal": return "np";
            case "netherlands": return "nl";
            case "new zealand": return "nz";
            case "norway": return "no";
            case "oman": return "om";
            case "pakistan": return "pk";
            case "peru": return "pe";
            case "philippines": return "ph";
            case "portugal": return "pt";
            case "qatar": return "qa";
            case "russia": return "ru";
            case "saudi arabia": return "sa";
            case "singapore": return "sg";
            case "south africa": return "za";
            case "south korea": return "kr";
            case "spain": return "es";
            case "sweden": return "se";
            case "thailand": return "th";
            case "turkey": return "tr";
            case "ukraine": return "ua";
            case "united kingdom": return "gb";
            case "united states": return "us";
            case "usa": return "us";
            case "vietnam": return "vn";
            default: return "";
        }
    }
}
