package com.example.placeholder;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;

import android.graphics.Matrix;
import android.widget.ImageView;
import 	android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        ImageView phoneIcon = findViewById(R.id.phoneIcon);
        phoneIcon.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely) );

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
        }
    }
}