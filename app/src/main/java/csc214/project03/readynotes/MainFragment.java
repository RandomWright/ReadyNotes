package csc214.project03.readynotes;


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
    private static String TAG = "MAIN FRAGMENT";

    private Button mSave;
    private Button mPhotoButton;
    private EditText mNote;
    private NotesList mainNotes;

    private RecyclerView mRecycler;
    private NoteAdapter mAdapter;
    private File mPhotoFile;
    private String mCurrentPhotoPath;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mainNotes = NotesList.getNotes(getContext());

        mRecycler = (RecyclerView)view.findViewById(R.id.main_frame);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        update();

        mNote = (EditText)view.findViewById(R.id.noteText);

        mSave = (Button)view.findViewById(R.id.saveButton);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Clicked");
                String notes = mNote.getText().toString();
                if(!notes.equals("")){
                    mainNotes.addNote(new Note(notes));
                    Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();

                }

                update();
            }
        });

        mPhotoButton = (Button)view.findViewById(R.id.add_Photo_Button);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    takeAPhoto();
                    mPhotoButton.setText("View Photo");
                }catch (IOException e){
                    Log.e(TAG, "IOException" + e.getMessage());
                }
            }
        });


        return view;
    }

    public void update(){
        List<Note> noteList = mainNotes.getAllNotes();
        if(mAdapter == null){
            mAdapter = new NoteAdapter(noteList);
            mRecycler.setAdapter(mAdapter);
        }
        else{
            mAdapter.update(noteList);
        }
    }

    public void takeAPhoto() throws IOException{
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
            //make a random filename
            String filename = "IMG_" + UUID.randomUUID().toString();
            //make a file in the external photos directory
            File picturesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            mPhotoFile = File.createTempFile(filename, ".jpg", picturesDir);

            mCurrentPhotoPath = mPhotoFile.getAbsolutePath();

            Log.d(TAG, "photo location: " + mPhotoFile.toString());

            if (mPhotoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "csc214.project03.readynotes.fileprovider", mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

}
