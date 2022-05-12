package com.example.project2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    Button cancelButton;
    Button saveButton;
    Button websiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        manufacturerInput = findViewById(R.id.manufacturerInput);
        modelInput = findViewById(R.id.modelInput);
        androidVersionInput = findViewById(R.id.androidVersionInput);
        websiteInput = findViewById(R.id.websiteInput);

        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        websiteButton = findViewById(R.id.websiteButton);

        if (getIntent().hasExtra("phone")) {
            phone = (Phone) getIntent().getExtras().get("phone");
            manufacturerInput.setText(phone.getManufacturer());
            modelInput.setText(phone.getModel());
            androidVersionInput.setText(phone.getAndroidVersion());
            websiteInput.setText(phone.getPageUrl());
        }

        cancelButton.setOnClickListener(view -> {
            finish();
        });
        saveButton.setOnClickListener(view -> {
            if (!validateInputs()) {
                return;
            }

            phone = new Phone(
                    manufacturerInput.getText().toString(),
                    modelInput.getText().toString(),
                    androidVersionInput.getText().toString(),
                    websiteInput.getText().toString()
            );

            if (getIntent().hasExtra("phone")) {
                phone.setId(((Phone) getIntent().getExtras().get("phone")).getId());
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("phone", phone);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        websiteButton.setOnClickListener(view -> {
            if (websiteInput.getText().toString().isEmpty()) {
                websiteInput.setError("Pole jest wymagane");
                return;
            }
            String url = websiteInput.getText().toString();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
            );
            startActivity(browserIntent);
        });

        manufacturerInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                return;
            }

            if (manufacturerInput.getText().toString().isEmpty()) {
                manufacturerInput.setError("Pole jest wymagane");
            }
        });
        modelInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                return;
            }

            if (modelInput.getText().toString().isEmpty()) {
                modelInput.setError("Pole jest wymagane");
            }
        });
        androidVersionInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                return;
            }

            if (androidVersionInput.getText().toString().isEmpty()) {
                androidVersionInput.setError("Pole jest wymagane");
            }
        });
        websiteInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                return;
            }

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