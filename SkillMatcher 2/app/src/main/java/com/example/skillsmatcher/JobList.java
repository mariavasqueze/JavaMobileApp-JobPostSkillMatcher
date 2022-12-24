package com.example.skillsmatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JobList extends AppCompatActivity implements RecyclerViewInterface{

//    private Runnable r;
//    private ScheduledExecutorService scheduledThreadPool;

    ArrayList<String[]> listings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);


        RecyclerView rec = (RecyclerView) findViewById(R.id.rView);
        Adapter adapter ;

        ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();;

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                StringBuffer sb = new StringBuffer();

                try {

                    HttpURLConnection urlConnection = null;
                    final String TEST_SITE = "https://api.lever.co/v0/postings/leverdemo?mode=json";

                    try {
                        URL url = new URL(TEST_SITE);
                        urlConnection = (HttpURLConnection) url.openConnection();

                        int code = urlConnection.getResponseCode();

                        if (code !=  200) {
                            throw new IOException("Invalid response from server: " + code);
                        }

                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                        String line;

                        while ((line = rd.readLine()) != null) {
                            sb.append(line);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();

                            // parse Json
                            if (sb.length() > 0) {
                                try {

                                    JSONArray results = new JSONArray(sb.toString());
                                    String[] postings;

                                    for(int i = 0; i < results.length(); i++){
                                        JSONObject json_data = results.getJSONObject(i);
                                        // conditions
                                        String t1 = json_data.getString("text");
                                        String t2 =  json_data.getString("descriptionPlain");

                                        if(!t1.contains("copy") && t2.length() > 190){
                                            postings = new String[2];
                                            postings[0] = json_data.getString("text");
                                            postings[1] = json_data.getString("descriptionPlain");
                                            listings.add(postings);
                                        }
                                    }
                                } catch (JSONException e) {
                                    Log.d("JReading Json",e.getMessage());
                                }
                            }
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    Log.d("thread status: " , e.getMessage());
                }
                return "Task 1";
            }
        });

        List<Future<String>> futures = null;
        try {
            futures = cachedThreadPool.invokeAll(callables);
        } catch (InterruptedException e) {
           Log.d("error",e.getMessage());
        }

        for(Future<String> future : futures){
            try {
                Log.d("future.get",future.get());
                if (future.get().equals("Task 1")) {
                    adapter = new Adapter(listings, this);
                    rec.setHasFixedSize(true);
                    rec.setLayoutManager(new LinearLayoutManager(this));
                    rec.setAdapter(adapter);

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void toEditSkills(View v){
        Intent i = new Intent(JobList.this, EditSkills.class);
        startActivity(i);
    }

    public void toSavedMatches(View v){
        Intent i = new Intent(JobList.this, SavedJobs.class);
        startActivity(i);
    }

    public void setup() {
        Log.d("Outside","done");
    }

    // When the user clicks on an item:
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(JobList.this, IndividualJobWindow.class);

        final String[] listing = listings.get(position);

        intent.putExtra("title", listing[0]);
        intent.putExtra("description", listing[1]);

        startActivity(intent);

    }
}