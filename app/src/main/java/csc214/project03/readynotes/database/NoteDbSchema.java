package csc214.project03.readynotes.database;

/**
 * Created by Charlotte on 4/26/2017.
 * Schema for the Notes Database
 */

public class NoteDbSchema {
    public static final String DATABASE_NAME = "notes.db";

    public static final class NoteTable {
        public static final String TABLENAME = "notes";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NOTE = "note";
            public static final String CREATE = "create";
            public static final String EDIT = "edit";
            public static final String PICPATH = "picture_path";
            public static final String CATEGORY = "categories";
        }
    }

}
