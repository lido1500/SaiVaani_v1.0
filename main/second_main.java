package com.example.dmacs.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.media.MediaPlayer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import android.content.res.TypedArray;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;

import mehdi.sakout.fancybuttons.FancyButton;


public class second_main extends Activity {

    private Bitmap bitmap;


    private MediaPlayer mediaPlayer= null;
    private RelativeLayout relativeLayout;
    private ImageView imageView=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen is set for the Window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        relativeLayout = (RelativeLayout)findViewById(R.id.relative1);
        String[] array_quotes;
        array_quotes = getResources().getStringArray(R.array.quotes_array);

        Random random = new Random();

        int Low = 0;
        int High = array_quotes.length;
        int i = random.nextInt(High-Low) + Low;

        ImageView img = (ImageView) findViewById(R.id.img1);
        TypedArray imgs = getResources().obtainTypedArray(R.array.apptour);
        int resID = imgs.getResourceId(i, 0);
        img.setImageResource(resID);
        img.setAlpha(210);


        BootstrapLabel bootstrapLabel= (BootstrapLabel)findViewById(R.id.text);
        bootstrapLabel.setText(array_quotes[i]);

        final AudioManager   mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        final int current_volume =mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        System.out.println(" curent System Volume is: "+current_volume);


        if(current_volume == 0) {
            AlertDialog.Builder builder1 =
                    new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder1.setMessage("Play Audio?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            int set_volume = 27;
                            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, set_volume, 0);
                        }
                    }).create().show();
        }
        try {
            mediaPlayer = new MediaPlayer();


            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }
            AssetFileDescriptor afd = getAssets().openFd("v" + i + ".mp3");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.setVolume(2f, 3f);
            mediaPlayer.start();

        } catch (final Exception e) {
            e.printStackTrace();
        }


        FancyButton Btn = (FancyButton)findViewById(R.id.repicButton);
        Btn.setTextSize(25);
        Btn.setFontIconSize(25);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(second_main.this, second_main.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                if(mediaPlayer != null) {
                    try{
                        mediaPlayer.stop(); //error
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }catch(Exception e){
                        Log.d("Nitif111 Activity", e.toString());
                    }
                }

            }
        });

        FancyButton Btn1 = (FancyButton)findViewById(R.id.repyButton);
        Btn1.setTextSize(25);
        Btn1.setFontIconSize(25);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   int set_volume = 27;
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, set_volume, 0);*/
                mediaPlayer.start();
            }
        });
    }

    public void onBackPressed() {

        if(mediaPlayer != null) {
            try{
                mediaPlayer.stop(); //error
                mediaPlayer.reset();
                mediaPlayer.release();
            }catch(Exception e){
                Log.d("Nitif Activity", e.toString());
            }
        }
        Intent intent = new Intent(second_main.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Configuration config=getResources().getConfiguration();
        if(config.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.second);
        }
        else if(config.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.second);
        }
    }
}




