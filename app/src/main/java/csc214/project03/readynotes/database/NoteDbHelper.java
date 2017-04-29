package csc214.project03.readynotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import csc214.project03.readynotes.database.NoteDbSchema.NoteTable;
import csc214.project03.readynotes.model.Note;

/**
 * Created by Charlotte on 4/26/2017.
 */

public class NoteDbHelper extends SQLiteOpenHelper{
    private static int VERSION = 1;

    public NoteDbHelper(Context context) {
        super(context, NoteDbSchema.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NoteTable.TABLENAME
                + "(_id integer primary key autoincrement, "
                + NoteTable.Cols.ID + ", "
                + NoteTable.Cols.NOTE + ", "
                + NoteTable.Cols.CREATE + ", "
                + NoteTable.Cols.EDIT + ", "
                + NoteTable.Cols.PICPATH + ", "
                + NoteTable.Cols.CATEGORY + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
