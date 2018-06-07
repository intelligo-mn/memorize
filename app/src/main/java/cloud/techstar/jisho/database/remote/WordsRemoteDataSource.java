package cloud.techstar.jisho.database.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.database.WordsDataSource;
import com.google.common.collect.Lists;

public class WordsRemoteDataSource implements WordsDataSource {

    private static WordsRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Words> WORDS_SERVICE_DATA;

    static {
        WORDS_SERVICE_DATA = new LinkedHashMap<>(2);
        addWord( "まいあさ", "every morning", "өглөө бүр", "nouns", "毎朝", "jlpt5", false, false, "2018-06-07T01:13:07.033Z");
        addWord("は", "teeth", "шүд", "nouns", "歯", "jlpt5",  false, false, "2018-06-07T01:14:11.193Z");
    }

    public static WordsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WordsRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private WordsRemoteDataSource() {}

    private static void addWord(String character, String meaning, String meaningMon, String kanji, String partOfSpeech, String level, boolean isMemorize, boolean isFavorite, String created) {
        Words newWord = new Words(character, meaning, meaningMon, kanji, partOfSpeech, level, isMemorize, isFavorite, created);
        WORDS_SERVICE_DATA.put(newWord.getId(), newWord);
    }

    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onWordsLoaded(Lists.newArrayList(WORDS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getWord(@NonNull final String wordId, @NonNull final GetWordCallback callback) {
        final Words words = WORDS_SERVICE_DATA.get(wordId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onWordLoaded(words);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveWord(@NonNull Words word) {
        WORDS_SERVICE_DATA.put(word.getId(), word);
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
