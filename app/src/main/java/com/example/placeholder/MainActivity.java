package com.example.placeholder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.InputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        public String readJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getAssets().open("sampleJSON.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                JSONObject jsonObj = new JSONObject(readJSONFromAsset());
                // Getting JSON Array node
                JSONObject wifi = jsonObj.getJSONObject("wifi");

                if (wifi != null) {
                    try {
                        String username = wifi.getString("username");
                        String password = wifi.getString("password");

                        // tmp hash map for single contact
                        HashMap<String, String> info = new HashMap<>();

                        // adding each child node to HashMap key => value
                        info.put("username", username);
                        info.put("password", password);

                        // adding contact to contact list
                        contactList.add(info);

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}