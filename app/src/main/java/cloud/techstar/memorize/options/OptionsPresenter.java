package cloud.techstar.memorize.options;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.words.WordFilterType;

public class OptionsPresenter implements OptionsContract.Presenter{

    private final WordsRepository wordsRepository;

    private final OptionsContract.View optionsView;

    public OptionsPresenter(WordsRepository wordsRepository, OptionsContract.View optionsView) {
        this.wordsRepository = wordsRepository;
        this.optionsView = optionsView;
        optionsView.setPresenter(this);
    }

    @Override
    public void init() {
        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {

                List<Words> mainWords = new ArrayList<Words>();

                for (Words word : words) {
                    if (word.isMemorize()) {
                        mainWords.add(word);
                    }

                    if (word.getIsLocal() == 1)
                        Logger.e("New words : "+word.toString());
                    else if (word.getIsLocal() == 2)
                        Logger.e("Updated words : "+word.toString());
                }
                optionsView.showToast("Memorized word "+mainWords.size());
            }

            @Override
            public void onDataNotAvailable() {


            }
        });
    }

    @Override
    public void changeLanguage() {

    }

    @Override
    public void downloadWordsRemote() {
        optionsView.setLoadingIndicator(true);
        wordsRepository.getWordsFromRemoteDataSource(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                optionsView.setLoadingIndicator(false);
                optionsView.showToast("Download "+words.size()+" word.");
            }

            @Override
            public void onDataNotAvailable() {
                optionsView.showToast("Data not available.");
            }
        });
    }

    @Override
    public void sendWordsRemote() {

    }
}
