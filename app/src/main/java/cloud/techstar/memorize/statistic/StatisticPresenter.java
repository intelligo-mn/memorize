package cloud.techstar.memorize.statistic;

import java.util.List;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;

public class StatisticPresenter implements StatisticContract.Presenter{
    private final WordsRepository wordsRepository;
    private final StatisticContract.View statisticView;

    public StatisticPresenter(WordsRepository wordsRepository, StatisticContract.View statisticView) {
        this.wordsRepository = wordsRepository;
        this.statisticView = statisticView;
        statisticView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void setStats() {

        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                for (Words word : words) {
                    if (word.isMemorize()) {

                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void init() {

    }
}
