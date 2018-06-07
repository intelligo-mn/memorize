package cloud.techstar.jisho.words;

import android.support.annotation.NonNull;

import java.util.List;

import cloud.techstar.jisho.BasePresenter;
import cloud.techstar.jisho.BaseView;
import cloud.techstar.jisho.database.Words;

public interface WordsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void showWordDetail(String wordId);

        void showLoadingWordsError();

        void showNoWords();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void openWordDetails(@NonNull Words requestedWord);

    }
}
