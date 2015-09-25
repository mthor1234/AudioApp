package com.crash.audio_boost.audioboost;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Main extends FragmentActivity {

    public ArrayList<File> listValues = new ArrayList<File>();


    public ArrayList<Song> songs_list = new ArrayList<Song>();

    ListView listview;
    Custom_Adapter adapter;

    SongInfo fragment_songinfo;

    FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listview = (ListView) findViewById(R.id.lv_songs);

        getFiles();

        adapter = new Custom_Adapter(this, songs_list);

        /**************** Create Custom Adapter *********/
        listview.setAdapter(adapter);

        fm = getFragmentManager();
        fragment_songinfo = new SongInfo();

    }




//     private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        String cmd = intent.getStringExtra("command");
//        Log.v("tag ", action + " / " + cmd);
//
//        String artist = intent.getStringExtra("artist");
//        String album = intent.getStringExtra("album");
//        String track = intent.getStringExtra("track");
//
//
//        long songId = intent.getLongExtra("id", -1);
//
//
//
//
//        Log.v("tag", artist + ":" + album + ":" + track + ":" + songId);
//        Toast.makeText(Main.this, track, Toast.LENGTH_SHORT).show();
//
//
//       getFilePath(track, artist, songId);
//
//
//    }
//    };


    public void getFiles() {
        ContentResolver cr = this.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, null);

        int count = 0;

        if (cur != null) {
            count = cur.getCount();

            if (count > 0) {
                while (cur.moveToNext()) {

                        String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                        int temp = cur.getColumnIndex(MediaStore.Audio.Media.DATA);

                        File f = new File(data);


                    String artist = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String album = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    String album_ID = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    String year = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.YEAR));

                    System.out.println("Sting Album ID is: " + album_ID);

                    String track = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));


//                    Bitmap album_Art = getAlbumart(Long.parseLong(album_ID));



                    songs_list.add(new Song(album, album_ID , artist, f, data, track, year));


//                    System.out.println("FilePath: " + f.getAbsolutePath());




//                    listValues.add(f);


                        // Add code to get more column here

                        // Save to your list here

                        // Add code to get more column here

//                        // Save to your list here


                }

            }
        }
        System.out.println("Complete!");
        cur.close();

    }

//    public void updateMedia(File f){
//
//
//        MusicMetadataSet src_set = null;
//        try {
//            src_set = new MyID3().read(f);
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } // read metadata
//
//        if (src_set == null) // perhaps no metadata
//        {
//            Log.i("NULL", "NULL");
//        }
//        else
//        {
//            try{
//                IMusicMetadata metadata = src_set.getSimplified();
//                String artist = metadata.getArtist();
//                String album = metadata.getAlbum();
//                String song_title = metadata.getSongTitle();
//                Number track_number = metadata.getTrackNumber();
//                Log.i("artist", artist);
//                Log.i("album", album);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            File dst = new File(pathdata);
//            MusicMetadata meta = new MusicMetadata("name");
//            meta.setAlbum("Chirag");
//            meta.setArtist("CS");
//            try {
////                new MyID3().write(src, dst, src_set, meta);
//
//                new MyID3().update(f, src_set, meta);
//
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (ID3WriteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }  // write updated metadata
//        }
//
//
//    }


//    public void refreshMedia(){
//        scanner=new MediaScannerConnection(getApplicationContext(),
//                new MediaScannerConnection.MediaScannerConnectionClient() {
//
//                    public void onScanCompleted(String path, Uri uri) {
//                        scanner.disconnect();
//                    }
//
//                    public void onMediaScannerConnected() {
//                        scanner.scanFile(path, "audio/*");
//                    }
//                });
//
//        scanner.connect();
//    }
@Override
public void onBackPressed() {
    if(fm.getBackStackEntryCount() != 0) {
        fm.popBackStack();

    } else {
        super.onBackPressed();
    }
}
}
