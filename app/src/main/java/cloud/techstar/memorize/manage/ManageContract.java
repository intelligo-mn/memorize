package cloud.techstar.memorize.manage;

import java.util.List;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

public interface ManageContract {
    interface View extends BaseView<ManageContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showWords(List<Words> words);

        void setData(Words word);

        void showLoadingWordsError();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void saveWord(Words words);

        void sendServer();
    }
}
