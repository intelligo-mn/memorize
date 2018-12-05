package cloud.techstar.memorize.statistic;

import com.github.mikephil.charting.data.PieData;

import java.util.List;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Words;

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
