package net.thesis.vglibvol;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;


public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;

    private LiveData<List<Note>> allLNotes;
    private List<Note> allNotes;

    private List<String> titles;

    private List<String> titles_guid;

    private List<String> genres;

    //public List<Note>allNames;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);

        allNotes = repository.getAllNotes();
        titles = repository.getTitles();

        allLNotes = repository.getAllLNotes();

        titles_guid = repository.getTitles_guid();
        //allNames = repository.getAllNames();
        genres = repository.getGenres();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }





    public List<Note> getAllNotes() {
        return allNotes;

    }

    public LiveData<List<Note>> getLAllNotes() {
        return allLNotes;

    }

    public List<String> getTitles() {
        return  titles;
    }

    public List<String> getTitles_guid() {
        return  titles_guid;
    }

    public List<String> getGenres() {return genres;}



}