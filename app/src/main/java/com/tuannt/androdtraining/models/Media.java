package com.tuannt.androdtraining.models;

/**
 * Created by TuanNT on 12/20/2015.
 */
public class Media {
    private String songName;
    private String artistName;
    private boolean isPlaying;

    public Media(String songName, String artistName, boolean isPlaying) {
        this.songName = songName;
        this.artistName = artistName;
        this.isPlaying = isPlaying;
    }

    public Media(String songName, String artistName) {
        this.songName = songName;
        this.artistName = artistName;
    }

    public void togglePlay() {
        isPlaying = !isPlaying;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
