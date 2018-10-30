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
    private Words word;

    public DetailPresenter(Words word, WordsRepository wordsRepository, DetailContract.View detailView) {
        this.wordsRepository = wordsRepository;
        this.detailView = detailView;
        this.word = word;
        detailView.setPresenter(this);
    }

    @Override
    public void init() {
        openWord();
    }

    private void openWord() {
        if (word==null) {
            detailView.showMissingWord();
            return;
        } else {

            detailView.setData(word);
        }
    }

    @Override
    public void favoriteWord() {
        checkNotNull(mWord, "favoriteWord cannot be null!");
        wordsRepository.favWord(mWord.getId());

        if (!mWord.isFavorite()) {
            detailView.showFavorite(true);
        }
    }

    @Override
    public void memorizeWord() {

    }
}
