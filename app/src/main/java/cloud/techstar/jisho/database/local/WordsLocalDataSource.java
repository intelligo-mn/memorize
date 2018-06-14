package cloud.techstar.jisho.database.local;

import android.support.annotation.NonNull;

import java.util.List;

import cloud.techstar.jisho.database.Word;
import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.database.WordsDataSource;
import cloud.techstar.jisho.utils.AppExecutors;

public class WordsLocalDataSource implements WordsDataSource {

    private static volatile WordsLocalDataSource INSTANCE;

    private WordsDao wordsDao;

    private AppExecutors appExecutors;

    private WordsLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull WordsDao wordsDao) {
        this.appExecutors = appExecutors;
        this.wordsDao = wordsDao;
    }

    public static WordsLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                   @NonNull WordsDao wordsDao) {
        if (INSTANCE == null) {
            synchronized (WordsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WordsLocalDataSource(appExecutors, wordsDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Words> words = wordsDao.getWords();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (words.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onWordsLoaded(words);
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getWord(@NonNull final String wordId, @NonNull final GetWordCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Words word = wordsDao.getWordById(wordId);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (word != null) {
                            callback.onWordLoaded(word);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveWord(@NonNull final Words word) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.insertWord(word);
            }
        };
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void memorizeWord(@NonNull Words word) {

    }

    @Override
    public void memorizeWord(@NonNull String wordId) {

    }

    @Override
    public void favWord(@NonNull final Words word) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.updateFavorited(word.getId(), true);
            }
        };

        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void favWord(@NonNull String wordId) {

    }

    @Override
    public void unFavWord(@NonNull Words word) {

    }

    @Override
    public void clearFavWords() {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteWord(@NonNull String wordId) {

    }

    @Override
    public void deleteAllWords() {

    }
}
