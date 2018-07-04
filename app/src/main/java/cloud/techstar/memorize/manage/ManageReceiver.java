package cloud.techstar.memorize.manage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.utils.ConnectionDetector;

public class ManageReceiver extends BroadcastReceiver {

     ManagePresenter managePresenter = new ManagePresenter(Injection.provideWordsRepository(AppMain.getContext()));

    @Override
    public void onReceive(Context context, Intent intent) {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        if (ConnectionDetector.isNetworkAvailable(context)){


        }
    }
}
