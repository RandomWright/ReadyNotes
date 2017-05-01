package csc214.project03.readynotes.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import csc214.project03.readynotes.R;
import csc214.project03.readynotes.model.Note;

/**
 * Created by Charlotte on 4/29/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

    private List<Note> mNoteList;

    public NoteAdapter(List<Note> noteList){
        mNoteList = noteList;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.basic_note_view, parent, false);
        NoteHolder holder = new NoteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.bindNote(mNoteList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public void update(List<Note> list){
        Log.e("UPDATE", list.toString());
        mNoteList = list;
        notifyDataSetChanged();
    }
}
