package com.example.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by junsuk on 2017. 3. 24..
 */

public class JoystickView extends View {
    private Paint mBackgroundPaint = new Paint();
    private Paint mJoystickPaint = new Paint();
    private float mX = 200;   // 0 ~ 400
    private float mY = 200;   // 0 ~ 400

    // 코드로 생성할 때
    public JoystickView(Context context) {
        this(context, null);
    }

    // XML에 삽입할 때
    public JoystickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBackgroundPaint.setColor(Color.BLACK);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);

        mJoystickPaint.setColor(Color.RED);
    }



    // View의 모양을 그리는 곳
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(200, 200, 200, mBackgroundPaint);

        canvas.drawCircle(mX, mY, 100, mJoystickPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                final int historySize = event.getHistorySize();
                final int pointerCount = event.getPointerCount();
                for (int h = 0; h < historySize; h++) {
                    for (int p = 0; p < pointerCount; p++) {
                        mX = event.getHistoricalX(p, h);
                        mY = event.getHistoricalY(p, h);
                        if (Math.sqrt(Math.pow(200 - mX, 2) + Math.pow(200 - mY, 2)) > 100) {
                            return true;
                        }
                        // onDraw() 를 호출
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                mX = 200;
                mY = 200;
                invalidate();
                break;
        }


        return true;
    }

    // 크기 결정
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 위치
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

}
