package net.thesis.vglibvol;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WishlistDao {


    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void Winsert(Wishlist_Item wishlist_item);

    @Update
    void Wupdate(Wishlist_Item wishlist_item);

    @Delete
    void Wdelete(Wishlist_Item wishlist_item);

    @Query("DELETE FROM wishlist_table")
    void deleteWAllNotes();

    @Query("SELECT * FROM wishlist_table ORDER BY title ASC")
    LiveData<List<Wishlist_Item>> getWLAllNotes();


    @Query("SELECT * FROM wishlist_table ORDER BY title ASC")
    List<Wishlist_Item> getWAllNotes();

    @Query("SELECT title from wishlist_table")
    List<String> getWTitles();

    @Query("SELECT guid from wishlist_table")
    List<String> getWTitles_Guid();


}