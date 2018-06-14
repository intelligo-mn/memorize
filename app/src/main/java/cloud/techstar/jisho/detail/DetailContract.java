package cloud.techstar.jisho.detail;

import android.support.annotation.NonNull;

import cloud.techstar.jisho.BasePresenter;
import cloud.techstar.jisho.BaseView;
import cloud.techstar.jisho.database.Words;

public interface DetailContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);

        void showMissingWord();

        void setData(Words word);

        void showFavorite(boolean isFav);
    }

    interface Presenter extends BasePresenter{

        void favoriteWord();

        void memorizeWord();

    }
}
