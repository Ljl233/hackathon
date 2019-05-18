package com.ljl.cxkball;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ImageView ballView;
    public Button buttonStart;
    public ConstraintLayout layout;
    public ProgressBar progressBar;
    public int temp = 0;
    public long timeDown;
    public long timeUp;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    public void init() {
        ballView = findViewById(R.id.ball);
        buttonStart = findViewById(R.id.bt_start);
        buttonStart.setVisibility(View.GONE);
        layout = findViewById(R.id.layout_main);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        textView = findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Animation ballRotate = AnimationUtils.loadAnimation(this, R.anim.ball_rotate);
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        temp = (i) * 150;
                        Thread.sleep(150);
                        progressBar.setProgress(temp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:
                Log.d("nidaoshirenga", "???!!!?");
                textView.setVisibility(View.INVISIBLE);

                ballView.startAnimation(ballRotate);
                timeDown = (new Date()).getTime();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(3000);
                progressBar.setProgress(0);


                mThread.start();

                break;
            case MotionEvent.ACTION_UP:
                ballRotate.cancel();
                Log.d("animation", String.valueOf(ballView.getAnimation()));
                timeUp = (new Date()).getTime();
                //stop
                mThread.interrupt();

                progressBar.setVisibility(View.INVISIBLE);
                Log.d("nidaoshirenga", "????");
                shoot();
                break;

        }
        return false;
    }

    public void shoot() {

        long time = timeUp - timeDown;
        Log.d("time time ", String.valueOf(time));

        Log.d("nidaoshi shoot a ", "????????");

        if (time >= 1500 && time <= 1700) {
            Animation ballAnimation = AnimationUtils.loadAnimation(this, R.anim.ball);
            ballView.startAnimation(ballAnimation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("你是只好鲲！！！");

                }
            }, 3000);

        } else if (time < 1500) {
            Animation badBall = AnimationUtils.loadAnimation(this, R.anim.ball_bad);
            ballView.startAnimation(badBall);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("你打球像菜虚鲲！！！低了");

                }
            }, 3000);
        } else {
            Animation highBall = AnimationUtils.loadAnimation(this, R.anim.ball_bad_high);
            ballView.startAnimation(highBall);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("你打球像菜虚鲲！！！高了");

                }
            }, 3000);
        }
        progressBar.setVisibility(View.VISIBLE);

    }

}
