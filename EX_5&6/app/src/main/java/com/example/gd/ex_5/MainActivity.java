package com.example.gd.ex_5;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by gd on 16/11/2.
 */
public class MainActivity extends AppCompatActivity {

    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private MusicService ms;
    private Button play;
    private Button stop;
    private Button quit;
    private TextView status, all, current;
    private SeekBar seekBar;
    private ImageView image;
    private float rotationCount = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        all = (TextView)findViewById(R.id.all);
        current = (TextView)findViewById(R.id.current);
        image = (ImageView)findViewById(R.id.image);
        status = (TextView)findViewById(R.id.status);

        //handler
        final Handler mHandler = new Handler();
        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (all != null) {  //更新当前播放进度的文本
                    all.setText(time.format(ms.mp.getDuration()));
                    current.setText(time.format(ms.mp.getCurrentPosition()));
                }
                if (ms.mp.isPlaying()) {  //更新进度条
                    seekBar.setProgress(ms.mp.getCurrentPosition());
                    seekBar.setMax(ms.mp.getDuration());
                }
                if (ms.mp.isPlaying()) {    //控制图片旋转
                    if (image != null) {
                        rotationCount += 1;
                        image.setRotation(rotationCount);
                    }
                }
                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.postDelayed(mRunnable, 0);


        //PLAY按钮
        play = (Button)findViewById(R.id.play);
        if (play != null) {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ms.mp.isPlaying()) {
                        if (status != null) {
                            status.setText("Pause");
                            play.setText("PLAY");
                        }
                    }
                    else {
                        if (status != null) {
                            status.setText("Playing");
                            play.setText("PAUSE");
                        }
                    }
                    ms.play();
                }
            });
        }

        //STOP按钮
        stop = (Button)findViewById(R.id.stop);
        if (stop != null) {
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (status != null) {
                        status.setText("Stop");
                        play.setText("PLAY");
                    }
                    ms.stop();
                }
            });
        }

        //QUIT按钮
        quit = (Button)findViewById(R.id.quit);
        if (quit != null) {
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHandler.removeCallbacks(mRunnable);
                    unbindService(sc);
                    try {
                        MainActivity.this.finish();
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //控制拖动进度条的响应
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.v("pppp", seekBar.getProgress()+"");
                ms.mp.seekTo(seekBar.getProgress());
                current.setText(time.format(ms.mp.getCurrentPosition()));
            }
        });

    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ms = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ms.mp.release();
        }
    };
}



















