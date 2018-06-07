package cloud.techstar.jisho.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.Injection;
import cloud.techstar.jisho.MainActivity;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.utils.ConnectionDetector;
import cloud.techstar.jisho.utils.JishoConstant;
import cloud.techstar.jisho.utils.PrefManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{

    private Handler mHandler;

    private SplashPresenter splashPresenter;

    private SplashContract.Presenter presenter;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler(Looper.getMainLooper());
        Handler handler = new Handler(Looper.getMainLooper());
        PrefManager prefManager = new PrefManager(AppMain.getContext());

        boolean shouldLoadDataFromRepo = true;

        // Prevent the presenter from loading data from the repository if this is a config change.
        if (savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

        splashPresenter = new SplashPresenter(Injection.provideWordsRepository(getApplicationContext()),
                this,
                shouldLoadDataFromRepo);

        connectServer();

        if (prefManager.isFirstTimeLaunch()){
            prefManager.setIsFirstTimeLaunch(false);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 300);
        }
    }

    public void connectServer() {

        if (!ConnectionDetector.isNetworkAvailable(SplashActivity.this)) {
            Toast.makeText(SplashActivity.this, "No internet connections", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(JishoConstant.WEB_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("Server connection failed : " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                assert response.body() != null;
                final String res = response.body().string();
                Logger.json(res);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONObject ob = new JSONObject(res);
                            JSONArray memorize = ob.getJSONArray("memorize");
                            presenter.saveWord(memorize);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showEmptyWordError() {

    }

    @Override
    public void showWordList() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
