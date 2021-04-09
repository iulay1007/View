package com.example.viewone;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class CustomView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;
    public CustomView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
    public void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int delta=destX-scrollX;
        mScroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getX();
        int y=(int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //method1
                //对left和right进行偏移
                //offsetLeftAndRight(offsetX);
                //对top和bottom进行偏移
                //offsetTopAndBottom(offsetY);


                //method2
               // LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)getLayoutParams();
                //layoutParams.leftMargin=getLeft()+offsetX;
                //layoutParams.topMargin=getTop()+offsetY;
                //setLayoutParams(layoutParams);

                //method3
                //((View)getParent()).scrollBy(-offsetX,-offsetY);

                 //method4
                layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);
        break;
        }
        return true;
    }
}
