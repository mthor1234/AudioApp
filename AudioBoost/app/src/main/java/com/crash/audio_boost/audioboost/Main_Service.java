package com.crash.audio_boost.audioboost;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class Main_Service extends Service {


    private Notification mNotification;

    private static final int NOTIFICATION_ID = 1;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

    setUpAsForeground("Audio Boost");


        IntentFilter iF = new IntentFilter();

        // Read action when music player changed current song
        // I just try it with stock music player form android

        // stock music player
        iF.addAction("com.android.music.metachanged");

        // MIUI music player
        iF.addAction("com.miui.player.metachanged");

        // HTC music player
        iF.addAction("com.htc.music.metachanged");

        // WinAmp
        iF.addAction("com.nullsoft.winamp.metachanged");

        // MyTouch4G
        iF.addAction("com.real.IMP.metachanged");

        registerReceiver(mReceiver, iF);

//        ContentResolver cr = this.getContentResolver();
//
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
//        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
//        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
//
//        int count = 0;
//
//        if(cur != null)
//        {
//            count = cur.getCount();
//
//            if(count > 0)
//            {
//                while(cur.moveToNext())
//                {
//                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
//
//                    // Add code to get more column here
//
//                    // Save to your list here
//                    System.out.println(getFileStreamPath(data));
//                }
//
//            }
//        }
//
//        cur.close();



        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }


    void setUpAsForeground(String text) {

        Intent notificationIntent = new Intent(this, Main.class);
//        notificationIntent.setAction(SyncStateContract.Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Audio Boost")
                .setTicker("Boosting")
                .setContentText("Boosting Audio")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(
                        Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true).build();
        startForeground(101,
                notification);



    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {


            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            Log.d("Music", artist + ":" + album + ":" + track);
            Log.d("Tits!", artist + ":" + album + ":" + track);


            // have fun with it <span class="wp-smiley wp-emoji wp-emoji-smile" title=":)">:)</span>
        }
    };

}
