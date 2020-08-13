package com.mejrabsoft.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Switch wifiswitch;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiswitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    wifiswitch.setText("Wifi is ON");
                    Toast.makeText(getApplicationContext(), "Wifi is on", Toast.LENGTH_SHORT).show();


                } else {
                    wifiManager.setWifiEnabled(false);
                    wifiswitch.setText("Wifi is OFF");
                    Toast.makeText(getApplicationContext(), "Wifi is off", Toast.LENGTH_SHORT).show();


                }
            }
        });

        if (wifiManager.isWifiEnabled()) {

            wifiswitch.setChecked(true);
            wifiswitch.setText("wifi is ON");
            Toast.makeText(getApplicationContext(), "Wifi is on", Toast.LENGTH_SHORT).show();

        } else {
            wifiswitch.setChecked(false);
            wifiswitch.setText("wifi is OFF");
            Toast.makeText(getApplicationContext(), "Wifi is off", Toast.LENGTH_SHORT).show();

        }
    }

    private BroadcastReceiver wifistaterecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int wifistateextra = getIntent().getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifistateextra) {

                case WifiManager.WIFI_STATE_ENABLED:
                    wifiswitch.setChecked(true);
                    wifiswitch.setText("wifi is ON");
                    Toast.makeText(context, "Wifi is on", Toast.LENGTH_SHORT).show();
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    wifiswitch.setChecked(false);
                    wifiswitch.setText("wifi is OFF");
                    Toast.makeText(context, "Wifi is off", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifistaterecevier,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifistaterecevier);
    }
}
