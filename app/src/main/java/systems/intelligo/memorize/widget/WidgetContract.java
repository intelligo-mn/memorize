package systems.intelligo.memorize.widget;

import java.util.List;

import systems.intelligo.memorize.BasePresenter;
import systems.intelligo.memorize.BaseView;
import systems.intelligo.memorize.database.entity.Words;

public interface WidgetContract {
    interface View extends BaseView<WidgetContract.Presenter> {

        void initIntent();

    }

    interface Presenter extends BasePresenter {

        List<Words> loadWords();
    }
}

