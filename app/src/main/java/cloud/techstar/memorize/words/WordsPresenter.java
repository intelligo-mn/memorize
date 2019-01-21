package cloud.techstar.memorize.words;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.database.entity.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.utils.ConnectionDetector;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cloud.techstar.memorize.utils.MemorizeUtils.getNowTime;
import static com.google.common.base.Preconditions.checkNotNull;

public class WordsPresenter implements WordsContract.Presenter, WordsDataSource.LoadWordsCallback {

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final WordsContract.View wordsView;

    private WordFilterType currentFilterType = WordFilterType.ACTIVE_WORDS;

    private Handler jishoHandler;
    private OkHttpClient jishoClient;
    private List<Words> searchWords;

    public WordsPresenter(@NonNull WordsDataSource wordRepository, @NonNull WordsContract.View wordsView) {
        this.wordRepository = wordRepository;
        this.wordsView = wordsView;
        jishoHandler = new Handler(Looper.getMainLooper());
        jishoClient = new OkHttpClient();
        searchWords = new ArrayList<>();
        wordsView.setPresenter(this);
    }

    @Override
    public void init() {
        loadWords(false);
    }

    @Override
    public void onWordsLoaded(List<Words> words) {

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadWords(boolean forceUpdate) {
        loadWords(forceUpdate, true);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link WordsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadWords(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            wordsView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            wordRepository.refreshWords();
        }

        wordRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {

                searchWords = words;

                List<Words> mainWords = new ArrayList<Words>();

                for (Words word : words) {

                    switch (getFilterType()) {
                        case ALL_WORDS:
                            mainWords.add(word);
                            break;
                        case ACTIVE_WORDS:
                            if (!word.isFavorite() && !word.isMemorize()) {
                                mainWords.add(word);
                            }
                        case NOT_TRANSLATE:
                            if (word.getMeaningMon().size() < 1) {
                                mainWords.add(word);
                            }
                            break;
                        default:
                            if (!word.isFavorite() && !word.isMemorize()) {
                                mainWords.add(word);
                            }
                            break;
                    }
                }

                if (currentFilterType == WordFilterType.RECENTLY) {
                    Collections.sort(mainWords, new Comparator<Words>() {
                        @Override
                        public int compare(Words o1, Words o2) {
                            return o2.getCreated().compareTo(o1.getCreated());
                        }
                    });
                }

                wordsView.showWords(mainWords);

                if (showLoadingUI) {
                    wordsView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                wordsView.setLoadingIndicator(false);
                wordsView.showLoadingWordsError();
            }
        });

    }

    @Override
    public void openWordDetails(@NonNull Words requestedWord) {
        checkNotNull(requestedWord, "requestedWord cannot be null!");
        saveWord(requestedWord);
        wordsView.showWordDetail(requestedWord);
    }

    @Override
    public void saveWord(Words word) {
        wordRepository.saveWord(word);
    }

    @Override
    public void search(final String keyWord) {

        wordsView.setLoadingIndicator(true);

        List<Words> result = new ArrayList<>();
        for (Words word : searchWords) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (word.getCharacter().contains(keyWord) || word.getMeaning().stream().anyMatch(str -> str.trim().equals(keyWord)) || word.getMeaningMon().contains(keyWord))
                    result.add(word);
            } else {
                if (word.getCharacter().contains(keyWord))
                    result.add(word);
            }
        }

        if (!ConnectionDetector.isNetworkAvailable(AppMain.getContext())) {

            wordsView.showWords(result);

            wordsView.setLoadingIndicator(false);
        } else {
            searchRemote(keyWord, result);
        }
    }

    @Override
    public void searchRemote(String keyWord, List<Words> local) {

        final List<Words> apiWords = new ArrayList<>(local);

        final Request jishoRequest = new Request.Builder()
                .url("https://jisho.org/api/v1/search/words?keyword=" + keyWord)
                .build();

        jishoClient.newCall(jishoRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                wordsView.showWords(apiWords);
                wordsView.setLoadingIndicator(false);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String res = response.body().string();
                jishoHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject ob = new JSONObject(res);
                            Logger.d(ob);

                            JSONArray datas = ob.getJSONArray("data");

                            for (int i = 0; i < datas.length(); i++) {

                                JSONObject data = datas.getJSONObject(i);

                                JSONArray tag = data.getJSONArray("tags");
                                JSONArray japanese = data.getJSONArray("japanese");

                                final List<String> tagList = new ArrayList<>();

                                for (int t = 0; t < tag.length(); t++) {
                                    if (!tag.getString(t).equals(""))
                                        tagList.add(tag.getString(t));
                                }

                                String kanji = "";
                                String character = "";
                                if (!japanese.getJSONObject(0).isNull("word")) {
                                    kanji = japanese.getJSONObject(0).getString("word");
                                }

                                if (!japanese.getJSONObject(0).isNull("reading")) {
                                    character = japanese.getJSONObject(0).getString("reading");
                                }

                                JSONArray senses = data.getJSONArray("senses");

                                List<String> meaningList = new ArrayList<>();
                                List<String> partOfSpeechList = new ArrayList<>();

                                for (int s = 0; s < senses.length(); s++) {
                                    JSONObject sObject = senses.getJSONObject(s);
                                    JSONArray english = sObject.getJSONArray("english_definitions");
                                    JSONArray partOfSpeech = sObject.getJSONArray("parts_of_speech");

                                    StringBuilder meaning = new StringBuilder();
                                    for (int e = 0; e < english.length(); e++) {
                                        meaning.append(english.getString(e)).append(", ");
                                    }
                                    meaning.deleteCharAt(meaning.length() - 2);
                                    meaningList.add(meaning.toString());

                                    if (partOfSpeech.length() > 0) {
                                        StringBuilder part = new StringBuilder();
                                        for (int p = 0; p < partOfSpeech.length(); p++) {
                                            if (!partOfSpeech.getString(p).equals(""))
                                                part.append(partOfSpeech.getString(p)).append(" ");
                                        }
                                        partOfSpeechList.add(part.toString());
                                    }
                                }

                                Words word = new Words(UUID.randomUUID().toString(), character, meaningList, new ArrayList<String>(), kanji, partOfSpeechList, "jlpt2", tagList, getNowTime());
                                if (kanji.equals("")) {
                                    word.setKanji(word.getCharacter());
                                }
                                apiWords.add(word);
                            }

                            if (apiWords.size() > 0) {
                                wordsView.showWords(apiWords);
                                wordsView.setLoadingIndicator(false);
                            } else {
                                wordsView.setLoadingIndicator(false);
                                wordsView.showToast("No result !!!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            wordsView.setLoadingIndicator(false);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setFilterType(WordFilterType filterType) {
        currentFilterType = filterType;
    }

    @Override
    public WordFilterType getFilterType() {
        return currentFilterType;
    }

    @Override
    public void setViewType(WordViewType viewType) {

    }

    @Override
    public WordViewType getViewType() {
        return null;
    }
}
