package csc214.project03.readynotes.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import csc214.project03.readynotes.database.NoteCursorWrapper;
import csc214.project03.readynotes.database.NoteDbHelper;
import csc214.project03.readynotes.database.NoteDbSchema;
import csc214.project03.readynotes.database.NoteDbSchema.NoteTable;

/**
 * Created by Charlotte on 4/27/2017.
 *
 */

public class NotesList {
    private static NotesList sNotes;
    public ArrayList<Category> sCategoryList;

    private static final String TAG = "NOTELIST";
    private final SQLiteDatabase mNoteDatabase;

    public NotesList(Context context){
        mNoteDatabase = new NoteDbHelper(context).getWritableDatabase();
        sCategoryList = new ArrayList<Category>();
        sCategoryList.add(new Category("Money", new String[]{"cash", "money", "$", "dollar"}, null));
        sCategoryList.add(new Category("CSC 214", new String[]{"csc 214", "android", "CSC214", "Mobile App", "2017SPRING", "ROBERT STJACQUES"},
                new TimeFrame(new int[]{3,5}, 15, 25, 16, 40)));
        sCategoryList.add(new Category("Weekday", new String[]{"Weekday"},
                new TimeFrame(new int[]{2,3,4,5,6}, 0, 0, 24, 0)));
    }

    public static synchronized NotesList getNotes(Context context){
        if(sNotes == null){
            sNotes = new NotesList(context);
        }
        return sNotes;
    }

    public void sortAll(){
        Log.d(TAG, "SORT ALL");
        List<Note> noteList = getAllNotes();
        for (Category c: sCategoryList) {
            for (Note n: noteList) {
                c.sort(n);
                updateNote(n);
            }
        }
    }

    public void addNote(Note note){
        for (Category c: sCategoryList) {
            c.sort(note);
        }
        ContentValues values = getNotesValues(note);
        mNoteDatabase.insert(NoteTable.TABLENAME, null, values);
    }

    public void deleteNote(Note note){
        String[] whereArgs = new String[] {note.getId().toString()};
        mNoteDatabase.delete(NoteTable.TABLENAME, "_id=?", whereArgs);
    }

    public void updateNote(Note note){
        String id = note.getId().toString();
        note.setEditDate(new Date());
        ContentValues values = getNotesValues(note);
        mNoteDatabase.update(NoteTable.TABLENAME,
                values,
                NoteTable.Cols.ID + "=?",
                new String[]{id});
    }

    public List<Note> getAllNotes(){
        NoteCursorWrapper wrapper = queryNotes(null, null);
        List<Note> list = new ArrayList<Note>();

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                Note note = wrapper.getNote();
                if(note.getNotes().equals(""))
                    deleteNote(note);
                else{
                    list.add(note);
                }
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }

        return list;
    }


    private NoteCursorWrapper queryNotes(String where, String[] args){
        Cursor cursor = mNoteDatabase.query(
                NoteTable.TABLENAME, //Table name
                null,               //which columns
                where,              //where clause
                args,               //where arguments
                null,               //group by
                null,               //having
                NoteTable.Cols.EDIT + " DESC"   //order by
        );

        return new NoteCursorWrapper(cursor);
    }


    public static ContentValues getNotesValues(Note note){
        ContentValues values = new ContentValues();

        values.put(NoteTable.Cols.ID, note.getId().toString());
        values.put(NoteTable.Cols.NOTE, note.getNotes());
        values.put(NoteTable.Cols.CREATE, note.getCreate().getTime());
        values.put(NoteTable.Cols.EDIT, note.getEditDate().getTime());
        values.put(NoteTable.Cols.PICPATH, note.getPicPath());
        values.put(NoteTable.Cols.CATEGORY, note.getCatString());

        Log.e(TAG, values.toString());
        return values;
    }
}
