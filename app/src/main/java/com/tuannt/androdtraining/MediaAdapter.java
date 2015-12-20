package com.tuannt.androdtraining;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuannt.androdtraining.models.Media;

import java.util.List;

/**
 * Created by TuanNT on 12/20/2015.
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnMediaItemListener {
        void onDeleteMediaCLick(int position);

        void onMediaItemClick(int position);
    }

    private List<Media> mMedias;
    private OnMediaItemListener mListener;

    public MediaAdapter(List<Media> list, OnMediaItemListener l) {
        this.mMedias = list;
        this.mListener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new MediaHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // get media add position
        Media media = mMedias.get(position);

        MediaHolder mediaHolder =(MediaHolder)holder;
        mediaHolder.mTvSongName.setText(media.getSongName());
        mediaHolder.mTvArtistName.setText(media.getArtistName());
        // hide delete button when media is running
        mediaHolder.mImgDelete.setSelected(media.isPlaying());
    }

    @Override
    public int getItemCount() {
        return mMedias.size();
    }

    private class MediaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTvSongName;
        private TextView mTvArtistName;
        private ImageView mImgDelete;

        public MediaHolder(View itemView) {
            super(itemView);
            mTvSongName =(TextView)itemView.findViewById(R.id.mTvSongName);
            mTvArtistName =(TextView)itemView.findViewById(R.id.mTvArtistName);
            mImgDelete =(ImageView)itemView.findViewById(R.id.mImgDelete);

            mImgDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null){
                return;
            }
            if(v.getId() == R.id.mImgDelete){
                mListener.onDeleteMediaCLick(getLayoutPosition());
            }else{
                mListener.onMediaItemClick(getLayoutPosition());
            }
        }
    }
}
