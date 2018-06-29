package cloud.techstar.memorize.manage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cloud.techstar.memorize.R;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.words.WordsContract;

public class ManageActivity extends AppCompatActivity implements ManageContract.View{

    private ManageContract.Presenter presenter;
    private EditText character, meaning, meaningMn, kanji;
    private Spinner partOfSpeech, level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        character = (EditText)findViewById(R.id.add_character);
        meaning  = (EditText)findViewById(R.id.add_meaning);
        meaningMn = (EditText)findViewById(R.id.add_meaning_mn);
        kanji = (EditText)findViewById(R.id.add_kanji);
        partOfSpeech = (Spinner)findViewById(R.id.add_partofspeech);
        level = (Spinner) findViewById(R.id.add_level);

        List<String> partOfSpeechs = new LinkedList<>(Arrays.asList("nouns","pronouns", "na-adjectives", "i-adjectives", "verbs", "particles", "adverbs", "conjunctions", "interjections"));

        List<String> levels = new LinkedList<>(Arrays.asList("jlpt5","jlpt4","jlpt3","jlpt2","jlpt1"));

        ArrayAdapter<String> partOfAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, partOfSpeechs);
        partOfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        partOfSpeech.setAdapter(partOfAdapter);
        level.setAdapter(levelAdapter);

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
