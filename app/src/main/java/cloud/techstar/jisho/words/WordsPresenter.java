package cloud.techstar.jisho.words;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.database.WordsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordsPresenter implements WordsContract.Presenter, WordsDataSource.LoadWordsCallback {

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final WordsContract.View wordsView;

    public WordsPresenter(@NonNull WordsDataSource wordRepository, @NonNull WordsContract.View wordsView) {
        this.wordRepository = wordRepository;
        this.wordsView = wordsView;

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
                Logger.e("Presenter words count : "+words.size());
                wordsView.showWords(words);

                if (showLoadingUI) {
                    wordsView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                wordsView.showLoadingWordsError();
            }
        });

    }

    @Override
    public void openWordDetails(@NonNull Words requestedWord) {
        checkNotNull(requestedWord, "requestedWord cannot be null!");
        wordsView.showWordDetail(requestedWord.getId());
    }
}
