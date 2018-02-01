package com.ss.androidstoragesystemstutorial.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ss.androidstoragesystemstutorial.R;

public class BroadcastReceiverSampleActivity extends AppCompatActivity {
    private CheckNetworkBroadCastReceiver checkNetworkBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver_sample);
        checkNetworkBroadCastReceiver = new CheckNetworkBroadCastReceiver();
        registerReceiver(checkNetworkBroadCastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        final EditText inputMessageEditText = findViewById(R.id.et_broadcastReceiverSample_messageInput);
        Button sendMessage = findViewById(R.id.button_broadcastReceiverSample_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMessageEditText.length() > 0) {
                    Intent intent = new Intent();
                    intent.setAction("7learn_new_message");
                    intent.putExtra(MessageBroadcastReceiver.EXTRA_KEY_NEW_MESSAGE, inputMessageEditText.getText().toString());
                    sendBroadcast(intent);
                } else {
                    inputMessageEditText.setError("Message cannot be empty");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(checkNetworkBroadCastReceiver);
    }

    private class CheckNetworkBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(context, "Wifi is connected", Toast.LENGTH_SHORT).show();
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Toast.makeText(context, "Mobile Data is connected", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
