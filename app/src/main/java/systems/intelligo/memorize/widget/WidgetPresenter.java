package systems.intelligo.memorize.widget;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.database.WordsDataSource;

public class WidgetPresenter implements WidgetContract.Presenter, WordsDataSource.LoadWordsCallback {

    @NonNull
    private final WordsDataSource wordRepository;

    public WidgetPresenter(@NonNull WordsDataSource wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void init() {
        loadWords();
    }

    @Override
    public void onWordsLoaded(List<Words> words) {

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public List<Words> loadWords() {

        final List<Words> mainWords = new ArrayList<Words>();

        wordRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {

                Logger.e("Presenter words count : " + words.size());

                for (Words word : words) {
                    if (word.isFavorite())
                        mainWords.add(word);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return mainWords;
    }
}
