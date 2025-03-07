package com.app.tourguide.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import com.app.tourguide.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<TourItem> itemList;

    public ItemAdapter(Context context, List<TourItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tour_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TourItem item = itemList.get(position);

        // Set text data
        holder.title.setText(item.getTitle());
        holder.region.setText("Region: " + item.getRegion());
        holder.season.setText("Season: " + item.getSeason());
        holder.duration.setText("Duration: " + item.getDuration());

        String flagUrl = getFlagUrlForCountry(item.getCountry());
        if (!flagUrl.isEmpty()) {
            Picasso.get().load(flagUrl).into(holder.flag);
        }

        // Set cities
        List<String> citiesList = item.getCities();
        if (citiesList != null && !citiesList.isEmpty()) {
            holder.cities.setText( String.join(", ", citiesList));
        } else {
            holder.cities.setText("Cities: Not Available");
        }

        // Set famous locations
        holder.famousLocationsContainer.removeAllViews();
        String famousLocationsJson = item.getFamousLocations();
        if (famousLocationsJson != null && !famousLocationsJson.isEmpty()) {
            try {
                JSONArray famousLocationsArray = new JSONArray(famousLocationsJson);
                for (int i = 0; i < famousLocationsArray.length(); i++) {
                    JSONObject location = famousLocationsArray.getJSONObject(i);
                    String cityName = location.getString("city");
                    JSONArray locations = location.getJSONArray("locations");

                    TextView cityTextView = new TextView(context);
                    cityTextView.setText(cityName + ":");
                    cityTextView.setTextSize(18);
                    cityTextView.setTypeface(null, Typeface.BOLD);
                    cityTextView.setTextColor(Color.WHITE);
                    holder.famousLocationsContainer.addView(cityTextView);

                    for (int j = 0; j < locations.length(); j++) {
                        TextView locationTextView = new TextView(context);
                        locationTextView.setText("• " + locations.getString(j));
                        locationTextView.setTextSize(16);
                        locationTextView.setTextColor(Color.BLACK);
                        locationTextView.setPadding(20, 2, 5, 2);
                        holder.famousLocationsContainer.addView(locationTextView);
                    }
                }
            } catch (JSONException e) {
                Log.e("ItemAdapter", "Error parsing famous locations JSON", e);
            }
        } else {
            TextView noLocationsText = new TextView(context);
            noLocationsText.setText("No famous locations available");
            noLocationsText.setTextSize(16);
            noLocationsText.setTextColor(Color.GRAY);
            holder.famousLocationsContainer.addView(noLocationsText);
        }

        // Clear previous schedule views
        holder.scheduleContainer.removeAllViews();
        List<String> scheduleList = item.getSchedule();

        if (scheduleList == null || scheduleList.isEmpty()) {
            TextView noScheduleText = new TextView(context);
            noScheduleText.setText("No schedule available");
            noScheduleText.setTextSize(16);
            noScheduleText.setTextColor(Color.GRAY);
            holder.scheduleContainer.addView(noScheduleText);
        } else {
            String lastDayTitle = null;
            for (String scheduleEntry : scheduleList) {
                int colonIndex = scheduleEntry.indexOf(":");
                if (colonIndex != -1) {
                    lastDayTitle = scheduleEntry.substring(0, colonIndex).trim();
                    String activityPart = scheduleEntry.substring(colonIndex + 1).trim();

                    TextView dayTextView = new TextView(context);
                    dayTextView.setText(lastDayTitle + ":");
                    dayTextView.setTextSize(18);
                    dayTextView.setTypeface(null, Typeface.BOLD);
                    dayTextView.setTextColor(Color.WHITE);
                    dayTextView.setPadding(5, 10, 5, 5);
                    holder.scheduleContainer.addView(dayTextView);

                    String[] activities = activityPart.split("\\s*,\\s*");
                    for (String activity : activities) {
                        TextView activityTextView = new TextView(context);
                        activityTextView.setText("• " + activity.trim());
                        activityTextView.setTextSize(16);
                        activityTextView.setTextColor(Color.BLACK);
                        activityTextView.setPadding(20, 2, 5, 2);
                        holder.scheduleContainer.addView(activityTextView);
                    }
                } else if (lastDayTitle != null) {
                    TextView activityTextView = new TextView(context);
                    activityTextView.setText("• " + scheduleEntry.trim());
                    activityTextView.setTextSize(16);
                    activityTextView.setTextColor(Color.BLACK);
                    activityTextView.setPadding(20, 2, 5, 2);
                    holder.scheduleContainer.addView(activityTextView);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView title, region, season, duration, cities;
        LinearLayout scheduleContainer, famousLocationsContainer;
        ImageView flag;

        ViewHolder(View view) {
            title = view.findViewById(R.id.title);
            region = view.findViewById(R.id.region);
            season = view.findViewById(R.id.season);
            duration = view.findViewById(R.id.duration);
            flag = view.findViewById(R.id.flag);
            scheduleContainer = view.findViewById(R.id.schedule_container);
            famousLocationsContainer = view.findViewById(R.id.famous_locations_container);
            cities = view.findViewById(R.id.cities);
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
