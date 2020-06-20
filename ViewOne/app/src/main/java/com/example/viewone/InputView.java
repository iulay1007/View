package com.example.viewone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InputView extends RelativeLayout {
    private int mCurrentNumber=0;
    TextView minusBtn;
    EditText valueEdt;
    TextView plusBtn;
    public InputView(Context context) {
        this(context,null);


    }
public InputView (Context context, AttributeSet attrs){
        this(context,attrs,0);
}
    public InputView (Context context, AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.input_view,this,true);
        minusBtn=this.findViewById(R.id.minus);
        valueEdt=this.findViewById(R.id.value_edt);
        plusBtn=this.findViewById(R.id.plus);

        setUpEvent();
    }
    private void setUpEvent() {
        minusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber--;
                updateText();
            }
        });
        plusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber++;
                updateText();
            }
        });
    }

    public int getNumber() {
        return mCurrentNumber;
    }

    public void setNumber(int value) {
        mCurrentNumber = value;
        this.updateText();
    }
    private void updateText(){
        valueEdt.setText(String.valueOf(mCurrentNumber));



    }
}
