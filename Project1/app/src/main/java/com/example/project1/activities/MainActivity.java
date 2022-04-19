package com.example.project1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;


public class MainActivity extends AppCompatActivity {
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText gradeCountInput;
    private Button gradesButton;
    private Button resultButton;
    private TextView averageTextView;
    private double average;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        gradeCountInput = findViewById(R.id.gradeCountInput);
        gradesButton = findViewById(R.id.gradesButton);
        averageTextView = findViewById(R.id.average);
        resultButton = findViewById(R.id.resultButton);

        updateButtonVisibility();

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

        ActivityResultLauncher<Intent> gradeActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != RESULT_OK) {
                        return;
                    }
                    Intent data = result.getData();
                    if (data == null) {
                        return;
                    }

                    average = (double) data.getExtras().get("average");
                    double roundOff = Math.round(average * 100.0) / 100.0;
                    String res = "Twoja średnia to: " + roundOff;
                    averageTextView.setText(res);
                    averageTextView.setVisibility(View.VISIBLE);

                    String buttonText;
                    if (average >= 3)
                        buttonText = "Super :)";
                    else
                        buttonText = "Tym razem mi nie poszło";

                    gradesButton.setVisibility(View.GONE);
                    resultButton.setText(buttonText);
                    resultButton.setVisibility(View.VISIBLE);
                    resultButton.setOnClickListener(view -> {
                        String msg;
                        if (average >= 3)
                            msg = "Gratulacje! Otrzymujesz zaliczenie!";
                        else
                            msg = "Wysyłam podanie o zaliczenie warunkowe";
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });

        gradesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GradesActivity.class);
            intent.putExtra("subject_count", Integer.parseInt(gradeCountInput.getText().toString()));
            gradeActivityResultLauncher.launch(intent);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("first_name", firstNameInput.getText().toString());
        editor.putString("last_name", lastNameInput.getText().toString());
        editor.putString("grade_count", gradeCountInput.getText().toString());

        editor.apply();
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
                !isGradeCountValid) {
            gradesButton.setVisibility(View.GONE);
        } else {
            gradesButton.setVisibility(View.VISIBLE);
        }
    }
}