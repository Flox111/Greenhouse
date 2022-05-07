package com.maltsev.greenhouse.ui.plant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.maltsev.greenhouse.R;

public class VerticalLineView extends View {

    public static final int STROKE_WIDTH = 7;
    Rect rect;

    private boolean isLastActive = false;
    private boolean isFirstActive = false;
    private boolean isActive = false;

    private int width = 0;
    private int height = 0;

    public VerticalLineView(Context context) {
        super(context);
    }

    public VerticalLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("w", Integer.toString(canvas.getHeight()));

        width = canvas.getWidth();
        height = canvas.getHeight();

        drawTopLine(canvas);
        drawBottomLine(canvas);

        if (isActive) drawActiveCircle(canvas);
        else drawNotActiveCircle(canvas);
    }

    private void drawTopLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(STROKE_WIDTH);
        if (!isActive || isFirstActive) {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.green_no_active));
        } else {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.green3));
        }
        canvas.drawLine(width / 2, 0, width / 2, height / 2 - 45, paint);
    }

    private void drawBottomLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(STROKE_WIDTH);
        if (!isActive || isLastActive) {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.green_no_active));
        } else {
            paint.setColor(ContextCompat.getColor(getContext(), R.color.green3));
        }
        canvas.drawLine(width / 2, height / 2 + 45, width / 2, height, paint);
    }

    private void drawNotActiveCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.green_no_active));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        canvas.drawCircle(width / 2, height / 2, 10f, paint);
    }

    private void drawActiveCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.green3));
        paint.setStrokeWidth(STROKE_WIDTH);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 10f, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height / 2, 20f, paint);
    }

    public boolean isLastActive() {
        return isLastActive;
    }

    public void setLastActive(boolean lastActive) {
        isLastActive = lastActive;
    }

    public boolean isFirstActive() {
        return isFirstActive;
    }

    public void setFirstActive(boolean firstActive) {
        isFirstActive = firstActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
