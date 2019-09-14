package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

//    public static final String TAG = "placeHolder";
//
//    private TextView mTextView;
//    private NfcAdapter mNfcAdapter;

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;
    private boolean androidBeamAvailable;
    private Context context;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mTextView = (TextView) findViewById(R.id.helloWorld);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            /*
             * Disable NFC features here.
             * For example, disable menu items or buttons that activate
             * NFC-related features
             */
            System.out.println("Not working");
            // Android Beam file transfer isn't supported
        } else if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // If Android Beam isn't available, don't continue.
            System.out.println("Android beam not working");
            androidBeamAvailable = false;
            /*
             * Disable Android Beam file transfer features here.
             */
            // Android Beam file transfer is available, continue
        } else {
            androidBeamAvailable = true;
            System.out.println("Android beam is working");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    if (mNfcAdapter == null) mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
                    if (mNfcAdapter == null || !mNfcAdapter.isEnabled()) {
                        mTextView.setText("NFC is disabled.");
                    } else {
                        mTextView.setText("NFC is working!");
                    }
                }
            }.start();
            
        }


        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        //DSADSADSADASDSAD
    }
    
    final WifiConnector connector = new WifiConnector(this);
    JSONFormatter json = new JSONFormatter();
}
