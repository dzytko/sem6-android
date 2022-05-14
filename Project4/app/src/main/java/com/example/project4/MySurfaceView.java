package com.example.project4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int FPS = 60;
    private static final int FRAME_DELTA_MILIS = 1000 / FPS;
    private final SurfaceHolder holder;
    private final Paint endPaint = new Paint();
    private final Paint pathPaint = new Paint();
    private final Path path = new Path();
    private Thread drawThread;
    private Bitmap bitmap;
    private final Object lock = new Object();
    private boolean isRunning;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        endPaint.setAntiAlias(true);
        endPaint.setStyle(Paint.Style.FILL);
        endPaint.setColor(Color.WHITE);

        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeWidth(12);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        final int END_RADIUS = 15;
        performClick();
        float x = event.getX();
        float y = event.getY();
        Canvas canvas = new Canvas(bitmap);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                synchronized (lock) {
                    canvas.drawCircle(x, y, END_RADIUS, endPaint);
                }
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                synchronized (lock) {
                    canvas.drawPath(path, pathPaint);
                }
                return true;
            case MotionEvent.ACTION_UP:
                synchronized (lock) {
                    canvas.drawCircle(x, y, END_RADIUS, endPaint);
                }
                return true;
        }

        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        startDrawing();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        stopDrawing();
    }

    public void setColor(int color) {
        endPaint.setColor(color);
        pathPaint.setColor(color);
        path.reset();
    }

    public void clear() {
        synchronized (lock) {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.BLACK);
            path.reset();
        }
    }

    public void startDrawing() {
        drawThread = new Thread(this);
        drawThread.start();
        isRunning = true;
    }

    public void stopDrawing() {
        isRunning = false;
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            try {
                synchronized (holder) {
                    if (!holder.getSurface().isValid()) {
                        continue;
                    }

                    canvas = holder.lockCanvas(null);
                    synchronized (lock) {
                        if (isRunning) {
                            canvas.drawBitmap(bitmap, 0, 0, null);
                        }
                    }
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
//                System.out.print("Frame time: " + (System.currentTimeMillis() - startTime) + "ms, ");
//                System.out.println("FPS: " + (1000 / Math.max(1, System.currentTimeMillis() - startTime)));
            }

            try {
                Thread.sleep(Math.max(0, FRAME_DELTA_MILIS - (System.currentTimeMillis() - startTime)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
