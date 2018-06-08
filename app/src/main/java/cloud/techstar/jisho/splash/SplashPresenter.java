package cloud.techstar.jisho.splash;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.MainActivity;
import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.database.WordsDataSource;
import cloud.techstar.jisho.utils.PrefManager;

public class SplashPresenter implements SplashContract.Presenter, WordsDataSource.GetWordCallback{

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final SplashContract.View splashView;

    private boolean isDataMissing;

    private PrefManager prefManager;

    public SplashPresenter(@NonNull WordsDataSource wordRepository, @NonNull SplashContract.View splashView, boolean isDataMissing) {
        this.wordRepository = wordRepository;
        this.splashView = splashView;
        this.isDataMissing = isDataMissing;
        splashView.setPresenter(this);
        prefManager = new PrefManager(AppMain.getContext());
    }

    @Override
    public void init() {
        if (prefManager.isFirstTimeLaunch()){
            splashView.getWordsRemote();
            prefManager.setIsFirstTimeLaunch(false);
        } else {
            splashView.showWordList();
        }
    }

    @Override
    public void saveWord(JSONArray memorize) {
        if (memorize.length() > 0) {
            wordRepository.deleteAllWords();
            int numWords = 0;
            for (int i = 0; i < memorize.length(); i++) {
                Words words = null;
                numWords += 1;
                try {
                    words = new Words(
                            memorize.getJSONObject(i).getString("_id"),
                            memorize.getJSONObject(i).getString("character"),
                            memorize.getJSONObject(i).getString("meanings"),
                            memorize.getJSONObject(i).getString("meaningsMongolia"),
                            memorize.getJSONObject(i).getString("kanji"),
                            memorize.getJSONObject(i).getString("partOfSpeech"),
                            memorize.getJSONObject(i).getString("level"),
                            memorize.getJSONObject(i).getBoolean("isMemorize"),
                            memorize.getJSONObject(i).getBoolean("isFavorite"),
                            memorize.getJSONObject(i).getString("created"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert words != null;
                wordRepository.saveWord(words);
            }

            Logger.d("Get remote total words: "+numWords);
        } else {
            splashView.showEmptyWordError();
        }
        splashView.showWordList();
    }

    @Override
    public void populateWords() {

    }

    @Override
    public boolean isDataMissing() {
        return isDataMissing;
    }

    @Override
    public void onWordLoaded(Words word) {

    }

    @Override
    public void onDataNotAvailable() {

    }
}
