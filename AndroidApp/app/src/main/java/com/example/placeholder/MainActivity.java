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
import android.widget.TextView;

import static android.nfc.NdefRecord.createMime;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    // public static final String TAG = "placeHolder";
    //
    // private TextView mTextView;
    // private NfcAdapter mNfcAdapter;

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;
    private Context context;
    private TextView mReceival;

    private Uri[] fileUris = new Uri[1];

    private class FileUriCallback implements NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback (){
        }
        @Override
        public Uri[] createBeamUris(NfcEvent event){
            String fileName = "purple.jpg";
            File dir = getExternalFilesDir(null);
            File sendFile = new File(dir, fileName);
            sendFile.setReadable(true, false);
            fileUri = Uri.fromFile(sendFile);
            if (fileUri != null) {
                fileUris[0] = fileUri;
            } else {
                Log.e("My Activity", "No File URI available for file.");
                return null;
            }
            return fileUris;
        }
    }


    private FileUriCallback fileUriCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mTextView = (TextView) findViewById(R.id.helloWorld);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (mNfcAdapter == null || !mNfcAdapter.isEnabled()) {
            mTextView.setText("NFC is disabled.");
        } else {
            mTextView.setText("NFC is working!");
        }

        fileUriCallback = new FileUriCallback();
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        mNfcAdapter.setBeamPushUrisCallback(fileUriCallback,this);
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
        System.out.println("onResume " + getIntent().getAction());
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            System.out.println("HELELELEOEO");
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // onResume gets called after this to handle the intent
        System.out.println("onNewIntent");
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        mReceival = (TextView) findViewById(R.id.receival);
        mPdfView = (TextView) findViewById(R.id.pdfView);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);


        NdefMessage msg = (NdefMessage) rawMsgs[0];

        System.out.println(new String(msg.getRecords()[0].getPayload()));
        System.out.println("OMG");

        if (fileUris[0] != null){
            System.out.println(uri.getPath());
            File exposeFile = new File(uri.getPath());
        }
        mReceival.setText(new String(msg.getRecords()[0].getPayload()) + new String(msg.getRecords()[1].getPayload()));
        new WifiConnector(this, new String(msg.getRecords()[0].getPayload()), new String(msg.getRecords()[1].getPayload()));
    }

//    JSONFormatter json = new JSONFormatter();
}
