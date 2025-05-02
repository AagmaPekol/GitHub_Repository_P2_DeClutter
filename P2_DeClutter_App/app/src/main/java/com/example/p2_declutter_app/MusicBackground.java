package com.example.p2_declutter_app;

import static android.app.Service.START_STICKY;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicBackground {
//    private MediaPlayer mediaPlayer;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mediaPlayer = MediaPlayer.create(this, R.raw.background_music); //Here the music file should be
//            mediaPlayer.start();
//        mediaPlayer.setLooping(true); // Loop the music ere
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (!mediaPlayer.isPlaying()) {
//            mediaPlayer.start();
//        }
//        return START_STICKY; // Keeps service running
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null; // We don’t need to bind
//    }
}