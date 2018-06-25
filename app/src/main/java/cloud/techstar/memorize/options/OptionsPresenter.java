package cloud.techstar.memorize.options;

import java.util.List;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;

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
                optionsView.showMessage("Download "+words.size()+" word.");
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
