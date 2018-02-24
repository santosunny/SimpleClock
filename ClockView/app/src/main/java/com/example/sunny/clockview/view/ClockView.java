package com.example.sunny.clockview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

/**
 * Create on 2018/2/23 16:55
 * <p>
 * author Zhang Zhixiang
 * <p>
 * Description:
 */
public class ClockView extends View {

    private int width;
    private int height;
    private Paint mPaint;
    private int radius;

    private int hRadius = 150;
    private int mRadius = 230;
    private int sRadius = 330;

    private int hAngle;
    private int mAngle;
    private int sAngle;

    private int hour;
    private int minute;
    private int second;

    private Time time;

    public ClockView(Context ctx) {
        super(ctx);
    }

    public ClockView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        time = new Time();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawClockFrame(canvas);
        drawClockPoints(canvas);
        drawCenterPoint(canvas);
        drawPointers(canvas);
        drawTexts(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        radius = 400;
    }

    private void drawCenterPoint(Canvas canvas) {
        canvas.drawCircle(0, 0, 10, mPaint);
    }


    //画指针
    private void drawPointers(Canvas canvas) {

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(0, 0,
                (float) (sRadius * Math.sin((180 - sAngle) * (Math.PI / 180))),
                (float) (sRadius * Math.cos((180 - sAngle) * (Math.PI / 180))),
                mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(7);
        canvas.drawLine(0, 0,
                (float) (mRadius * Math.sin((180 - mAngle) * (Math.PI / 180))),
                (float) (mRadius * Math.cos((180 - mAngle) * (Math.PI / 180))),
                mPaint);

        mPaint.setStrokeWidth(9);
        canvas.drawLine(0, 0,
                (float) (hRadius * Math.sin((180 - hAngle) * (Math.PI / 180))),
                (float) (hRadius * Math.cos((180 - hAngle) * (Math.PI / 180))),
                mPaint);

    }

    private void calAngle() {
        sAngle = (int) (second / 60f * 360);
        mAngle = (int) (minute / 60f * 360) + (int) (second / 60f * 6);
        hAngle = (int) (hour / 12f * 360) + (int) (minute / 60f * 30);
    }

    public void startAnim() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (true) {
                        Thread.sleep(1000);

                        time.setToNow();

                        hour = time.hour;
                        minute = time.minute;
                        second = time.second;

                        calAngle();

                        postInvalidate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void drawTexts(Canvas canvas) {
        mPaint.setTextSize(40);

        canvas.drawText(minute + "分",
                (float) (mRadius * Math.sin((180 - mAngle) * (Math.PI / 180)) + 20),
                (float) (mRadius * Math.cos((180 - mAngle) * (Math.PI / 180)) + 20),
                mPaint);

        canvas.drawText(hour + "点",
                (float) (hRadius * Math.sin((180 - hAngle) * (Math.PI / 180)) + 20),
                (float) (hRadius * Math.cos((180 - hAngle) * (Math.PI / 180)) + 20),
                mPaint);
    }

    private void drawClockPoints(Canvas canvas) {

        canvas.translate(width / 2, height / 2);

        for (int i = 0; i < 360; i += 6) {

            if (i % 90 == 0) {
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(6);
                canvas.drawLine(0, 350, 0, 390, mPaint);
            } else if (i % 5 == 0) {
                mPaint.setStrokeWidth(4);
                canvas.drawLine(0, 360, 0, 390, mPaint);
            } else {
                mPaint.setStrokeWidth(2);
                canvas.drawLine(0, 370, 0, 390, mPaint);
            }
            canvas.rotate(6, 0, 0);

        }

    }

    private void drawClockFrame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
    }

}