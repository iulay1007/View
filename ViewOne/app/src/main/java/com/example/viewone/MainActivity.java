package com.example.viewone;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
        private CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView=(CustomView)findViewById(R.id.customview);
        //在1000ms内沿着X轴向右平移300像素
        //ObjectAnimator.ofFloat(customView,"translationX",0,300).setDuration(1000).start();

        //沿着X轴向右平移400像素
       // customView.smoothScrollTo(-400,0);

        ObjectAnimator animator1=ObjectAnimator.ofFloat(customView,"translationX",0.0f,200.0f,0f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(customView,"scaleX",1.0f,2.0f);
        ObjectAnimator animator3=ObjectAnimator.ofFloat(customView,"rotationX",0.0f,90.f,0.0f);
        AnimatorSet set=new AnimatorSet();
        set.setDuration(1000);
        set.play(animator1).with(animator2).after(animator3);
        set.start();
    }
}
