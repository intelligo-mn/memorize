package cloud.techstar.memorize.manage;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.utils.ConnectionDetector;
import cloud.techstar.memorize.utils.MemorizeConstant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class ManagePresenter implements ManageContract.Presenter{

    private Handler handler;

    private WordsRepository wordsRepository;

    private ManageContract.View manageView;

    public ManagePresenter(WordsRepository wordsRepository, ManageContract.View manageView){
        this.wordsRepository = wordsRepository;
        this.manageView = manageView;

        handler = new Handler();

        manageView.setPresenter(this);
    }

    public ManagePresenter(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void saveWord(Words words) {
        wordsRepository.saveWord(words);
        if (ConnectionDetector.isNetworkAvailable(AppMain.getContext()))
            sendServer();
        wordsRepository.sendServer(words);
        manageView.clearFields();
    }

    @Override
    public void sendServer() {

    }

    public JSONArray getNewLocalData(){

        final JSONArray newWordsArray = new JSONArray();

        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                for (Words word : words){
                    if (word.getIsLocal() == 1){
                        JSONObject newWords = new JSONObject();
                        try {
                            newWords.put("character", word.getCharacter());
                            newWords.put("meanings", word.getMeaning());
                            newWords.put("meaningsMongolia", checkNotNull(word.getMeaningMon()));
                            newWords.put("partOfSpeech", checkNotNull(word.getPartOfSpeech()));
                            newWords.put("kanji", checkNotNull(word.getKanji()));
                            newWords.put("level", checkNotNull(word.getLevel()));
                            newWordsArray.put(newWords);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return newWordsArray;
    }

    public JSONArray getUpdatedLocalData(){

        final JSONArray newWordsArray = new JSONArray();

        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                for (Words word : words){
                    if (word.getIsLocal() == 2){
                        JSONObject newWords = new JSONObject();
                        try {
                            newWords.put("id", word.getId());
                            newWords.put("character", word.getCharacter());
                            newWords.put("meanings", word.getMeaning());
                            newWords.put("meaningsMongolia", checkNotNull(word.getMeaningMon()));
                            newWords.put("partOfSpeech", checkNotNull(word.getPartOfSpeech()));
                            newWords.put("kanji", checkNotNull(word.getKanji()));
                            newWords.put("level", checkNotNull(word.getLevel()));
                            newWords.put("isFavorite", word.isFavorite());
                            newWords.put("isMemorize", word.isMemorize());
                            newWordsArray.put(newWords);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return newWordsArray;
    }

    @Override
    public void init() {

    }
}
