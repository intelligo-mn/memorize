package systems.intelligo.memorize.options;

import systems.intelligo.memorize.BasePresenter;
import systems.intelligo.memorize.BaseView;

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

        void sendWordsRemote();
    }
}
