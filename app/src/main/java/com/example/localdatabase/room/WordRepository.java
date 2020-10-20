package com.example.localdatabase.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.localdatabase.models.WordModel;

import java.util.List;
import java.util.concurrent.Executors;

public class WordRepository {

    private final WordDao mWordDao;

    private static final int NO_OF_THREADS = 4;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
    }

    public LiveData<List<WordModel>> getAllWords() {
        return mWordDao.getAlphabetizedWords();
    }

    public void insert(WordModel wordModel) {
        Executors.newFixedThreadPool(NO_OF_THREADS).execute(() -> mWordDao.insert(wordModel));
    }

    public void update(WordModel wordModel) {
        Executors.newFixedThreadPool(NO_OF_THREADS).execute(() -> mWordDao.update(wordModel));
    }

    public void delete(WordModel wordModel) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> mWordDao.delete(wordModel));
    }
}
