package cloud.techstar.jisho.splash;

import org.json.JSONArray;

import cloud.techstar.jisho.BasePresenter;
import cloud.techstar.jisho.BaseView;

public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void showEmptyWordError();

        void showWordList();
    }

    interface Presenter extends BasePresenter {
        void saveWord(JSONArray memorize);

        void populateWords();

        boolean isDataMissing();
    }
}
