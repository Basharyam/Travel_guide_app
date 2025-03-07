package com.app.tourguide.utils;

import android.content.Context;
import android.util.Log;

import com.app.tourguide.model.TourGuideResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonParser {
    public static TourGuideResponse parseJson(Context context) {
        String json = loadJSONFromAsset(context);
        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<TourGuideResponse>(){}.getType());
        }
        return null;
    }

    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("tour_guide_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Log.e("JsonParser", "Error reading JSON file", ex);
        }
        return json;
    }
}
