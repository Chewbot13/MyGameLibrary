package net.thesis.vglibvol;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


public class WishlistRepository {
    private WishlistDao wishlistDao;
    private LiveData<List<Wishlist_Item>> allWLNotes;
    private List<Wishlist_Item> allWNotes;

    private List<String> wtitles;
    private List<String> wtitles_guid;



   // private List<Note> allNames;

    public WishlistRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        wishlistDao = database.wishlistDao();
        allWNotes = wishlistDao.getWAllNotes();

        wtitles = wishlistDao.getWTitles();

        allWLNotes = wishlistDao.getWLAllNotes();

        wtitles_guid = wishlistDao.getWTitles_Guid();
        //allNames = noteDao.getAllNames();
    }

    public void insert(Wishlist_Item wishlist_item) {
        new InsertNoteAsyncTask(wishlistDao).execute(wishlist_item);
    }

    public void update(Wishlist_Item wishlist_item) {
        new UpdateNoteAsyncTask(wishlistDao).execute(wishlist_item);
    }

    public void delete(Wishlist_Item wishlist_item) {
        new DeleteNoteAsyncTask(wishlistDao).execute(wishlist_item);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(wishlistDao).execute();
    }

   // public LiveData<List<Note>> getAllNotes() {
     //   return allNotes;
    //}

    public List<Wishlist_Item> getAllNotes() {
        return allWNotes;
    }

    public LiveData<List<Wishlist_Item>> getAllLNotes() {
        return allWLNotes;
    }


    public List<String> getTitles() {return wtitles;}

    public List<String> getTitles_guid() {return wtitles_guid;}


    //public List<Note> getAllNames() {
        //return allNames;
    //}


    private static class InsertNoteAsyncTask extends AsyncTask<Wishlist_Item, Void, Void> {
        private WishlistDao wishlistDao;

        private InsertNoteAsyncTask(WishlistDao wishlistDao) {
            this.wishlistDao = wishlistDao;
        }

        @Override
        protected Void doInBackground(Wishlist_Item... wishlist_items) {
            wishlistDao.Winsert(wishlist_items[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Wishlist_Item, Void, Void> {
        private WishlistDao wishlistDao;

        private UpdateNoteAsyncTask(WishlistDao wishlistDao) {
            this.wishlistDao = wishlistDao;
        }

        @Override
        protected Void doInBackground(Wishlist_Item... wishlist_items) {
            wishlistDao.Wupdate(wishlist_items[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Wishlist_Item, Void, Void> {
        private WishlistDao wishlistDao;

        private DeleteNoteAsyncTask(WishlistDao wishlistDao) {
            this.wishlistDao = wishlistDao;
        }

        @Override
        protected Void doInBackground(Wishlist_Item... wishlist_items) {
            wishlistDao.Wdelete(wishlist_items[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private WishlistDao wishlistDao;

        private DeleteAllNotesAsyncTask(WishlistDao wishlistDao) {
            this.wishlistDao = wishlistDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wishlistDao.deleteWAllNotes();
            return null;
        }
    }
}