package cloud.techstar.memorize;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;

public class AppMain extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext(){
        return context;
    }
}