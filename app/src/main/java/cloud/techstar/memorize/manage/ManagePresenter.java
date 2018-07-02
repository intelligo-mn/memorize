package cloud.techstar.memorize.manage;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

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

import static com.google.common.base.Preconditions.checkNotNull;

public class ManagePresenter implements ManageContract.Presenter{

    private final Handler handler;

    private WordsRepository wordsRepository;

    private ManageContract.View manageView;

    public ManagePresenter(WordsRepository wordsRepository, ManageContract.View manageView){
        this.wordsRepository = wordsRepository;
        this.manageView = manageView;

        handler = new Handler();

        manageView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void saveWord(Words words) {
        wordsRepository.saveWord(words);
        if (ConnectionDetector.isNetworkAvailable(AppMain.getContext()))
            sendServer(words);

        manageView.clearFields();
    }

    @Override
    public void sendServer(Words words) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("character", words.getCharacter())
                .add("meanings", words.getMeaning())
                .add("meaningsMongolia", checkNotNull(words.getMeaningMon()))
                .add("partOfSpeech", checkNotNull(words.getPartOfSpeech()))
                .add("kanji", checkNotNull(words.getKanji()))
                .add("level", checkNotNull(words.getLevel()))
                .build();

        Request request = new Request.Builder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url(MemorizeConstant.CREATE_WORD)
                .post(formBody)
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
                                manageView.showToast("Сэрвэрт амжилттай илгээлээ");
                            } else {
                                manageView.showToast("Сэрвэрт хадгалах үед алдаа гарлаа");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    @Override
    public void init() {

    }
}
