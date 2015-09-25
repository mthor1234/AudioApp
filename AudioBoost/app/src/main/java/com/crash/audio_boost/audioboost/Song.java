package com.crash.audio_boost.audioboost;

import android.graphics.Bitmap;

import java.io.File;

/**
 Song is used to hold all the song's information.  A list of all the user's 'Songs' are stored and can be edited by the user
 Will also be vital for updating song information and automating finding song information such as album art, correct titles, etc.
 */
public class Song {

    private String track, artist, album, track_num, genre, year, filepath, album_ID;
    private int track_ID;
    private Bitmap album_Art;

    private File file;

//    public Song(){
//
//    }

    public Song(String album, File file, String filepath, String artist, String track) {
        this.album = album;
        this.file = file;
        this.filepath = filepath;
        this.artist = artist;
        this.track = track;
    }

    public Song(String album, String album_ID, String artist, File file, String filepath, String track, String year) {
        this.album = album;
        this.album_ID = album_ID;
        this.artist = artist;
        this.file = file;
        this.filepath = filepath;
        this.track = track;
        this.year = year;
    }

    public Song(String album, Bitmap album_Art, String album_ID, String artist, String genre, String track, int track_ID, String track_num, String year) {
        this.album = album;
        this.album_Art = album_Art;
        this.album_ID = album_ID;
        this.artist = artist;
        this.genre = genre;
        this.track = track;
        this.track_ID = track_ID;
        this.track_num = track_num;
        this.year = year;
    }



    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Bitmap getAlbum_Art() {
        return album_Art;
    }

    public void setAlbum_Art(Bitmap album_Art) {
        this.album_Art = album_Art;
    }

    public String getAlbum_ID() {
        return album_ID;
    }

    public void setAlbum_ID(String album_ID) {
        this.album_ID = album_ID;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public int getTrack_ID() {
        return track_ID;
    }

    public void setTrack_ID(int track_ID) {
        this.track_ID = track_ID;
    }

    public String getTrack_num() {
        return track_num;
    }

    public void setTrack_num(String track_num) {
        this.track_num = track_num;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
