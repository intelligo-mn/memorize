package cloud.techstar.jisho.favorite;

import android.support.annotation.NonNull;

import java.util.List;

import cloud.techstar.jisho.BasePresenter;
import cloud.techstar.jisho.BaseView;
import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.words.WordsContract;

public interface FavoriteContract {
    interface View extends BaseView<FavoriteContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void showWordDetail(String wordId);

        void showLoadingWordsError();

        void showNoWords();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void openWordDetails(@NonNull Words requestedWord);

    }
}
