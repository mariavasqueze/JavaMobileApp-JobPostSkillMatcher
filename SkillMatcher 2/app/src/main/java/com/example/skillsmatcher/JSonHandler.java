package com.example.skillsmatcher;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class
JSonHandler {

    static ArrayList<String[]>listings = new ArrayList<>();

    public static void parseJsonData(String jsonString ) {
        try {
            JSONArray results = new JSONArray(jsonString);

            String title = "";
            String[] postings = new String[2];
            // ============================
            for(int i = 0; i < results.length(); i++){
                JSONObject json_data = results.getJSONObject(i);

                Log.d("-> ", json_data.getString("text"));
                title = json_data.getString("text");

                // from here
                postings[0] = json_data.getString("text");
                postings[1] = json_data.getString("descriptionPlain");
                listings.add(postings);
            }

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("arraylist", (Serializable) listings);
            intent.putExtra("bundle", bundle);

        } catch (JSONException e) {
            Log.d("JReading Json",e.getMessage());
        }

    }
}
