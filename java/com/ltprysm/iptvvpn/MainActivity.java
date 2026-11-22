package com.ltprysm.iptvvpn;

import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String[] VPN_IPS = {"57.129.110.214", "198.244.150.88", "57.128.212.172"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl("https://khaddartahar-commit.github.io/Tatip/");

        findViewById(R.id.btn1).setOnClickListener(v -> startVPN(0));
        findViewById(R.id.btn2).setOnClickListener(v -> startVPN(1));
        findViewById(R.id.btn3).setOnClickListener(v -> startVPN(2));
        findViewById(R.id.btnStop).setOnClickListener(v -> stopVPN());
    }

    void startVPN(int index){
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, index);
        } else {
            onActivityResult(index, RESULT_OK, null);
        }
    }

    void stopVPN(){
        stopService(new Intent(this, MyVpnService.class));
        Toast.makeText(this, "تم ايقاف VPN", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(this, MyVpnService.class);
            intent.putExtra("ip", VPN_IPS[requestCode]);
            startService(intent);
            Toast.makeText(this, "متصل بـ " + VPN_IPS[requestCode], Toast.LENGTH_SHORT).show();
        }
    }
}
