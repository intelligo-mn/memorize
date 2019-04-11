package systems.intelligo.memorize.database.local;


import java.util.List;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.database.WordsDataSource;
import systems.intelligo.memorize.utils.AppExecutors;

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
    public void selectWord(final String condition, final String value, final LoadWordsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Words> words = wordsDao.selectWord(condition, value);
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
    public void updateWord(@NonNull final Words word) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.updateWord(word);
            }
        };
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void checkWord(@NonNull final String wordChar, final String kanji, @NonNull final GetWordCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Words word = wordsDao.checkCharKanji(wordChar, kanji);

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
    public void memorizeWord(@NonNull final Words word) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.updateMemorized(word.getId(), true, true);
            }
        };

        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void memorizeWord(@NonNull String wordId) {
        // Not required because the {@link WordsRepository}
    }

    @Override
    public void favWord(@NonNull final Words word) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.updateFavorited(word.getId(), true, true);
            }
        };

        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void favWord(@NonNull String wordId) {
        // Not required because the {@link WordsRepository}
    }

    @Override
    public void refreshWords() {
        // Not required because the {@link WordsRepository}
    }

    @Override
    public void deleteWord(@NonNull final String wordId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                wordsDao.deleteWordById(wordId);
            }
        };

        appExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteAllWords() {
        Runnable deleteAllRunnabe = new Runnable() {
            @Override
            public void run() {
                wordsDao.deleteWords();
            }
        };
        appExecutors.diskIO().execute(deleteAllRunnabe);
    }
}
