package com.example.placeholder;

import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class WifiConnector {

    final String networkSsid = "Hack the North";
    final String networkPassphrase = "uwaterloo";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void WifiConnector() {
        WifiNetworkSpecifier.Builder specifier = new WifiNetworkSpecifier.Builder();
        specifier.setSsid(networkSsid);
        specifier.setWpa2Passphrase(networkPassphrase);

        System.out.println(specifier);

//        final NetworkRequest.Builder request = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//                .setNetworkSpecifier(specifier.build());
//
        }


}
