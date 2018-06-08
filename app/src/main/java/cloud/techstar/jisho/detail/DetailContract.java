package cloud.techstar.jisho.detail;

import cloud.techstar.jisho.BasePresenter;
import cloud.techstar.jisho.BaseView;
import cloud.techstar.jisho.database.Words;

public interface DetailContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);

        void showMissingWord();

        void setData(Words word);
    }

    interface Presenter extends BasePresenter{

    }
}
