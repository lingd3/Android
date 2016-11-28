package com.example.gd.ex_5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by gd on 16/11/2.
 */

public class MusicService extends Service {

    private Boolean isStop = false;

    public static MediaPlayer mp = new MediaPlayer();
    public final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public MusicService() {
        try {
            mp.setDataSource("/data/K.Will-Melt.mp3");  //获取资源，进入就绪状态
            mp.prepare();
            mp.setLooping(true);    //循环播放
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //控制播放与暂停
    public void play() {
        if (isStop == true) {   //在这里设置变量控制点击stop按钮后的操作而不像pdf讲的
            isStop = false;     //因为如果写在stop()函数里，点击stop后总会再播放一会再
            try {               //停止，然而并不知道为什么。。。
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mp.isPlaying()) {
            mp.pause();
        }
        else {
            mp.start();
        }
    }

    //控制停止播放
    public void stop() {
        if (mp != null) {
            mp.stop();
            isStop = true;
        }
    }

}















