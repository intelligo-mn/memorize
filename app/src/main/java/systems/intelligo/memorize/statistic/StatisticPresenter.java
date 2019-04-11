package systems.intelligo.memorize.statistic;

import java.util.List;

import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.database.WordsDataSource;
import systems.intelligo.memorize.database.WordsRepository;

public class StatisticPresenter implements StatisticContract.Presenter {
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
    public void setStatsPie() {

        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {

                int mem = 0;
                int fav = 0;
                int active = 0;
                int total = words.size();
                for (Words word : words) {

                    if (word.isMemorize()) {
                        mem++;
                    } else if (word.isFavorite() && !word.isMemorize()) {
                        fav++;
                    } else {
                        active++;
                    }
                }

                statisticView.setStatData(total, mem, fav, active);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void init() {
        setStatsPie();
    }
}
