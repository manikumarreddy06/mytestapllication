package com.myproject.myapplication;

import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        findViewById(R.id.button).setOnClickListener(listener);

    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final float fromRadius = getResources().getDimension(R.dimen.start_corner_radius);
            final float toRadius = v.getHeight() / 2;
            final GradientDrawable gd = (GradientDrawable) v.getBackground();
            final ValueAnimator animator = ValueAnimator.ofFloat(fromRadius, toRadius);
            animator.setDuration(2000)
                    .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            gd.setCornerRadius(value);
                        }
                    });
            animator.start();
        }
    };
}