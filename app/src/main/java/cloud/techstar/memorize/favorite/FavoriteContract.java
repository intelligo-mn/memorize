package cloud.techstar.memorize.favorite;

import java.util.List;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

public interface FavoriteContract {
    interface View extends BaseView<FavoriteContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void showWordDetail(String wordId);

        void showLoadingWordsError();

        void showNoWords();

        void showMemorize(boolean isFav);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void openWordDetails(@NonNull Words requestedWord);

        void memorizeWord(String wordId);
    }
}
