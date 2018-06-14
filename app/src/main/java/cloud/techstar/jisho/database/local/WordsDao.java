package cloud.techstar.jisho.database.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cloud.techstar.jisho.database.Words;

@Dao
public interface WordsDao {

    /**
     * Select all Words from the words Words.
     *
     * @return all Words.
     */
    @Query("SELECT * FROM Words")
    List<Words> getWords();

    /**
     * Select a word by id.
     *
     * @param wordId the word id.
     * @return the word with wordId.
     */
    @Query("SELECT * FROM Words WHERE id = :wordId")
    Words getWordById(String wordId);

    /**
     * Insert a word in the database. If the word already exists, replace it.
     *
     * @param word the word to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Words word);

    /**
     * Update a word.
     *
     * @param word word to be updated
     * @return the number of words updated. This should always be 1.
     */
    @Update
    int updateWord(Words word);

    /**
     * Update the favorite status of a word
     *
     * @param wordId    id of the word
     * @param favorited status to be updated
     */
    @Query("UPDATE words SET favorited = :favorited WHERE id = :wordId")
    void updateFavorited(String wordId, boolean favorited);

    /**
     * Update the memorize status of a word
     *
     * @param wordId    id of the word
     * @param memorized status to be updated
     */
    @Query("UPDATE words SET memorized = :memorized WHERE id = :wordId")
    void updateMemorized(String wordId, boolean memorized);

    /**
     * Delete a word by id.
     *
     * @return the number of words deleted. This should always be 1.
     */
    @Query("DELETE FROM Words WHERE id = :wordId")
    int deleteWordById(String wordId);
    
}