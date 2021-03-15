package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive= false;
    Button button;
    CountDownTimer countDownTimer;
    public void updateTimer(int secondsLeft){
        int minutes= (int) secondsLeft/60;
        int seconds =   secondsLeft-minutes*60;
        String ss = Integer.toString(seconds);
        if (secondsLeft<=9){
            ss="0"+ss;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+ss);
    }
    public void resetTimer(){
        counterIsActive=false;
        timerTextView.setText("0:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
    }
    public void controlTimer(View view){
        if(counterIsActive==false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            button.setText("Stop");
            countDownTimer=new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm_audio);
                    mp.start();
                    Toast.makeText(MainActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    resetTimer();
                }
            }.start();
        } else{
            resetTimer();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar) findViewById(R.id.seekBar);
        timerTextView = (TextView) findViewById(R.id.textView) ;
        button=(Button)findViewById(R.id.button);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}