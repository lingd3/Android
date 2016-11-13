package com.example.gd.ex4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gd on 16/10/19.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = (Button)findViewById(R.id.static_register);
        if (bt1 != null) {
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, StaticActivity.class);
                    startActivity(intent);
                }
            });
        }

        Button bt2 = (Button)findViewById(R.id.dynamic_register);
        if (bt2 != null) {
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DynamicActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
