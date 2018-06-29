package cloud.techstar.memorize.manage;

import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;

public class ManagePresenter implements ManageContract.Presenter{

    WordsRepository wordsRepository;

    ManageContract.View manageView;

    public ManagePresenter(WordsRepository wordsRepository, ManageContract.View manageView){
        this.wordsRepository = wordsRepository;
        this.manageView = manageView;
        manageView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void saveWord(Words words) {

    }

    @Override
    public void sendServer() {

    }

    @Override
    public void init() {

    }
}
