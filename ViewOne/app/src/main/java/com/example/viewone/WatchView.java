package com.example.viewone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;

public class WatchView extends View {
    int secondColor;
    int minColor;
    int hourColor;
    int scaleColor;
    Paint mSecondPaint;
    private Paint mMinPaint;
    private Paint mHourPaint;
    private Paint mScalePaint;
    private Calendar mCalendar;
    private final float innerCircleRadius=15f;


    public WatchView(Context context) {
        this(context,null);
    }
    public WatchView (Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public WatchView (Context context, AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        TypedArray a= context.obtainStyledAttributes(R.styleable.WatchView);

        secondColor = a.getColor(R.styleable.WatchView_secondColor,getResources().getColor(R.color.secondDefaultColor));
        minColor=  a.getColor(R.styleable.WatchView_minColor,getResources().getColor(R.color.minDefaultColor));
        hourColor=  a.getColor(R.styleable.WatchView_hourColor,getResources().getColor(R.color.hourDefaultColor));
        scaleColor=  a.getColor(R.styleable.WatchView_scaleColor,getResources().getColor(R.color.scaleDefaultColor));
        a.recycle();
        initPaint();
        mCalendar=Calendar.getInstance();
        mCalendar.setTimeZone(TimeZone.getDefault());
    }
    private boolean isUpdate=false;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isUpdate=true;
        post(new Runnable() {
            @Override
            public void run() {
                if(isUpdate){
                    invalidate();
                    postDelayed(this,1000);

                }else{
                    removeCallbacks(this);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isUpdate=false;
    }

    public void initPaint(){
        mSecondPaint=new Paint();
        mSecondPaint.setColor(secondColor);
        mSecondPaint.setStyle(Paint.Style.STROKE);
        mSecondPaint.setStrokeWidth(5f);
        mSecondPaint.setAntiAlias(true);

        mMinPaint=new Paint();
        mMinPaint.setColor(minColor);
        mMinPaint.setStyle(Paint.Style.STROKE);
        mMinPaint.setStrokeWidth(10f);
        mMinPaint.setAntiAlias(true);

        mHourPaint=new Paint();
        mHourPaint.setColor(hourColor);
        mHourPaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setStrokeWidth(15f);
        mHourPaint.setStrokeCap(Paint.Cap.ROUND);
        mHourPaint.setAntiAlias(true);

        mScalePaint=new Paint();
        mScalePaint.setColor(scaleColor);
        mScalePaint.setStyle(Paint.Style.STROKE);
        mScalePaint.setStrokeWidth(5f);
        mScalePaint.setAntiAlias(true);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
         int widthMode=MeasureSpec.getMode(widthMeasureSpec);
         int wideSize= MeasureSpec.getSize(widthMeasureSpec);
         int heightMode=MeasureSpec.getMode(heightMeasureSpec);
         int heightSize= MeasureSpec.getSize(heightMeasureSpec);
         int wideTargetSize=wideSize-getPaddingLeft()+getPaddingRight();
         int heightTargetSize=heightSize-getPaddingTop()-getPaddingBottom();
         int targetSize=Math.min(wideTargetSize,heightTargetSize);
         setMeasuredDimension(targetSize,targetSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        long currentMillis=System.currentTimeMillis();
        mCalendar.setTimeInMillis(currentMillis);
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        int radius=(int)(width/2f);


        canvas.drawColor(Color.parseColor("#000000"));
        canvas.drawRect(0,0,width,height,mScalePaint);
        canvas.drawCircle(radius,radius,15f,mScalePaint);
        int innerR=(int) (width/2*0.85f);
        int outerR=(int) (width/2*0.95f);

        for(int i=0;i<12;i++){
            double th=i*Math.PI*2/12;
            int innerB=(int)(Math.cos(th) *innerR);
            int innerX=height/2-innerB;
            int innerA=(int)(innerR*Math.sin(th));
            int innerY=width/2+innerA;
            int outerB=(int)(Math.cos(th) *outerR);
            int outerX=height/2-outerB;
            int outerA=(int)(outerR*Math.sin(th));
            int outerY=width/2+outerA;
            canvas.drawLine(innerX,innerY,outerX,outerY,mScalePaint);
        }
        canvas.save();
        int hourValue=mCalendar.get(Calendar.HOUR);
        int minValue=mCalendar.get(Calendar.MINUTE);
        int secondValue=mCalendar.get(Calendar.SECOND);
        int hourRadius =(int)(radius *0.6f);
        int minRadius =(int)(radius *0.75f);
        int secondRadius=(int)(radius *0.85f);
        float houroffsetRotate=minValue /2f;
        float hourRotate=hourValue *30+houroffsetRotate;
        canvas.rotate(hourRotate,radius,radius);
        canvas.drawLine(radius,radius-hourRadius,radius,radius-innerCircleRadius,mHourPaint);
        canvas.restore();

        canvas.save();
        float minRotate=minValue *6f;
        canvas.rotate(minRotate,radius,radius);
        canvas.drawLine(radius,radius-minRadius,radius,radius-innerCircleRadius,mMinPaint);
        canvas.restore();

        canvas.save();
        float secondRotate=secondValue *6f;
        canvas.rotate(secondRotate,radius,radius);
        canvas.drawLine(radius,radius-secondRadius,radius,radius-innerCircleRadius,mSecondPaint);

        canvas.restore();
    }
}
