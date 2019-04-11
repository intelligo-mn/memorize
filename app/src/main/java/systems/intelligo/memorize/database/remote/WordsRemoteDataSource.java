package systems.intelligo.memorize.database.remote;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.AppMain;
import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.database.WordsDataSource;
import systems.intelligo.memorize.utils.MemorizeConstant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WordsRemoteDataSource implements WordsDataSource {

    private static WordsRemoteDataSource INSTANCE;

    private final static Map<String, Words> WORDS_SERVICE_DATA;

    static {
        WORDS_SERVICE_DATA = new LinkedHashMap<>();
    }

    public static WordsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WordsRemoteDataSource();
        }
        return INSTANCE;
    }


    // Prevent direct instantiation.
    private WordsRemoteDataSource() {
    }

    @Override
    public void getWords(@NonNull final LoadWordsCallback callback) {

        final Handler mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();

        for (int i = 0; i <= 55; i++) {
            Request request = new Request.Builder()
                    .url(MemorizeConstant.GET_WORDS.concat("?page=" + i))
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Logger.e("Server connection failed : " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    assert response.body() != null;
                    final String res = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                JSONObject ob = new JSONObject(res);
                                JSONArray memorize = ob.getJSONArray("memorize");
                                if (memorize.length() > 0) {
                                    int numWords = 0;
                                    for (int i = 0; i < memorize.length(); i++) {
                                        numWords += 1;
                                        List<String> meaning = new ArrayList<>();
                                        List<String> meaningMon = new ArrayList<>();
                                        List<String> partOfSpeech = new ArrayList<>();
                                        List<String> tag = new ArrayList<>();

                                        JSONArray meaningJSON = memorize.getJSONObject(i).getJSONArray("meanings");
                                        JSONArray meaningMnJSON = memorize.getJSONObject(i).getJSONArray("meaningsMongolia");
                                        JSONArray partJSON = memorize.getJSONObject(i).getJSONArray("partOfSpeech");
                                        JSONArray tagJSON = memorize.getJSONObject(i).getJSONArray("tag");
                                        String level = memorize.getJSONObject(i).getString("level");

                                        for (int e = 0; e < meaningJSON.length(); e++) {
                                            meaning.add(meaningJSON.getString(e));
                                        }
                                        for (int e = 0; e < meaningMnJSON.length(); e++) {
                                            meaningMon.add(meaningMnJSON.getString(e));
                                        }
                                        for (int e = 0; e < partJSON.length(); e++) {
                                            partOfSpeech.add(partJSON.getString(e));
                                        }
                                        for (int e = 0; e < tagJSON.length(); e++) {
                                            tag.add(tagJSON.getString(e));
                                        }

                                        Words words = new Words(
                                                memorize.getJSONObject(i).getString("_id"),
                                                memorize.getJSONObject(i).getString("character"),
                                                meaning,
                                                meaningMon,
                                                memorize.getJSONObject(i).getString("kanji"),
                                                partOfSpeech,
                                                level,
                                                tag,
                                                memorize.getJSONObject(i).getBoolean("isMemorize"),
                                                memorize.getJSONObject(i).getBoolean("isFavorite"),
                                                memorize.getJSONObject(i).getString("created"),
                                                false);

                                        WORDS_SERVICE_DATA.put(words.getId(), words);
                                    }
                                } else {
                                    Toast.makeText(AppMain.getContext(), "Data not available !!!", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

        callback.onWordsLoaded(Lists.newArrayList(WORDS_SERVICE_DATA.values()));
    }

    @Override
    public void getWord(@NonNull final String wordId, @NonNull final GetWordCallback callback) {
        final Words words = WORDS_SERVICE_DATA.get(wordId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onWordLoaded(words);
            }
        });
    }

    @Override
    public void saveWord(@NonNull Words word) {
        WORDS_SERVICE_DATA.put(word.getId(), word);
    }

    @Override
    public void selectWord(String condition, String value, LoadWordsCallback callback) {

    }

    @Override
    public void updateWord(@NonNull Words word) {
        WORDS_SERVICE_DATA.put(word.getId(), word);
    }

    @Override
    public void checkWord(@NonNull String wordChar, String kanji, @NonNull GetWordCallback callback) {

    }

    @Override
    public void memorizeWord(@NonNull Words word) {
        Words memorizedWord = new Words(word.getId(), word.getCharacter(), word.getMeaning(), word.getMeaningMon(), word.getKanji(), word.getPartOfSpeech(), word.getLevel(), word.getTag(),
                true, word.isFavorite(), word.getCreated(), true);
        WORDS_SERVICE_DATA.put(word.getId(), memorizedWord);
    }

    @Override
    public void memorizeWord(@NonNull String wordId) {

    }

    @Override
    public void favWord(@NonNull Words word) {
        Words favoritedWord = new Words(word.getId(), word.getCharacter(), word.getMeaning(), word.getMeaningMon(), word.getKanji(), word.getPartOfSpeech(), word.getLevel(), word.getTag(),
                word.isMemorize(), true, word.getCreated(), true);
        WORDS_SERVICE_DATA.put(word.getId(), favoritedWord);
    }

    @Override
    public void favWord(@NonNull String wordId) {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteWord(@NonNull String wordId) {
        WORDS_SERVICE_DATA.remove(wordId);
    }

    @Override
    public void deleteAllWords() {
        WORDS_SERVICE_DATA.clear();
    }
}
