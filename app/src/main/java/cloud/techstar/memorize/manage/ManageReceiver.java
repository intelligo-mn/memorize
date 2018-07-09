package cloud.techstar.memorize.manage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsRepository;
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

import static com.google.common.base.Preconditions.checkNotNull;

public class ManageReceiver extends BroadcastReceiver {

     ManagePresenter managePresenter = new ManagePresenter(Injection.provideWordsRepository(AppMain.getContext()));

    @Override
    public void onReceive(Context context, Intent intent) {
        JSONArray newData = managePresenter.getNewLocalData();
        JSONArray updateData = managePresenter.getUpdatedLocalData();
        if (ConnectionDetector.isNetworkAvailable(context)){

            final Handler handler = new Handler(Looper.getMainLooper());
            OkHttpClient client = new OkHttpClient();

            if (newData.length()>0){
                MultipartBody.Builder formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("new", newData.toString());

                RequestBody requestBody = formBody.build();

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
                MultipartBody.Builder formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("updated", newData.toString());

                MultipartBody requestBody = formBody.build();

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
}
