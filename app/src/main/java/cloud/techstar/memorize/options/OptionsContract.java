package cloud.techstar.memorize.options;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;

public interface OptionsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void manageWordShow();

        void memorizeShow();

        void historyShow();
    }

    interface Presenter extends BasePresenter {
        void changeLanguage();

        void downloadWordsRemote();
    }
}
