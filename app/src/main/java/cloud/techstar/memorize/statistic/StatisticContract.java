package cloud.techstar.memorize.statistic;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;

public interface StatisticContract {
    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void setStats();
    }
}
