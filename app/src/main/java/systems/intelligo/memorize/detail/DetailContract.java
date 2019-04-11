package systems.intelligo.memorize.detail;

import systems.intelligo.memorize.BasePresenter;
import systems.intelligo.memorize.BaseView;
import systems.intelligo.memorize.database.entity.Words;

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
