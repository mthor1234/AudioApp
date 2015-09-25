package com.crash.audio_boost.audioboost;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class Custom_Adapter extends BaseAdapter{

    private List<Song> songs;
    private static LayoutInflater inflater = null;
    Main activity;


    public Custom_Adapter(Main a, List<Song> songs){

        this.songs = songs;
        /********** Take passed values **********/
        activity = a;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        System.out.println("Adapter is complete!");
    }


    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        System.out.println("Get View!");


        if (convertView == null) {


            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.custom_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tv_song = (TextView) vi.findViewById(R.id.tv_song);
            holder.tv_artist = (TextView) vi.findViewById(R.id.tv_artist);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (songs.size() <= 0) {
            holder.tv_song.setText("No Data");
            holder.tv_artist.setText("No Data");


        } else {

            System.out.println("Trying to get file info!");

                    holder.tv_song.setText(songs.get(position).getTrack());
                    holder.tv_artist.setText(songs.get(position).getArtist());
                    holder.album = songs.get(position).getAlbum();

        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activity.fragment_songinfo.isAdded()){
                    activity.fragment_songinfo.init(activity, songs.get(position));
                    FragmentTransaction ft = activity.fm.beginTransaction();
                    ft.replace(R.id.fragment_holder, activity.fragment_songinfo);
                    ft.addToBackStack("songinfo");
                    activity.fragment_songinfo.songPosition = position;
                    // alternatively add it with a tag
                    // trx.add(R.id.your_placehodler, new YourFragment(), "detail");
                    ft.commit();
                }
                else {
                    activity.fragment_songinfo.init(activity, songs.get(position));
                    activity.fragment_songinfo.songPosition = position;

                    FragmentTransaction ft = activity.fm.beginTransaction();
                    ft.add(R.id.fragment_holder, activity.fragment_songinfo);
                    ft.addToBackStack("songinfo");
                    ft.commit();
                }

            }
        });

        return vi;

    }

public static class ViewHolder {
    public TextView tv_song;
    public TextView tv_artist;

    String album;

}

}
