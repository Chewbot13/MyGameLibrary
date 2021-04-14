package net.thesis.vglibvol;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {


    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY title ASC")
    LiveData<List<Note>> getLAllNotes();


    @Query("SELECT * FROM note_table ORDER BY title ASC")
    List<Note> getAllNotes();

    @Query("SELECT title from note_table")
    List<String> getTitles();

    @Query("SELECT guid from note_table")
    List<String> getTitles_Guid();

    @Query("SELECT genres from note_table")
    List<String> getGenres();


}