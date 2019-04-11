package systems.intelligo.memorize.database;


import java.util.List;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.database.entity.Words;

public interface WordsDataSource {

    interface LoadWordsCallback {

        void onWordsLoaded(List<Words> words);

        void onDataNotAvailable();
    }

    interface GetWordCallback {

        void onWordLoaded(Words word);

        void onDataNotAvailable();
    }

    void getWords(@NonNull LoadWordsCallback callback);

    void getWord(@NonNull String wordId, @NonNull GetWordCallback callback);

    void saveWord(@NonNull Words word);

    void selectWord(String condition, String value, LoadWordsCallback callback);

    void updateWord(@NonNull Words word);

    void checkWord(@NonNull String wordChar, String kanji, @NonNull GetWordCallback callback);

    void memorizeWord(@NonNull Words word);

    void memorizeWord(@NonNull String wordId);

    void favWord(@NonNull Words word);

    void favWord(@NonNull String wordId);

    void refreshWords();

    void deleteWord(@NonNull String wordId);

    void deleteAllWords();
}
