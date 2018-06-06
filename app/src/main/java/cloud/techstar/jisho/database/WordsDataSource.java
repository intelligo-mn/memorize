package cloud.techstar.jisho.database;

import android.support.annotation.NonNull;

import java.util.List;

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

    void memorizeWord(@NonNull Words word);

    void memorizeWord(@NonNull String wordId);

    void favWord(@NonNull Words word);

    void favWord(@NonNull String wordId);

    void refreshWords();

    void deleteWord(@NonNull String wordId);
}
