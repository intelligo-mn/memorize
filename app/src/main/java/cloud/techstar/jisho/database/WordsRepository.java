package cloud.techstar.jisho.database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WordsRepository implements WordsDataSource {

    private static  WordsRepository INSTANCE = null;

    private final WordsDataSource wordsRemoteDataSource;

    private final WordsDataSource wordsLocalDataSource;

    Map<String, Words> cachedWords;

    boolean mCacheIsDirty = false;

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

    private void getWordsFromRemoteDataSource(LoadWordsCallback callback) {
    }

    @Override
    public void getWord(@NonNull String wordId, @NonNull GetWordCallback callback) {

    }

    @Override
    public void saveWord(@NonNull Words word) {
        wordsRemoteDataSource.saveWord(word);
        wordsLocalDataSource.saveWord(word);

        if (cachedWords == null)
            cachedWords = new LinkedHashMap<>();
        cachedWords.put(word.getId(), word);
    }

    @Override
    public void memorizeWord(@NonNull Words word) {

    }

    @Override
    public void memorizeWord(@NonNull String wordId) {

    }

    @Override
    public void favWord(@NonNull Words word) {

    }

    @Override
    public void favWord(@NonNull String wordId) {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteWord(@NonNull String wordId) {

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
}
