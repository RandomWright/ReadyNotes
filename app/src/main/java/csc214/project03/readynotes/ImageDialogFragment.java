package csc214.project03.readynotes;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDialogFragment extends DialogFragment {

    private static final String ARG_PATH = "ARG_PATH";


    public ImageDialogFragment() {
        // Required empty public constructor
    }

    public static ImageDialogFragment newInstance(String path) {
        ImageDialogFragment dialog = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle saved){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_image_dialog, null);

        Bundle args = getArguments();

        ImageView image = (ImageView)view.findViewById(R.id.imageOfNote);

        File imgFile = new File(args.getString(ARG_PATH));
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);

        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Photo Power")
                .setNeutralButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int result) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, null);
    }

}
