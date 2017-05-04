package csc214.project03.readynotes;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.ListFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlotte on 5/3/2017.
 */

public class SongFragment extends ListFragment{


    private static final String TAG = "SONG";
    private static final String DIR = "music";
    private MediaPlayer mPlayer;
    private AssetManager mAssets;
    private List<Song> mSongList;

    public SongFragment(){

    }


    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        setRetainInstance(true);
        mAssets = getActivity().getAssets();
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "PREPARED");
                mPlayer.start();
            }
        });

        mSongList= new ArrayList<>();
        try {
            String[] musicNames = mAssets.list(DIR);
            for(String filename : musicNames) {
                String path = DIR + "/" + filename;
                mSongList.add(new Song(filename, path));
            }
        }
        catch (IOException ioe) {
            Log.e(TAG, "Failed song!", ioe);
        }


        ArrayAdapter<Song> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, mSongList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        Song song = mSongList.get(pos);
        try {
            AssetManager assets = getActivity().getAssets();
            AssetFileDescriptor afd = assets.openFd(song.getPath());
            if(mPlayer.isPlaying()) {
                Log.i(TAG, "Song is playing");
                mPlayer.stop();
            }
            mPlayer.reset();
            Log.i(TAG, "Setting media player data source: " + song.getName());
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mPlayer.prepare();
        }
        catch(IOException ioe) {
            Log.e(TAG, "Failed to play music: " + song.getPath());
        }

    }

    public class Song {
        private final String mName;
        private final String mPath;

        public Song(String name, String path) {
            mName = name;
            mPath = path;
        }

        public String getName() {
            return mName;
        }

        public String getPath() {
            return mPath;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestory");
        mPlayer.release();
        mPlayer = null;
    }
}
