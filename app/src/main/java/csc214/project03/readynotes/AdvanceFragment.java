package csc214.project03.readynotes;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceFragment extends Fragment {


    private static final int REQUEST_VIDEO_CAPTURE = 6;
    private static final String TAG = "ADVANCE";

    private String mCurrentVideoPath;
    private Button mVideoButton;
    private VideoView mVideo;

    public AdvanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advance, container, false);

        mVideoButton = (Button)view.findViewById(R.id.videoButton);
        mVideo = (VideoView)view.findViewById(R.id.video);

        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takeAVideo();

                }catch (IOException e){
                    //EERRRORR
                }
            }
        });

        return view;
    }

    public void takeAVideo() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
            //make a random filename
            String filename = "VIDEO_" + UUID.randomUUID().toString();;
            //make a file in the external photos directory
            File videosDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            File videoFile = new File(videosDir, filename + ".mp4");

            mCurrentVideoPath = videoFile.getAbsolutePath();

            Log.d(TAG, "video location: " + videoFile.toString());

            if (videoFile != null) {
                Uri videoURI = FileProvider.getUriForFile(getContext(), "csc214.project03.readynotes.fileprovider", videoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_OK){
            Uri uri = Uri.parse(mCurrentVideoPath);
            mVideo.setVideoURI(uri);
            mVideo.start();
        }


    }

}
