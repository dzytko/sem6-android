package com.example.project2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2.R;
import com.example.project2.entities.Phone;

public class AddPhoneActivity extends AppCompatActivity {
    Phone phone;
    EditText manufacturerInput;
    EditText modelInput;
    EditText androidVersionInput;
    EditText websiteInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        manufacturerInput = findViewById(R.id.manufacturerInput);
        modelInput = findViewById(R.id.modelInput);
        androidVersionInput = findViewById(R.id.androidVersionInput);
        websiteInput = findViewById(R.id.websiteInput);

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> {
            finish();
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            if (!validateInputs()) {
                return;
            }

            phone = new Phone();
            phone.setManufacturer(manufacturerInput.getText().toString());
            phone.setModel(modelInput.getText().toString());
            phone.setAndroidVersion(androidVersionInput.getText().toString());
            phone.setPageUrl(websiteInput.getText().toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("phone", phone);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        manufacturerInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            if (manufacturerInput.getText().toString().isEmpty()) {
                manufacturerInput.setError("Pole jest wymagane");
            }
        });

        modelInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            if (modelInput.getText().toString().isEmpty()) {
                modelInput.setError("Pole jest wymagane");
            }
        });

        androidVersionInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            if (androidVersionInput.getText().toString().isEmpty()) {
                androidVersionInput.setError("Pole jest wymagane");
            }
        });

        websiteInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                return;

            if (websiteInput.getText().toString().isEmpty()) {
                websiteInput.setError("Pole jest wymagane");
            }
        });
    }

    private boolean validateInputs() {
        if (manufacturerInput.getText().toString().isEmpty()) {
            manufacturerInput.setError("Pole jest wymagane");
            return false;
        }
        else if (modelInput.getText().toString().isEmpty()) {
            modelInput.setError("Pole jest wymagane");
            return false;
        }
        else if (androidVersionInput.getText().toString().isEmpty()) {
            androidVersionInput.setError("Pole jest wymagane");
            return false;
        }
        else if (websiteInput.getText().toString().isEmpty()) {
            websiteInput.setError("Pole jest wymagane");
            return false;
        }
        return true;
    }
}