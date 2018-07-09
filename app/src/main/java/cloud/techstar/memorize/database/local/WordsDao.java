package cloud.techstar.memorize.database.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cloud.techstar.memorize.database.Words;

@Dao
public interface WordsDao {

    @Query("SELECT * FROM words")
    List<Words> getWords();

    @Query("SELECT * FROM words WHERE id = :wordId")
    Words getWordById(String wordId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Words word);

    @Update
    int updateWord(Words word);

    @Query("UPDATE words SET favorited = :favorited, local = :isLocal WHERE id = :wordId")
    void updateFavorited(String wordId, boolean favorited, int isLocal);

    @Query("UPDATE words SET memorized = :memorized, local = :isLocal WHERE id = :wordId")
    void updateMemorized(String wordId, boolean memorized, int isLocal);

    @Query("DELETE FROM words")
    void deleteWords();

    @Query("DELETE FROM Words WHERE id = :wordId")
    int deleteWordById(String wordId);
    
}