package cloud.techstar.jisho;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.util.logging.Logger;

public class AppMain extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}