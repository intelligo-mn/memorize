package cloud.techstar.memorize.detail;

import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract.Presenter{

    private final WordsRepository wordsRepository;

    private final DetailContract.View detailView;

    private Words mWord;

    @Nullable
    private String wordId;

    public DetailPresenter(String wordId, WordsRepository wordsRepository, DetailContract.View detailView) {
        this.wordsRepository = wordsRepository;
        this.detailView = detailView;
        this.wordId = wordId;
        detailView.setPresenter(this);
    }

    @Override
    public void init() {
        openWord();
    }

    private void openWord() {
        if (Strings.isNullOrEmpty(wordId)) {
            detailView.showMissingWord();
            return;
        }

        detailView.setLoadingIndicator(true);
        wordsRepository.getWord(wordId, new WordsDataSource.GetWordCallback(){

            @Override
            public void onWordLoaded(Words word) {
                if (null == word) {
                    detailView.showMissingWord();
                } else {
                    mWord = word;
                    detailView.setData(word);
                }

                detailView.setLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                detailView.showMissingWord();
            }
        });
    }

    @Override
    public void favoriteWord() {
        checkNotNull(mWord, "favoriteWord cannot be null!");
        wordsRepository.favWord(mWord.getId());

        if (!mWord.isFavorite()) {
            detailView.showFavorite(true);
        } else {
            detailView.showFavorite(false);
        }
    }

    @Override
    public void memorizeWord() {

    }
}
