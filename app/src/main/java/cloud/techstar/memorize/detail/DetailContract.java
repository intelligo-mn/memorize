package cloud.techstar.memorize.detail;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showMissingWord();

        void setData(Words word, boolean refresh);

        void showFavorite(boolean isFav);

    }

    interface Presenter extends BasePresenter {

        void favoriteWord();

        void memorizeWord();

        void addMeaning(String meaning);
    }
}
