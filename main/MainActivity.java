package com.example.dmacs.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.content.Intent;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;

import com.google.android.gms.common.api.GoogleApiClient;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends Activity {

    private MediaPlayer bgm= null;

    private Button bu;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Intent playbackServiceIntent;

    BackgroundAudioService bck= new BackgroundAudioService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen is set for the Window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        playbackServiceIntent = new Intent(this, BackgroundAudioService.class);
        startService(playbackServiceIntent);
       // stopService(playbackServiceIntent);

        FloatingActionButton credit= (FloatingActionButton)findViewById(R.id.credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("SaiVaani-The Path for your Dilemma v1.0");
                builder.setIcon(R.drawable.icon1);
                builder.setMessage("The chit-picker app relaying messages from the Divine Master, Bhagawan Sri Sathya Sai Baba.\n" +
                        "Don't ever put an expiry date for God's words. They hold true forever..." +
                        "it's just that you are unable to see it..." +
                        "\n-BABA" +
                        "\nCopyright \u00a9 2016, by-\nHAMRO AFNO SANSAR,\nhillsaistudents,SSSIHL:\n developers.hamroafnosansar@ gmail.com");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });


        final AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
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
        FancyButton Btn = (FancyButton)findViewById(R.id.btn_preview);
        Btn.setTextSize(25);
        Btn.setFontIconSize(25);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Intent in = new Intent(MainActivity.this, second_main.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        ImageView img = (ImageView) findViewById(R.id.img);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //@Override
    public void onBackPressed() {
            new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        moveTaskToBack(true);

                        stopService(playbackServiceIntent);
                        finish();
                    }
                }).create().show();

    }

    public void onPause(){

        super.onPause();

    }
     public void onResume(){
        super.onResume();
//        /mServ.resumeMusic();
    }

}




