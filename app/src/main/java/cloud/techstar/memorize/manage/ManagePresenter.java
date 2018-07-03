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

    private Handler handler;

    private WordsRepository wordsRepository;

    private ManageContract.View manageView;

    public ManagePresenter(WordsRepository wordsRepository, ManageContract.View manageView){
        this.wordsRepository = wordsRepository;
        this.manageView = manageView;

        handler = new Handler();

        manageView.setPresenter(this);
    }

    public ManagePresenter(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void saveWord(Words words) {
        wordsRepository.saveWord(words);
        if (ConnectionDetector.isNetworkAvailable(AppMain.getContext()))
            sendServer();
        wordsRepository.sendServer(words);
        manageView.clearFields();
    }

    @Override
    public void sendServer() {

    }

    @Override
    public void init() {

    }
}
