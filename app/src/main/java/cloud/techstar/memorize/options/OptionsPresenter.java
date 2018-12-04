package cloud.techstar.memorize.options;

import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.utils.ConnectionDetector;
import cloud.techstar.memorize.utils.MemorizeConstant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OptionsPresenter implements OptionsContract.Presenter{

    private final WordsRepository wordsRepository;

    private final OptionsContract.View optionsView;
    private JSONArray newWordsArray;

    public OptionsPresenter(WordsRepository wordsRepository, OptionsContract.View optionsView) {
        this.wordsRepository = wordsRepository;
        this.optionsView = optionsView;
        optionsView.setPresenter(this);
    }

    @Override
    public void init() {

        newWordsArray = new JSONArray();

        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {

                List<Words> mainWords = new ArrayList<Words>();

                for (Words word : words) {
                    if (word.isMemorize()) {
                        mainWords.add(word);
                    }

                    if (word.getIsLocal()){
                        JSONObject updatedWords = new JSONObject();
                        try {
                            updatedWords.put("id", word.getId());
                            updatedWords.put("character", word.getCharacter());
                            updatedWords.put("meanings",  new JSONArray(word.getMeaning()));
                            updatedWords.put("meaningsMongolia", new JSONArray(word.getMeaningMon()));
                            updatedWords.put("partOfSpeech",  new JSONArray(word.getPartOfSpeech()));
                            updatedWords.put("kanji", word.getKanji());
                            updatedWords.put("tag",  new JSONArray(word.getTag()));
                            updatedWords.put("level",  word.getTag());
                            updatedWords.put("isMemorize", word.isMemorize());
                            updatedWords.put("isFavorite", word.isFavorite());
                            updatedWords.put("isMemorize", word.isMemorize());
                            newWordsArray.put(updatedWords);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
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

        if (!ConnectionDetector.isNetworkAvailable(AppMain.getContext())){
            optionsView.showToast("No internet connection !!!");
            return;
        }

        optionsView.setLoadingIndicator(true);
        wordsRepository.getWordsFromRemoteDataSource(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                optionsView.setLoadingIndicator(false);
                optionsView.showToast("Download "+words.size()+" word.");
            }

            @Override
            public void onDataNotAvailable() {
                optionsView.setLoadingIndicator(false);
                optionsView.showToast("Entry not available.");
            }
        });
    }

    @Override
    public void sendWordsRemote() {

        if (!ConnectionDetector.isNetworkAvailable(AppMain.getContext())){
            optionsView.showToast("No internet connection !!!");
            return;
        }

        optionsView.setLoadingIndicator(true);

        if (newWordsArray.length() > 0) {
            sendDatas();
        } else {
            optionsView.setLoadingIndicator(false);
            optionsView.showToast("Өөрчлөлт байхгүй байна");
        }
    }

    public void sendDatas(){
        final Handler handler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("words", newWordsArray.toString())
                .build();

        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(MemorizeConstant.MANAGE_MULTIPLE)
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
                            if (ob.getString("status").equals("success")) {
                                optionsView.showToast(ob.getString("message"));
                            } else {
                                optionsView.showToast(ob.getString("message"));
                            }
                            optionsView.setLoadingIndicator(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            optionsView.setLoadingIndicator(false);
                            optionsView.showToast("Алдаа гарлаа: "+e);
                        }

                    }
                });
            }
        });
    }
}
