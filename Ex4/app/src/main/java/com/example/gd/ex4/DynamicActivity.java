package com.example.gd.ex4;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by gd on 16/10/19.
 */
public class DynamicActivity extends AppCompatActivity {

    private static final String DYNAMICACTION = "com.example.gd.ex4.dynamicreceiver";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_layout);

        final DynamicReceiver dynamicReceiver = new DynamicReceiver();

        final Button button = (Button)findViewById(R.id.register);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getText().toString().equals("Register Broadcast")) {
                        button.setText("Unregister Broadcast");
                        IntentFilter dynamic_filter = new IntentFilter();
                        dynamic_filter.addAction(DYNAMICACTION);
                        registerReceiver(dynamicReceiver, dynamic_filter);
                    }
                    else {
                        button.setText("Register Broadcast");
                        unregisterReceiver(dynamicReceiver);
                    }
                }
            });
        }

        Button button2 = (Button)findViewById(R.id.send);
        final EditText editText = (EditText)findViewById(R.id.text);
        if (button2 != null && editText != null) {
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.gd.ex4.dynamicreceiver");
                    Bundle bundle = new Bundle();
                    bundle.putString("message", editText.getText().toString());
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
            });
        }

    }

}
