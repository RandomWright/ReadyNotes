package csc214.project03.readynotes;


import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import csc214.project03.readynotes.model.Note;
import csc214.project03.readynotes.model.NotesList;
import csc214.project03.readynotes.recycler.NoteAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public final int REQUEST_IMAGE_CAPTURE = 3;
    public static int REQUEST_SHOW_PIC = 4;
    private static String TAG = "MAIN FRAGMENT";

    private Button mSave;
    private Button mPhotoButton;
    private Button mSendEmail;
    private EditText mNote;
    private NotesList mainNotes;

    private RecyclerView mRecycler;
    private NoteAdapter mAdapter;
    private String mCurrentPhotoPath;
    private boolean yesPhoto;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Note note = new Note();

        mainNotes = NotesList.getNotes(getContext());
        mainNotes.sortAll();

        mRecycler = (RecyclerView)view.findViewById(R.id.main_frame);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        update();

        mNote = (EditText)view.findViewById(R.id.noteText);

        if (savedInstanceState != null) {
            mNote.setText(savedInstanceState.getString("NOTE"));
            mCurrentPhotoPath = savedInstanceState.getString("PATH");
            yesPhoto = savedInstanceState.getBoolean("YES");
        }

        mSave = (Button)view.findViewById(R.id.saveButton);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Clicked");
                String notes = mNote.getText().toString();
                if(!notes.equals("")){
                    note.setNotes(notes);
                    note.setPicPath(mCurrentPhotoPath);
                    mainNotes.addNote(note);
                    Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();

                }

                update();
            }
        });

       yesPhoto = false;

        mPhotoButton = (Button)view.findViewById(R.id.add_Photo_Button);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yesPhoto){
                    FragmentManager manager = getFragmentManager();
                    ImageDialogFragment dialog = ImageDialogFragment.newInstance(mCurrentPhotoPath);
                    dialog.setTargetFragment(MainFragment.this, REQUEST_SHOW_PIC);
                    dialog.show(manager, "PHOTO");

                }
                else {
                    try {
                        takeAPhoto();
                        mPhotoButton.setText("View Photo");
                        yesPhoto = true;
                        note.setPicPath(mCurrentPhotoPath);
                    } catch (IOException e) {
                        Log.e(TAG, "IOException" + e.getMessage());
                    }
                }
            }
        });

        mSendEmail = (Button)view.findViewById(R.id.emailButton);
        mSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT, "ReadyNote");
                i.putExtra(Intent.EXTRA_TEXT   , mNote.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void update(){
        List<Note> noteList = mainNotes.getAllNotes();
        if(mAdapter == null){
            mAdapter = new NoteAdapter(noteList, MainFragment.this);
            mRecycler.setAdapter(mAdapter);
        }
        else{
            mAdapter.update(noteList);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putString("PATH", mCurrentPhotoPath);
        state.putString("NOTE", mNote.getText().toString());
        state.putBoolean("YES", yesPhoto);
    }

    public void takeAPhoto() throws IOException{
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
            //make a random filename
            String filename = "IMG_" + UUID.randomUUID().toString();
            //make a file in the external photos directory
            File picturesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(filename, ".jpg", picturesDir);

            mCurrentPhotoPath = photoFile.getAbsolutePath();

            Log.d(TAG, "photo location: " + photoFile.toString());

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "csc214.project03.readynotes.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}
