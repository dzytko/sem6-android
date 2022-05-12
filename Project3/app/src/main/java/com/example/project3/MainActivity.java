package com.example.project3;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.project3.services.DownloadFileService;

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

        downloadFileButton.setOnClickListener(view -> {
            String url = urlInput.getText().toString();
            if (url.isEmpty()) {
                urlInput.setError("Please enter a URL");
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
            }

            DownloadFileService.startFileDownload(this, url);
        });
    }
}