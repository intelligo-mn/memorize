package cloud.techstar.memorize.favorite;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class FavoritePresenter implements FavoriteContract.Presenter, WordsDataSource.LoadWordsCallback {

    @NonNull
    private final WordsDataSource wordRepository;

    @NonNull
    private final FavoriteContract.View favoriteView;

    public FavoritePresenter(@NonNull WordsDataSource wordRepository, @NonNull FavoriteContract.View favoriteView) {
        this.wordRepository = wordRepository;
        this.favoriteView = favoriteView;

        favoriteView.setPresenter(this);
    }

    @Override
    public void init() {
        loadWords(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadWords(boolean forceUpdate) {
        loadWords(forceUpdate, true);
    }

    private void loadWords(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            favoriteView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            wordRepository.refreshWords();
        }

        wordRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                List<Words> favWords = new ArrayList<Words>();

                for (Words word : words) {
                    if (word.isFavorite() && !word.isMemorize())
                        favWords.add(word);
                }

                favoriteView.showWords(favWords);

                if (showLoadingUI) {
                    favoriteView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                favoriteView.showLoadingWordsError();
            }
        });

    }

    @Override
    public void openWordDetails(@NonNull Words requestedWord) {
        checkNotNull(requestedWord, "requestedWord cannot be null!");
        favoriteView.showWordDetail(requestedWord.getId());
    }

    @Override
    public void memorizeWord(String wordId) {
        wordRepository.memorizeWord(wordId);
    }

    @Override
    public void onWordsLoaded(List<Words> words) {

    }

    @Override
    public void onDataNotAvailable() {

    }
}
