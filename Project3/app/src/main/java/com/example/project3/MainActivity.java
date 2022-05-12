package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView urlInput;
    Button fetchFileInfoButton;
    Button downloadFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlInput = findViewById(R.id.url_input);
        fetchFileInfoButton = findViewById(R.id.fetch_file_info_button);
        downloadFileButton = findViewById(R.id.download_file_button);

        fetchFileInfoButton.setOnClickListener(view -> {
            String url = urlInput.getText().toString();
            if (url.isEmpty()) {
                urlInput.setError("Please enter a URL");
                return;
            }

            new FetchFileInfoTask(this).execute(url);
        });
    }
}