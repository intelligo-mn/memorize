package cloud.techstar.memorize.manage;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.progressbar.TSProgressBar;

public class ManageActivity extends AppCompatActivity implements ManageContract.View{

    private ManageContract.Presenter presenter;
    private EditText character, meaning, meaningMn, kanji;
    private Spinner partOfSpeech, level;
    private Handler handler;
    private Button addBtn;
    private ImageButton backBtn;
    private String partOfValue, levelValue = null;
    private TSProgressBar prgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        prgLoading = (TSProgressBar)findViewById(R.id.add_progress);
        character = (EditText)findViewById(R.id.add_character);
        meaning  = (EditText)findViewById(R.id.add_meaning);
        meaningMn = (EditText)findViewById(R.id.add_meaning_mn);
        kanji = (EditText)findViewById(R.id.add_kanji);
        partOfSpeech = (Spinner)findViewById(R.id.add_partofspeech);
        level = (Spinner) findViewById(R.id.add_level);
        addBtn = (Button) findViewById(R.id.add_btn);
        backBtn = (ImageButton)findViewById(R.id.back);

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

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields())
                    return;
//                presenter.saveWord(
//                        new Words(character.getText().toString(),
//                                meaning.getText().toString(),
//                                meaningMn.getText().toString(),
//                                kanji.getText().toString(),
//                                partOfSpeech.getItemAtPosition(partOfSpeech.getSelectedItemPosition()).toString(),
//                                level.getItemAtPosition(level.getSelectedItemPosition()).toString(),
//                                MemorizeUtils.getNowTime()));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active)
            prgLoading.setVisibility(View.VISIBLE);
        else
            prgLoading.setVisibility(View.GONE);
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
    public void clearFields() {
        character.setText("");
        meaning.setText("");
        meaningMn.setText("");
        kanji.setText("");
    }

    @Override
    public boolean validateFields() {
        boolean valid = true;

        if (character.getText().toString().isEmpty()) {
            character.setError("Япон үг оруулна уу");
            valid = false;
        } else {
            character.setError(null);
        }

        if (meaning.getText().toString().isEmpty()) {
            meaning.setError("Орчуулга оруулна уу");
            valid = false;
        } else {
            meaning.setError(null);
        }

        if (meaningMn.getText().toString().isEmpty()) {
            meaningMn.setError("Монгол орчуулга оруулна уу");
            valid = false;
        } else {
            meaningMn.setError(null);
        }

        if (kanji.getText().toString().isEmpty()) {
            kanji.setText(character.getText());
        }
        return valid;
    }

    @Override
    public void setPresenter(ManageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
