package csc214.project03.readynotes.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import csc214.project03.readynotes.R;
import csc214.project03.readynotes.model.Note;


/**
 * Created by Charlotte on 4/29/2017.
 *
 */

public class NoteHolder extends RecyclerView.ViewHolder{

    private TextView mNote;
    private TextView mDate;
    private TextView mCat;

    public NoteHolder(View itemView) {
        super(itemView);
        mNote = (TextView)itemView.findViewById(R.id.basicNoteText);
        mDate = (TextView)itemView.findViewById(R.id.basicDateText);
        mCat = (TextView)itemView.findViewById(R.id.basicCatText);

    }

    public void bindNote(Note note){
        mNote.setText(note.getNotes());
        mDate.setText(note.getEditDate().toString());
        mCat.setText(note.getCatString());
    }
}
