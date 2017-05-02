package csc214.project03.readynotes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import csc214.project03.readynotes.model.Note;
import csc214.project03.readynotes.model.NotesList;
import csc214.project03.readynotes.recycler.NoteAdapter;

public class MainActivity extends AppCompatActivity {

    //private Button mSave;
    //private EditText mNote;
    //private NotesList mainNotes;

    //private RecyclerView mRecycler;
    //private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.frame_main_act);
        if(fragment == null) {
            fragment = new MainFragment();
            manager.beginTransaction()
                    .add(R.id.frame_main_act, fragment)
                    .commit();
        }

        /**
        mainNotes = NotesList.getNotes(getApplicationContext());

        mNote = (EditText)findViewById(R.id.noteText);

        mRecycler = (RecyclerView)findViewById(R.id.main_frame);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mSave = (Button)findViewById(R.id.saveButton);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = mNote.getText().toString();
                if(notes != ""){
                    mainNotes.addNote(new Note(notes));
                    Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();

                }

                update();
            }
        });

        update();
        **/
    }

}
