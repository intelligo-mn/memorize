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

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
