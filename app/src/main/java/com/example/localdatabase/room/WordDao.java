package com.example.localdatabase.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.localdatabase.models.WordModel;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordModel wordModel);

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<WordModel>> getAlphabetizedWords();

    @Delete
    void delete(WordModel model);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Update
    void update(WordModel model);
}
