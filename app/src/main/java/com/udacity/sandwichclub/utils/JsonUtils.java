package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        ArrayList<String> knownAsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();

        try {
            JSONObject jObject = new JSONObject(json);

            JSONArray arr = jObject.getJSONObject("name").optJSONArray("alsoKnownAs");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    knownAsList.add(arr.getString(i));
                }
            }
            JSONArray arr1 = jObject.optJSONArray("ingredients");
            if (arr1 != null) {
                for (int i = 0; i < arr1.length(); i++) {
                    ingredientsList.add(arr1.getString(i));
                }
            }

            sandwich = new Sandwich(
                    jObject.getJSONObject("name").optString("mainName"),
                    knownAsList,
                    jObject.optString("placeOfOrigin"),
                    jObject.optString("description"),
                    jObject.optString("image"),
                    ingredientsList
            );

        } catch (JSONException e) {
            // Err while parsing
            Log.d(JsonUtils.class.getCanonicalName(), e.getMessage());
        }

        return sandwich;
    }
}
