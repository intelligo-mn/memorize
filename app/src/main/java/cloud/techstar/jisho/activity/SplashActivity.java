package cloud.techstar.jisho.activity;

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
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.utils.ConnectionDetector;
import cloud.techstar.jisho.utils.JishoConstant;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;
import cloud.techstar.jisho.utils.PrefManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler(Looper.getMainLooper());
        Handler handler = new Handler(Looper.getMainLooper());
        PrefManager prefManager = new PrefManager(AppMain.getContext());
        //

        if (prefManager.isFirstTimeLaunch()){
            connectServer();
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

                            Logger.json(memorize.toString());

                            if (memorize.length() > 0) {
                                WordTable wordTable = new WordTable();

                                wordTable.deleteAll();

                                for (int i = 0; i < memorize.length(); i++) {
                                    Words words = new Words();
                                    words.setId(memorize.getJSONObject(i).getString("_id"));
                                    words.setCharacter(memorize.getJSONObject(i).getString("character"));
                                    words.setMeaning(memorize.getJSONObject(i).getString("meanings"));
                                    words.setMeaningMon(memorize.getJSONObject(i).getString("meaningsMongolia"));
                                    words.setKanji(memorize.getJSONObject(i).getString("kanji"));
                                    words.setPartOfSpeech(memorize.getJSONObject(i).getString("partOfSpeech"));
                                    words.setLevel(memorize.getJSONObject(i).getString("level"));
                                    words.setIsMemorize(memorize.getJSONObject(i).getString("isMemorize"));
                                    words.setIsFavorite(memorize.getJSONObject(i).getString("isFavorite"));
                                    words.setCreated(memorize.getJSONObject(i).getString("created"));
                                    wordTable.insert(words);
                                }
                            } else {
                                Toast.makeText(SplashActivity.this, "Words not found", Toast.LENGTH_LONG)
                                        .show();
                            }
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
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
}
