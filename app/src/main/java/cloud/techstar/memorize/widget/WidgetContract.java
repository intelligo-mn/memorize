package cloud.techstar.memorize.widget;

import android.support.annotation.NonNull;

import java.util.List;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

public interface WidgetContract {
    interface View extends BaseView<WidgetContract.Presenter> {

        void initIntent();

    }

    interface Presenter extends BasePresenter {

        List<Words> loadWords();
    }
}

