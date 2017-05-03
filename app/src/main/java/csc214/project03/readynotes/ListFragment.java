package csc214.project03.readynotes;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import csc214.project03.readynotes.model.Note;
import csc214.project03.readynotes.model.NotesList;
import csc214.project03.readynotes.recycler.NoteAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private RecyclerView mOneRecycler;
    private NoteAdapter mOneAdapter;
    private RecyclerView mTwoRecycler;
    private NoteAdapter mTwoAdapter;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        NotesList mainNotes = NotesList.getNotes(getContext());
        ArrayList<Note> oneList = mainNotes.sCategoryList.get(2).getNotes();
        ArrayList<Note> twoList = mainNotes.sCategoryList.get(1).getNotes();
        System.out.println(mainNotes.sCategoryList.get(1));

        mOneRecycler = (RecyclerView)view.findViewById(R.id.catWeekday);
        mOneRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mOneAdapter = new NoteAdapter(oneList, ListFragment.this);
        mOneRecycler.setAdapter(mOneAdapter);

        mTwoRecycler = (RecyclerView)view.findViewById(R.id.csc214);
        mTwoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mTwoAdapter = new NoteAdapter(twoList, ListFragment.this);
        mTwoRecycler.setAdapter(mTwoAdapter);


        return view;
    }

}
