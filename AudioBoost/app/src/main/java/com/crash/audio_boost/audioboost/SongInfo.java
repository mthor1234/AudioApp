package com.crash.audio_boost.audioboost;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;


public class SongInfo extends Fragment {


    Main activity;
    EditText et_song, et_artist, et_album;
    ImageView img_albumArt;
    Button btn_save, btn_auto_fill;

    Song song;
    int songPosition;
    MediaScannerConnection scanner;


    public SongInfo(){
    }

    public void init(Main activity, Song song){
        this.activity = activity;
        this.song = song;


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.songinfo,
                container, false);
//        Intent intent = getIntent();
//        int songPosition = intent.getIntExtra("position", -1); //if it's a string you stored.
//        String song = intent.getStringExtra("song"); //if it's a string you stored.
//        String artist = intent.getStringExtra("artist"); //if it's a string you stored.
//        String album = intent.getStringExtra("album"); //if it's a string you stored.


//        String song = activity.songs_list.get(songPosition).getTrack();
//        String artist = activity.songs_list.get(songPosition).getArtist();
//        String album = activity.songs_list.get(songPosition).getAlbum();
//        Bitmap albumArt = activity.songs_list.get(songPosition).getAlbum_Art();

        et_song = (EditText) view.findViewById(R.id.et_song);
        et_artist = (EditText) view.findViewById(R.id.et_artist);
        et_album = (EditText) view.findViewById(R.id.et_album);
        img_albumArt = (ImageView) view.findViewById(R.id.imgview_album_art);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        btn_auto_fill = (Button) view.findViewById(R.id.btn_autoinfo);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Updating the media
                updateMedia(song.getFile());
                refreshMedia();
                activity.songs_list.get(songPosition).setArtist(et_artist.getText().toString());

                activity.adapter.notifyDataSetChanged();

                activity.fm.popBackStack();
            }
        });

        // Pressed the button to auto fill the track information... Need to add statements to check which edit text info I'd be using
        // And if there is internet etc....
        btn_auto_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Need to add an auto

            }
        });


        return view;

    }

    public Bitmap getAlbumart(Long album_id)
    {

        System.out.println("Long Album ID is: " + album_id);

        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = activity.getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
            else{
                System.out.println("pfd is null");
            }
        } catch (Exception e) {
        }
        return bm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        String songName = song.getTrack();
        String artist = song.getArtist();
        String album = song.getAlbum();
        Bitmap albumArt; // = song.getAlbum_Art();

        et_song.setText(songName);
        et_artist.setText(artist);
        et_album.setText(album);

        albumArt = getAlbumart(Long.parseLong(song.getAlbum_ID(), 10 ));

        img_albumArt.setImageBitmap(albumArt);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



//        activity.songs_list.get(songPosition).setAlbum_Art(albumArt);

//        img_albumArt.setImageDrawable();

    }

    public void update(Song updatedSong){
        String songName = updatedSong.getTrack();
        String artist = updatedSong.getArtist();
        String album = updatedSong.getAlbum();
        Bitmap albumArt; // = song.getAlbum_Art();

        et_song.setText(songName);
        et_artist.setText(artist);
        et_album.setText(album);

        albumArt = getAlbumart(Long.parseLong(updatedSong.getAlbum_ID(), 10 ));

        img_albumArt.setImageBitmap(albumArt);
    }

        public void updateMedia(File f){


        MusicMetadataSet src_set = null;
        try {
            src_set = new MyID3().read(f);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } // read metadata

        if (src_set == null) // perhaps no metadata
        {
            Log.i("NULL", "NULL");
        }
        else
        {
//            try{
//                IMusicMetadata metadata = src_set.getSimplified();
//                String artist = metadata.getArtist();
//                String album = metadata.getAlbum();
//                String song_title = metadata.getSongTitle();
//                Number track_number = metadata.getTrackNumber();
//
//                Log.i("artist", artist);
//                Log.i("album", album);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }

            File dst = new File(song.getFilepath());
            MusicMetadata meta = new MusicMetadata("name");
            meta.setAlbum(et_album.getText().toString());
            meta.setArtist(et_artist.getText().toString());
            meta.setSongTitle(et_song.getText().toString());

            try {

                System.out.println("Trying to update music");
//                new MyID3().write(src, dst, src_set, meta);

                new MyID3().update(f, src_set, meta);

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ID3WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  // write updated metadata
        }


    }
        public void refreshMedia(){
        scanner=new MediaScannerConnection(activity.getApplicationContext(),
                new MediaScannerConnection.MediaScannerConnectionClient() {

                    public void onScanCompleted(String path, Uri uri) {
                        scanner.disconnect();
                    }

                    public void onMediaScannerConnected() {
                        scanner.scanFile(song.getFilepath(), "audio/*");
                    }
                });

        scanner.connect();
    }



}
