package com.tuannt.androdtraining;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuannt.androdtraining.models.Media;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MediaAdapter.OnMediaItemListener, View.OnClickListener {
    private static final int REQUEST_ADD_NEW = 100;

    private List<Media> mMedias = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TextView mTvSongName;
    private ImageButton mBtnBack;
    private ImageButton mBtnPlay;
    private ImageButton mBtnPrevious;
    private ImageButton mBtnNext;
    private ImageButton mBtnAddNew;
    private ImageView mImgAboutMe;

    private MediaAdapter mAdapter;

    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mTvSongName = (TextView) findViewById(R.id.mTvSongName);
        mBtnBack = (ImageButton) findViewById(R.id.mBtnBack);
        mBtnAddNew = (ImageButton) findViewById(R.id.mBtnAddNew);
        mBtnPrevious = (ImageButton) findViewById(R.id.mBtnPrevious);
        mBtnPlay = (ImageButton) findViewById(R.id.mBtnPlay);
        mBtnNext = (ImageButton) findViewById(R.id.mBtnNext);
        mImgAboutMe = (ImageView) findViewById(R.id.mBtnAboutMe);

        initData();
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        mBtnNext.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnAddNew.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mImgAboutMe.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mAdapter = new MediaAdapter(mMedias, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            Media media = new Media("Song - " + i, "Artist - " + i);
            mMedias.add(media);
        }
    }

    private void stopCurrentSong() {
        // stop when media is running
        if (mMedias.get(mCurrentIndex).isPlaying()) {
            mMedias.get(mCurrentIndex).togglePlay();
        }
    }

    private void play() {
        mMedias.get(mCurrentIndex).togglePlay();
        Media media = mMedias.get(mCurrentIndex);
        // Update UI
        if (media.isPlaying()) {
            mTvSongName.setText(media.getSongName());
        } else {
            // Set text from string.xml
            mTvSongName.setText(R.string.media_stop);
        }
        mBtnPlay.setSelected(media.isPlaying());
        mAdapter.notifyDataSetChanged();
    }

    private void previousSong() {
        // stop current song before star next song
        stopCurrentSong();
        mCurrentIndex--;
        if (mCurrentIndex < 0) {
            mCurrentIndex = mMedias.size() - 1;
        }
        // start next song
        play();
    }

    private void nextSong() {
        // stop current song before star next song
        stopCurrentSong();
        mCurrentIndex++;
        if (mCurrentIndex > mMedias.size() - 1) {
            mCurrentIndex = 0;
        }
        // start next song
        play();
    }

    @Override
    public void onDeleteMediaCLick(int position) {
        confirmDelete(position);
    }

    private void confirmDelete(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.confirm_delete);
        dialog.setNegativeButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMedias.remove(position);
                // refresh list song
                mAdapter.notifyDataSetChanged();
                // update current after delete song
                if (mCurrentIndex > mMedias.size() - 1) {
                    mCurrentIndex = mMedias.size() - 1;
                }
            }
        });
        dialog.setPositiveButton(R.string.btn_no, null);
        dialog.show();
    }

    private void startActivityAddNew() {
        Intent intent = new Intent(this, AddNewActivity.class);
        startActivityForResult(intent, REQUEST_ADD_NEW);
    }

    private void startActivityProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivityForResult(intent, REQUEST_ADD_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_NEW) {
            if (resultCode != RESULT_OK) { // xóa thử cái này xem ra sao
                return;
            }

            String songName = data.getStringExtra(AddNewActivity.ARG_SONG_NAME);
            String artistName = data.getStringExtra(AddNewActivity.ARG_ARTIST_NAME);
            mMedias.add(new Media(songName, artistName));
            mAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Add successful", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMediaItemClick(int position) {
        stopCurrentSong();
        mCurrentIndex = position;
        play();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnNext:
                nextSong();
                break; //
            case R.id.mBtnPlay:
                play();
                break;
            case R.id.mBtnPrevious:
                previousSong();
                break;
            case R.id.mBtnAddNew:
                startActivityAddNew();
                break;
            case R.id.mBtnAboutMe:
                startActivityProfile();
                break;
            case R.id.mBtnBack:
                finish();
                break;
        }
    }
}
