package com.example.dmacs.myapplication;

/**
 * Created by dmacs on 4/8/16.
 */
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * Created by digen on 3/8/16.
 */
public class BackgroundAudioService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;
    int length= 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        int mfile[] = new int[5];

        mfile[0]= R.raw.bgm1;
        mfile[1]= R.raw.bgm2;
        mfile[2]= R.raw.bgm3;
        mfile[3]= R.raw.bgm4;
        mfile[4]= R.raw.bgm5;

        Random random = new Random();
        int Low = 0;
        int High = mfile.length;
        int randomInt = random.nextInt(High-Low) + Low;

        mediaPlayer = MediaPlayer.create(this, mfile[randomInt]);// raw/s.mp3
        mediaPlayer.setOnCompletionListener(this);
    }

        @Override
        public int onStartCommand (Intent intent,int flags, int startId){
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            return START_STICKY;
        }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();

    }


    public void onCompletion(MediaPlayer _mediaPlayer) {
        stopSelf();
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

 }

