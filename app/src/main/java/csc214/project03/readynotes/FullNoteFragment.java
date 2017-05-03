package csc214.project03.readynotes;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import csc214.project03.readynotes.model.Note;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullNoteFragment extends DialogFragment {

    private static final String ARG_PATH = "ARG_PATH";
    private static final String ARG_CREATE = "ARG_CREATE";
    private static final String ARG_EDIT = "ARG_EDIT";
    private static final String ARG_NOTE = "ARG_NOTE";
    private static final String ARG_CAT = "ARG_CATEGORIES";
    public static final String ARG_ID = "ARG_ID";



    public FullNoteFragment() {
        // Required empty public constructor
    }

    public static FullNoteFragment newInstance(Note note){
        FullNoteFragment dialog = new FullNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, note.getId().toString());
        args.putString(ARG_PATH, note.getPicPath());
        args.putString(ARG_CAT, note.getCatString());
        args.putString(ARG_EDIT, "Edit: " + note.getEditDate().toString());
        args.putString(ARG_CREATE, "Create: " + note.getCreate().toString());
        args.putString(ARG_NOTE, note.getNotes());
        dialog.setArguments(args);
        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle saved){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_full_note, null);

        final Bundle args = getArguments();

        ImageView image = (ImageView)view.findViewById(R.id.fullImage);

        TextView edit = (TextView)view.findViewById(R.id.fullEdit);
        TextView note = (TextView)view.findViewById(R.id.fullNote);
        TextView create = (TextView)view.findViewById(R.id.fullCreate);
        TextView cat = (TextView)view.findViewById(R.id.fullCat);

        note.setText(args.getString(ARG_NOTE));
        if (! (args.getString(ARG_PATH) == null)){
            File imgFile = new File(args.getString(ARG_PATH));
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);

            }
        }


        edit.setText(args.getString(ARG_EDIT));
        create.setText(args.getString(ARG_CREATE));
        cat.setText(args.getString(ARG_CAT));



        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Note")
                .setNeutralButton("Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .setNegativeButton("Edit",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(ARG_ID, args.getString(ARG_ID));
                                sendResult(Activity.RESULT_CANCELED, resultIntent);
                            }
                        })
                .create();
    }

    private void sendResult(int result) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, null);
    }

    private void sendResult(int result, Intent i) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, i);
    }



}
