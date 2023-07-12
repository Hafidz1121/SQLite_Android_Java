package com.example.latihan_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "libraryDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_book";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TITLE + " TEXT, "
                        + COLUMN_AUTHOR + " TEXT, "
                        + COLUMN_PAGES + " INTEGER);";

        sqlDB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i1) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }

    void addBook(String title, String author, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        Long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed Add Book Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success Add Book Data", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData () {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(query, null);
        }

        return cursor;
    }

    void updateData(String row_id, String title, String author, String pages) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        long result = database.update(TABLE_NAME, cv, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed To Update Book Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Update Book Data", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase database = this.getWritableDatabase();

        long result = database.delete(TABLE_NAME, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed To Delete Book Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete Book Data", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);
        database.execSQL("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = '" + TABLE_NAME + "'");
    }
}
