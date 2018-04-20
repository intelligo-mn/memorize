package cloud.techstar.jisho;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;

import cloud.techstar.jisho.database.DatabaseHelper;
import cloud.techstar.jisho.database.DatabaseManager;

public class AppMain extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        DatabaseHelper dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext(){
        return context;
    }
}