package com.la.hotels.inroom.ui.customviews;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class CustomDigitalCounter extends View {

    private Paint mPaint;
    private boolean isInit;  // it will be true once the clock will be initialized.
    private int backgroundColor =Color.TRANSPARENT;
    private int mTextColor =Color.BLACK;
    private float mTextSize =20;
    private long mEndTime=0;

    public CustomDigitalCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public CustomDigitalCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /** initialize necessary values */
        if (!isInit) {
            mPaint = new Paint();
            isInit = true;  // set true once initialized
        }

        /** draw the canvas-color */
        canvas.drawColor(backgroundColor);

        /** circle border */
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);


/** border of hours */
        int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mTextSize, getResources().getDisplayMetrics());
        mPaint.setTextSize(fontSize);  // set font size (optional)


        Calendar calendar = Calendar.getInstance();


        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        if(mEndTime==0) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            hour = hour > 12 ? hour - 12 : hour;
            canvas.drawText( getNumberFromDigit(hour) + ":" + getNumberFromDigit(minutes) + ":" + getNumberFromDigit(seconds), 0, mTextSize, mPaint);  // you can draw dots to denote hours as alternative
        }
        else {
            long timeDiff  =  mEndTime-calendar.getTimeInMillis() ;
            if(timeDiff>=0)
                canvas.drawText(getDuration(timeDiff), 0, mTextSize, mPaint);
            else
                canvas.drawText( "00:00:00", 0, mTextSize, mPaint);

        }
        /** invalidate the appearance for next representation of time  */
        postInvalidateDelayed(1000);
        invalidate();
    }
    public void setTextColor(int textColor)
    {
        mTextColor =textColor;
    }
    public void setTextSize(int textSize)
    {
        mTextSize =textSize;
    }
    public void setEndTime(long endTime)
    {
        mEndTime= endTime;
    }
    private    String getDuration(long milliseconds)
    {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        if (hours < 1)
            return "00:"+getNumberFromDigit(minutes) + ":" + getNumberFromDigit(seconds);
        return getNumberFromDigit(hours) + ":" + getNumberFromDigit(minutes) + ":" + getNumberFromDigit(seconds);
    }
    private    String getNumberFromDigit(int digit)
    {
        return  digit<=9 ? "0"+digit: ""+digit;

    }


}
