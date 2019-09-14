package com.example.placeholder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    public static final String TAG = "placeHolder";
//
//    private TextView mTextView;
//    private NfcAdapter mNfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        mTextView = (TextView) findViewById(R.id.textView_explanation);
//
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//
//        if (mNfcAdapter == null) {
//            Toast.makeText(this, "No NFC", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//
//        if (!mNfcAdapter.isEnabled()) {
//            mTextView.setText("NFC is disabled.");
//        } else {
//            mTextView.setText(R.string.explanation);
//        }
//
////        handleIntent(getIntent());
//    }
//
//    private void handleIntent(Intent intent) {
//        //DSADSADSADASDSAD
//    }

    final WifiConnector connector = new WifiConnector(this);
}}
