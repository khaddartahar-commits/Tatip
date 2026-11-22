package com.ltprysm.iptvvpn;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;

public class MyVpnService extends VpnService {
    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ip = intent.getStringExtra("ip");
        
        Builder builder = new Builder();
        builder.addAddress("10.0.0.2", 24);
        builder.addDnsServer("8.8.8.8");
        builder.addRoute("0.0.0.0", 0);
        builder.setSession("VPN-" + ip);
        
        vpnInterface = builder.establish();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try { 
            if(vpnInterface != null) vpnInterface.close(); 
        } catch (Exception e) {}
    }
}
