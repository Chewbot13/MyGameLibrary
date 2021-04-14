package net.thesis.vglibvol;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class WishlistViewModel extends AndroidViewModel {
    private WishlistRepository wishlistRepository;

    private LiveData<List<Wishlist_Item>> allWLNotes;
    private List<Wishlist_Item> allWNotes;

    private List<String> wtitles;

    private List<String> wtitles_guid;


    public WishlistViewModel(@NonNull Application application) {
        super(application);
        wishlistRepository = new WishlistRepository(application);

        allWNotes = wishlistRepository.getAllNotes();
        wtitles = wishlistRepository.getTitles();

        allWLNotes = wishlistRepository.getAllLNotes();

        wtitles_guid = wishlistRepository.getTitles_guid();
    }

    public void insert(Wishlist_Item wishlist_item) {
        wishlistRepository.insert(wishlist_item);
    }

    public void update(Wishlist_Item wishlist_item) {
        wishlistRepository.update(wishlist_item);
    }

    public void delete(Wishlist_Item wishlist_item) {
        wishlistRepository.delete(wishlist_item);
    }

    public void deleteAllNotes() {
        wishlistRepository.deleteAllNotes();
    }




    public List<Wishlist_Item> getAllWNotes() {
        return allWNotes;

    }

    public LiveData<List<Wishlist_Item>> getWLAllNotes() {
        return allWLNotes;

    }

    public List<String> getTitles() {
        return  wtitles;
    }

    public List<String> getTitles_guid() {
        return  wtitles_guid;
    }



}