package systems.intelligo.memorize.statistic;

import systems.intelligo.memorize.BasePresenter;
import systems.intelligo.memorize.BaseView;

public interface StatisticContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void setStatData(int total, int memorized, int favorited, int active);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void setStatsPie();
    }
}
