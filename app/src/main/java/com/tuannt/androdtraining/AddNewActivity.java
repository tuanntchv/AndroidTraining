package com.tuannt.androdtraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by TuanNT on 12/20/2015.
 */
public class AddNewActivity extends AppCompatActivity {
    public static final String ARG_SONG_NAME = "arg_song_name";
    public static final String ARG_ARTIST_NAME = "arg_artist_name";


    private EditText mEdtSongName;
    private EditText mEdtAritstName;
    private Button mBtnSave;
    private Button mBtnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        mEdtSongName = (EditText) findViewById(R.id.mEdtSongName);
        mEdtAritstName = (EditText) findViewById(R.id.mEdtArtistName);
        mBtnSave = (Button) findViewById(R.id.mBtnSave);
        mBtnBack = (Button) findViewById(R.id.mBtnBack);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                // pass data to MainActivity
                intent.putExtra(ARG_SONG_NAME, mEdtSongName.getText().toString());
                intent.putExtra(ARG_ARTIST_NAME, mEdtAritstName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
