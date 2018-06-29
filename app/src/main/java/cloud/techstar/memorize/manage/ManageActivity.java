package cloud.techstar.memorize.manage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import cloud.techstar.memorize.R;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.utils.MemorizeConstant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageActivity extends AppCompatActivity implements ManageContract.View{

    private ManageContract.Presenter presenter;
    private EditText character, meaning, meaningMn, kanji;
    private Spinner partOfSpeech, level;
    private Handler handler;
    private Button addBtn;

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
        addBtn = (Button) findViewById(R.id.add_btn);

        handler = new Handler();
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
                create();
            }
        });

    }

    public void create () {

        final String randomId = UUID.randomUUID().toString().substring(0, 8).replaceAll("-", "");

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("character", "demo")
                .addFormDataPart("meanings", "demo")
                .addFormDataPart("meaningsMongolia", "demo")
                .addFormDataPart("partOfSpeech", "demo")
                .addFormDataPart("kanji", "demo")
                .addFormDataPart("level", "demo")
                .build();

        Request request = new Request.Builder()
                .url(MemorizeConstant.CREATE_WORD)
                .addHeader("Content-Type", "application/json")
                .addHeader("Access-Control-Allow-Origin", "*")
                .addHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With")
                .addHeader("Access-Control-Allow-Methods", "GET, PUT, POST")
                .post(formBody)
                .build();

        Logger.e(request.toString()+request.headers().toString()+request.body());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Logger.e(res.toString());

                    }
                });
            }
        });
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
