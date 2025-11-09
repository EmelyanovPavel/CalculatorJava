package com.example.calculatorjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ButtonClick", "Button 0 clicked");
                int id = view.getId();
                view.setVisibility(View.GONE);
                int name = view.getVisibility();
                int name2 = view.getVisibility();
            }
        });
    }
}