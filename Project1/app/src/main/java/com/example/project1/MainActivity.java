package com.example.project1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText gradeCountInput;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        gradeCountInput = findViewById(R.id.gradeCountInput);
        button = findViewById(R.id.gradesButton);

        firstNameInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            String firstName = firstNameInput.getText().toString();
            if (firstName.isEmpty()) {
                firstNameInput.setError("Imię jest wymagane");
                Toast.makeText(MainActivity.this, "Imię jest wymagane", Toast.LENGTH_LONG).show();
            }
            updateButtonVisibility();

        });

        lastNameInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            String firstName = lastNameInput.getText().toString();
            if (firstName.isEmpty()) {
                lastNameInput.setError("Nazwisko jest wymagane");
                Toast.makeText(MainActivity.this, "Nazwisko jest wymagane", Toast.LENGTH_LONG).show();
            }
            updateButtonVisibility();
        });

        gradeCountInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            try {
                int gradeCount = Integer.parseInt(gradeCountInput.getText().toString());
                if (gradeCount < 5 || gradeCount > 15) {
                    gradeCountInput.setError("Liczba ocen musi być w przedziale 5-15");
                    Toast.makeText(MainActivity.this, "Liczba ocen musi być w przedziale 5-15", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                gradeCountInput.setError("Liczba ocen musi być liczbą");
                Toast.makeText(MainActivity.this, "Liczba ocen musi być liczbą", Toast.LENGTH_LONG).show();
            }
            updateButtonVisibility();
        });
    }



    private void updateButtonVisibility() {
        boolean isGradeCountValid = true;
        try {
            int gradeCount = Integer.parseInt(gradeCountInput.getText().toString());
            if (gradeCount < 5 || gradeCount > 15) {
                isGradeCountValid = false;
            }
        } catch (NumberFormatException e) {
            isGradeCountValid = false;
        }

        if (firstNameInput.getText().toString().isEmpty() ||
                lastNameInput.getText().toString().isEmpty() ||
                !isGradeCountValid)
            button.setVisibility(View.GONE);
        else
            button.setVisibility(View.VISIBLE);
    }
}