package cloud.techstar.memorize.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.database.entity.Words;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordsRepository implements WordsDataSource {

    private static WordsRepository INSTANCE = null;

    private final WordsDataSource wordsRemoteDataSource;

    private final WordsDataSource wordsLocalDataSource;

    private Map<String, Words> cachedWords;

    private boolean mCacheIsDirty = false;

    public WordsRepository(WordsDataSource wordsRemoteDataSource, WordsDataSource wordsLocalDataSource) {
        this.wordsRemoteDataSource = wordsRemoteDataSource;
        this.wordsLocalDataSource = wordsLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param wordsRemoteDataSource the backend data source
     * @param wordsLocalDataSource  the device storage data source
     * @return the {@link WordsRepository} instance
     */
    public static WordsRepository getInstance(WordsDataSource wordsRemoteDataSource,
                                              WordsDataSource wordsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WordsRepository(wordsRemoteDataSource, wordsLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(WordsDataSource, WordsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        checkNotNull(callback);
        // Respond immediately with cache if available and not dirty
        if (cachedWords != null && !mCacheIsDirty) {
            callback.onWordsLoaded(new ArrayList<>(cachedWords.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getWordsFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            wordsLocalDataSource.getWords(new LoadWordsCallback() {
                @Override
                public void onWordsLoaded(List<Words> words) {
                    refreshCache(words);
                    callback.onWordsLoaded(new ArrayList<>(cachedWords.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getWordsFromRemoteDataSource(callback);
                }
            });
        }
    }

    public void getWordsFromRemoteDataSource(final LoadWordsCallback callback) {
        checkNotNull(callback);
        wordsRemoteDataSource.getWords(new LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                refreshCache(words);
                refreshLocalDataSource(words);
                callback.onWordsLoaded(new ArrayList<Words>(cachedWords.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getWord(@NonNull final String wordId, @NonNull final GetWordCallback callback) {
        checkNotNull(callback);
        final Words cachedWord = getWordsWithId(wordId);

        if (cachedWord != null) {
            callback.onWordLoaded(cachedWord);
            return;
        }

        wordsLocalDataSource.getWord(wordId, new GetWordCallback() {

            @Override
            public void onWordLoaded(Words word) {
                if (cachedWords == null) {
                    cachedWords = new LinkedHashMap<>();
                }
                cachedWords.put(word.getId(), word);
                callback.onWordLoaded(word);
            }

            @Override
            public void onDataNotAvailable() {
                wordsRemoteDataSource.getWord(wordId, new GetWordCallback() {
                    @Override
                    public void onWordLoaded(Words word) {
                        // Do in memory cache update to keep the app UI up to date
                        if (cachedWords == null) {
                            cachedWords = new LinkedHashMap<>();
                        }
                        cachedWords.put(word.getId(), word);
                        callback.onWordLoaded(word);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveWord(@NonNull final Words word) {

        checkWord(word.getCharacter(), word.getKanji(), new GetWordCallback() {
            @Override
            public void onWordLoaded(Words checkedWord) {
                wordsRemoteDataSource.updateWord(word);
                wordsLocalDataSource.updateWord(word);

                if (cachedWords == null)
                    cachedWords = new LinkedHashMap<>();
                cachedWords.put(word.getId(), word);
            }

            @Override
            public void onDataNotAvailable() {

                wordsRemoteDataSource.saveWord(word);
                wordsLocalDataSource.saveWord(word);

                if (cachedWords == null)
                    cachedWords = new LinkedHashMap<>();
                cachedWords.put(word.getId(), word);
            }
        });
    }

    @Override
    public void selectWord(final String condition, final String value, final LoadWordsCallback callback) {
        // Query the local storage if available. If not, query the network.
        wordsLocalDataSource.selectWord(condition, value, new LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                refreshCache(words);
                callback.onWordsLoaded(new ArrayList<>(cachedWords.values()));
            }

            @Override
            public void onDataNotAvailable() {
                wordsRemoteDataSource.selectWord(condition, value, callback);
            }
        });
    }

    @Override
    public void updateWord(@NonNull Words word) {
        wordsRemoteDataSource.updateWord(word);
        wordsLocalDataSource.updateWord(word);

        if (cachedWords == null)
            cachedWords = new LinkedHashMap<>();
        cachedWords.put(word.getId(), word);
    }

    @Override
    public void checkWord(@NonNull String wordChar, String kanji, @NonNull final GetWordCallback callback) {
        wordsLocalDataSource.checkWord(wordChar, kanji, new GetWordCallback() {
            @Override
            public void onWordLoaded(Words word) {
                callback.onWordLoaded(word);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void memorizeWord(@NonNull Words word) {
        checkNotNull(word);
        wordsRemoteDataSource.memorizeWord(word);
        wordsLocalDataSource.memorizeWord(word);

        Words memorizedWord = new Words(
                word.getId(),
                word.getCharacter(),
                word.getMeaning(),
                word.getMeaningMon(),
                word.getKanji(),
                word.getPartOfSpeech(),
                word.getLevel(),
                word.getTag(),
                true,
                word.isFavorite(),
                word.getCreated(),
                true);
        if (cachedWords == null) {
            cachedWords = new LinkedHashMap<>();
        }

        cachedWords.put(word.getId(), memorizedWord);
    }

    @Override
    public void memorizeWord(@NonNull String wordId) {
        checkNotNull(wordId);
        memorizeWord(getWordsWithId(wordId));
    }

    @Override
    public void favWord(@NonNull Words word) {
        checkNotNull(word);
        wordsRemoteDataSource.favWord(word);
        wordsLocalDataSource.favWord(word);

        Words favoritedWord = new Words(
                word.getId(),
                word.getCharacter(),
                word.getMeaning(),
                word.getMeaningMon(),
                word.getKanji(),
                word.getPartOfSpeech(),
                word.getLevel(),
                word.getTag(),
                word.isMemorize(),
                true,
                word.getCreated(),
                true);

        // Do in memory cache update to keep the app UI up to date
        if (cachedWords == null) {
            cachedWords = new LinkedHashMap<>();
        }
        cachedWords.put(word.getId(), favoritedWord);
    }

    @Override
    public void favWord(@NonNull String wordId) {
        checkNotNull(wordId);
        favWord(getWordsWithId(wordId));
    }

    @Override
    public void refreshWords() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteWord(@NonNull String wordId) {
        wordsRemoteDataSource.deleteWord(wordId);
        wordsLocalDataSource.deleteWord(wordId);

        cachedWords.remove(wordId);
    }

    @Override
    public void deleteAllWords() {
        wordsRemoteDataSource.deleteAllWords();
        wordsLocalDataSource.deleteAllWords();

        if (cachedWords == null) {
            cachedWords = new LinkedHashMap<>();
        }
        cachedWords.clear();
    }

    private void refreshCache(List<Words> words) {
        if (cachedWords == null) {
            cachedWords = new LinkedHashMap<>();
        }
        cachedWords.clear();
        for (Words word : words) {
            cachedWords.put(word.getId(), word);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Words> words) {
        checkNotNull(words);
        wordsLocalDataSource.deleteAllWords();

        for (Words word : words) {
            wordsLocalDataSource.saveWord(word);
        }
    }

    private Words getWordsWithId(@NonNull String id) {
        checkNotNull(id);
        if (cachedWords == null || cachedWords.isEmpty()) {
            return null;
        } else {
            return cachedWords.get(id);
        }
    }
}
