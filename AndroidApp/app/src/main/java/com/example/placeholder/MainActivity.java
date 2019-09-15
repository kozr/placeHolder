package com.example.placeholder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.nfc.NdefRecord.createMime;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    private NfcAdapter mNfcAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(context);

        this.getSupportActionBar().hide();
        ImageView phoneIcon = findViewById(R.id.phoneIcon);
        phoneIcon.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely) );

        mNfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String ssid = "Hack the North";
        String password = "uwaterloo";
        NdefMessage msg = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            msg = new NdefMessage(
                    NdefRecord.createMime("application/vnd.com.example.placeholder", ssid.getBytes()),
                    NdefRecord.createMime("application/vnd.com.example.placeholder", password.getBytes())
            );
        }
        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Log.d("MainActivity", "procesing intent");
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        NdefMessage msg = (NdefMessage) rawMsgs[0];
        System.out.println(new String(msg.getRecords()[0].getPayload()));
        new WifiConnector(this, new String(msg.getRecords()[0].getPayload()), new String(msg.getRecords()[1].getPayload()));

        Intent i = new Intent(this, SuccessActivity.class);
        startActivity(i);
    }
}
