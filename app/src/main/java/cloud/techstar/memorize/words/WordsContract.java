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

    void searchRemote(String keyWord);

    void setViewType(WordViewType viewType);

    WordViewType getViewType();
  }
}