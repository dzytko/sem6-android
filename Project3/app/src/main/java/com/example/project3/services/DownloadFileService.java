package com.example.project3.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.project3.MainActivity;
import com.example.project3.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileService extends IntentService {
    private static final String ACTION_DOWNLOAD_FILE = "com.example.project3.services.action.DOWNLOAD_FILE";
    private NotificationManager notificationManager;


    public DownloadFileService() {
        super("DownloadFileService");
    }

    public static void startFileDownload(Context context, String url) {
        Intent intent = new Intent(context, DownloadFileService.class);
        intent.setAction(ACTION_DOWNLOAD_FILE);
        intent.putExtra("url", url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(getString(R.string.app_name), name, NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(channel);
        startForeground(1, createNotification());

        if (intent == null) {
            return;
        }
        if (intent.getAction().equals(ACTION_DOWNLOAD_FILE)) {
            final String url = intent.getStringExtra("url");
            handleActionDownloadFile(url);
        }

    }

    private void handleActionDownloadFile(String addr) {
        try {
            URL url = new URL(addr);
            URLConnection connection = url.openConnection();
            connection.connect();

            int totalSize = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream());

            String filePath = url.getFile();
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            String downloadPath = Environment.getExternalStorageDirectory() + File.separator + fileName;
            OutputStream output = new FileOutputStream(downloadPath);

            byte[] downloadBuffer = new byte[1024];

            int downloadedSize = 0;
            int bytesRead;
            while ((bytesRead = input.read(downloadBuffer)) != -1) {
                downloadedSize += bytesRead;
                output.write(downloadBuffer, 0, bytesRead);
                Log.i("DownloadFileService", "Downloaded " + downloadedSize + " of " + totalSize);
            }

            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(this, getString(R.string.app_name));
        notificationBuilder.setContentTitle(getString(R.string.notification_title))
                .setProgress(100, 0, false)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH);

        notificationBuilder.setChannelId(getString(R.string.app_name));
        return notificationBuilder.build();
    }
}
