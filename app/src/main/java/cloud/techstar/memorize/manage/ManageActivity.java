package cloud.techstar.memorize.manage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import cloud.techstar.jisho.R;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.words.WordsContract;

public class ManageActivity extends AppCompatActivity implements ManageContract.View{

    private ManageContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        new ManagePresenter(Injection.provideWordsRepository(getApplicationContext()), this);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showWords(List<Words> words) {

    }

    @Override
    public void setData(Words word) {

    }

    @Override
    public void showLoadingWordsError() {

    }

    @Override
    public void setPresenter(ManageContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
