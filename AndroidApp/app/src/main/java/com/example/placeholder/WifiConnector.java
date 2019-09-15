package com.example.placeholder;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;


public class WifiConnector {

    public WifiConnector(Context context, String networkSSID, String networkPass) {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\""+ networkPass +"\"";
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();

                break;
            }
        }
    }}

    // ANDROID 29 :(

//        WifiNetworkSpecifier.Builder specifier = new WifiNetworkSpecifier.Builder();
//        specifier.setSsid(networkSsid);
//        specifier.setWpa2Passphrase(networkPassphrase);
//        WifiNetworkSpecifier wifiNetworkSpecifier = specifier.build();
//
//        final NetworkRequest.Builder request = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//                .setNetworkSpecifier(wifiNetworkSpecifier);
//
//        NetworkRequest networkRequest = request.build();
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
//            @Override
//            public void onAvailable(@NonNull Network network) {
//                //Use this network object to Send request.
//                //eg - Using OkHttp library to create a service request
//
//                super.onAvailable(network);
//            }
//
//
//        };
//        cm.requestNetwork(networkRequest, networkCallback);
