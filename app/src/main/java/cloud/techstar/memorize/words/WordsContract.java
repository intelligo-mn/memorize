package cloud.techstar.memorize.words;

import android.support.annotation.NonNull;

import java.util.List;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

public interface WordsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void showWordDetail(String wordId);

        void showLoadingWordsError();

        void showNoWords();

        void setSuggest(List<Words> words);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void openWordDetails(@NonNull Words requestedWord);

    }
}
