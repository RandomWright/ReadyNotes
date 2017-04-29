package csc214.project03.readynotes.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import csc214.project03.readynotes.model.Note;
import csc214.project03.readynotes.database.NoteDbSchema.NoteTable;

/**
 * Created by Charlotte on 4/27/2017.
 */

public class NoteCursorWrapper extends CursorWrapper{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        Note note = new Note(UUID.fromString(getString(getColumnIndex(NoteTable.Cols.ID))));

        note.setNotes(getString(getColumnIndex(NoteTable.Cols.NOTE)));
        note.setCreate(new Date(getLong(getColumnIndex(NoteTable.Cols.CREATE))));
        note.setEditDate(new Date(getLong(getColumnIndex(NoteTable.Cols.EDIT))));
        note.setPicPath(getString(getColumnIndex(NoteTable.Cols.PICPATH)));
        String[] cat = getString(getColumnIndex(NoteTable.Cols.CATEGORY)).split("|");
        note.setCategories(cat);

        return note;
    }
}
