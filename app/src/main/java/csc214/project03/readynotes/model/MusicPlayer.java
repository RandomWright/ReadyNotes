package csc214.project03.readynotes.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class MusicPlayer {
    private static final String TAG = "MusicPlayer";
    private static final String TRACKS_FOLDER = "tracks";

    private List<Music> ITEMS;
    private AssetManager mAssets;
    private static SoundPool mSoundPool;

    public MusicPlayer(Context context) {
        ITEMS = new ArrayList<Music>();
        mAssets = context.getAssets();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        String[] trackNames;
        try {
            trackNames = mAssets.list(TRACKS_FOLDER);
            int i = 1;
            for (String filename: trackNames) {
                String path =TRACKS_FOLDER + "/" + filename;
                Music track = new Music(path, "Track " + i, "Album", "Person");
                ITEMS.add(track);

                try {
                    AssetFileDescriptor afd = mAssets.openFd(track.getPath());
                    int soundId = mSoundPool.load(afd, 1);
                    track.setId(soundId);
                } catch (IOException ioe) {
                    Log.e(TAG, "could not load from file: " + track.getPath(), ioe);
                }

                i = i + 1;
            }
        }catch (IOException ioe){
            Log.e(TAG, "could not load sound files.", ioe);
        }
    }

    public static void play(Music track) {
        Integer id = track.getId();
        if(id != null){
            mSoundPool.play(
                    id,
                    1.0f,
                    1.0f,
                    1,
                    0,
                    1.0f
            );
        }
    }

    public void play(final int loc) {

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                Music track = ITEMS.get(loc);
                Integer id = track.getId();
                mSoundPool.play(
                        id,
                        1.0f,
                        1.0f,
                        1,
                        0,
                        1.0f
                );
            }
        });

    }

    public List<Music> getMusic(){
        return ITEMS;
    }

    public void release() {
        mSoundPool.release();
    }

    public class Music{
        private final String mPath;
        private final String mName;
        private final String mAlbum;
        private final String mArtist;
        private Integer mId;

        public Music(String path, String name, String album, String artist){
            mPath = path;
            mName = name;
            mAlbum = album;
            mArtist = artist;
        }

        public String getPath() {
            return mPath;
        }

        public String getName() {
            return mName;
        }

        public String getAlbum() {
            return mAlbum;
        }

        public String getArtist() {
            return mArtist;
        }

        public Integer getId() {
            return mId;
        }

        public void setId(Integer mId) {
            this.mId = mId;
        }


    }

}