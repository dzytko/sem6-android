package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MySurfaceView drawingSurface;
    Button redButton;
    Button yellowButton;
    Button blueButton;
    Button greenButton;
    Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingSurface = findViewById(R.id.surface_view);
        drawingSurface.setColor(getResources().getColor(R.color.red));

        redButton = findViewById(R.id.red_button);
        yellowButton = findViewById(R.id.yellow_button);
        blueButton = findViewById(R.id.blue_button);
        greenButton = findViewById(R.id.green_button);
        clearButton = findViewById(R.id.clear_button);

        redButton.setBackgroundColor(getResources().getColor(R.color.red));
        yellowButton.setBackgroundColor(getResources().getColor(R.color.yellow));
        blueButton.setBackgroundColor(getResources().getColor(R.color.blue));
        greenButton.setBackgroundColor(getResources().getColor(R.color.green));
        clearButton.setBackgroundColor(getResources().getColor(R.color.gray));

        redButton.setOnClickListener(v -> drawingSurface.setColor(getResources().getColor(R.color.red)));
        yellowButton.setOnClickListener(v -> drawingSurface.setColor(getResources().getColor(R.color.yellow)));
        blueButton.setOnClickListener(v -> drawingSurface.setColor(getResources().getColor(R.color.blue)));
        greenButton.setOnClickListener(v -> drawingSurface.setColor(getResources().getColor(R.color.green)));
        clearButton.setOnClickListener(v -> drawingSurface.clear());
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawingSurface.stopDrawing();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawingSurface.startDrawing();
    }
}