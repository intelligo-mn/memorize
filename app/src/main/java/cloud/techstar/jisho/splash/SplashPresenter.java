package cloud.techstar.jisho.splash;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.database.WordsDataSource;

public class SplashPresenter implements SplashContract.Presenter, WordsDataSource.GetWordCallback{

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final SplashContract.View splashView;

    private boolean isDataMissing;

    public SplashPresenter(@NonNull WordsDataSource wordRepository, @NonNull SplashContract.View splashView, boolean isDataMissing) {
        this.wordRepository = wordRepository;
        this.splashView = splashView;
        this.isDataMissing = isDataMissing;
        splashView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveWord(JSONArray memorize) {
        if (memorize.length() > 0) {
            wordRepository.deleteAllWords();

            for (int i = 0; i < memorize.length(); i++) {
                Words words = null;
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
