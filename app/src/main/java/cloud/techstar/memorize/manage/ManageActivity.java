package cloud.techstar.memorize.manage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.utils.ConnectionDetector;
import cloud.techstar.memorize.utils.MemorizeConstant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
    private String partOfValue, levelValue = null;
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
                presenter.saveWord(
                        new Words(character.getText().toString(),
                                meaning.getText().toString(),
                                meaningMn.getText().toString(),
                                kanji.getText().toString(),
                                partOfSpeech.getItemAtPosition(partOfSpeech.getSelectedItemPosition()).toString(),
                                level.getItemAtPosition(level.getSelectedItemPosition()).toString(),
                                getNowTime()));
            }
        });

        ManagePresenter managePresenter = new ManagePresenter(Injection.provideWordsRepository(AppMain.getContext()));

        JSONArray newData = managePresenter.getNewLocalData();
        JSONArray updateData = managePresenter.getUpdatedLocalData();
        if (ConnectionDetector.isNetworkAvailable(AppMain.getContext())){

            Logger.json(newData.toString());
            Logger.json(updateData.toString());
            final Handler handler = new Handler(Looper.getMainLooper());
            OkHttpClient client = new OkHttpClient();

            if (newData.length()>0){
                RequestBody requestBody = new FormBody.Builder()
                        .add("new", newData.toString())
                        .build();

                Request request = new Request.Builder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .url(MemorizeConstant.CREATE_MULTIPLE)
                        .post(requestBody)
                        .build();

                Logger.e(request.toString()+request.headers().toString()+request.body());

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, IOException e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        final String res = response.body().string();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    JSONObject ob = new JSONObject(res);
                                    if (ob.getString("message").equals("1")) {
                                        Logger.d("Complete");
                                    } else {
                                        Logger.d("Error");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
            } else if (updateData.length() > 0){

                RequestBody requestBody = new FormBody.Builder()
                        .add("updated", updateData.toString())
                        .build();

                Request request = new Request.Builder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .url(MemorizeConstant.EDIT_MULTIPLE)
                        .post(requestBody)
                        .build();

                Logger.e(request.toString()+request.headers().toString()+request.body());

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, IOException e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        final String res = response.body().string();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    JSONObject ob = new JSONObject(res);
                                    if (ob.getString("message").equals("1")) {
                                        Logger.d("Complete");
                                    } else {
                                        Logger.d("Error");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
            }
        }
    }

    public String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
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
