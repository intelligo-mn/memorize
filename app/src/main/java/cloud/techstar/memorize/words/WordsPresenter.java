package cloud.techstar.memorize.words;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordsPresenter implements WordsContract.Presenter, WordsDataSource.LoadWordsCallback {

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final WordsContract.View wordsView;

    private WordFilterType currentFilterType = WordFilterType.ACTIVE_WORDS;

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

                List<Words> mainWords = new ArrayList<Words>();

                Logger.e("Presenter words count : "+words.size());

                for (Words word : words) {

                    switch (getFilterType()) {
                        case ALL_WORDS:
                            mainWords.add(word);
                            break;
                        case ACTIVE_WORDS:
                            if (!word.isFavorite() && !word.isMemorize()) {
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

                if (currentFilterType == WordFilterType.RECENTLY){
                    Collections.sort(mainWords, new Comparator<Words>() {
                        @Override
                        public int compare(Words o1, Words o2) {
                            return o2.getCreated().compareTo(o1.getCreated());
                        }
                    });
                }
                Logger.e("Presenter words count : "+words.size());
                wordsView.showWords(mainWords);
                wordsView.setSuggest(words);
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
