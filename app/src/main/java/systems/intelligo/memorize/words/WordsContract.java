package systems.intelligo.memorize.words;

import java.util.List;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.BasePresenter;
import systems.intelligo.memorize.BaseView;
import systems.intelligo.memorize.database.entity.Words;

public interface WordsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void showWordDetail(Words word);

        void showLoadingWordsError();

        void showNoWords();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadWords(boolean forceUpdate);

        void openWordDetails(@NonNull Words requestedWord);

        void setFilterType(WordFilterType filterType);

        WordFilterType getFilterType();

        void saveWord(Words word);

        void search(String keyWord);

        void searchRemote(String keyWord, List<Words> localResult);

        void setViewType(WordViewType viewType);

        WordViewType getViewType();
    }
}