package cloud.techstar.memorize.manage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private WordsRepository wordsRepository;

    private ManageContract.View manageView;

    private Handler handler;

    public ManagePresenter(WordsRepository wordsRepository, ManageContract.View manageView){
        this.wordsRepository = wordsRepository;
        this.manageView = manageView;
        handler = new Handler(Looper.getMainLooper());
        manageView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void saveWord(final Words words) {
        if (ConnectionDetector.isNetworkAvailable(AppMain.getContext())){
            wordsRepository.saveWord(words);
            sendServer(words);
        } else {
            manageView.setLoadingIndicator(true);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    wordsRepository.saveWord(words);
                    manageView.setLoadingIndicator(false);
                    manageView.clearFields();
                    manageView.showToast("Амилттай нэмэгдлээ.");
                }
            });
        }
    }

    @Override
    public void sendServer(Words words) {
        manageView.setLoadingIndicator(true);
        final Handler handler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("character", words.getCharacter())
                .add("meanings", words.getMeaning().get(0))
                .add("meaningsMongolia", checkNotNull(words.getMeaningMon().get(0)))
                .add("partOfSpeech", checkNotNull(words.getPartOfSpeech().get(0)))
                .add("kanji", checkNotNull(words.getKanji()))
                .add("level", checkNotNull(words.getLevel().get(0)))
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
                manageView.setLoadingIndicator(false);
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
                                manageView.showToast(ob.getString("message"));
                            } else if (ob.getString("status").equals("failed")){
                                manageView.showToast(ob.getString("message"));
                            } else if (ob.getString("status").equals("duplicated")){
                                manageView.showToast(ob.getString("message"));
                            } else {
                                manageView.showToast("Алдаа гарлаа");
                            }

                            manageView.setLoadingIndicator(false);
                            manageView.clearFields();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            manageView.showToast("Сэрвэрт холбогдоход алдаа гарлаа");
                            manageView.setLoadingIndicator(false);
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
