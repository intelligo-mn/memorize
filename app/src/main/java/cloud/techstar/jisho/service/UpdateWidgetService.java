package cloud.techstar.jisho.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.activity.WordWidget;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class UpdateWidgetService extends Service {

    WordTable wordTable;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Random random = new Random();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        wordTable = new WordTable();
        try {
            if (appWidgetIds.length > 0) {
                for (int widgetId : appWidgetIds) {
                    List<Words> qList = wordTable.selectFavorite();
                    int nextInt = random.nextInt(qList.size());
                    RemoteViews remoteViews = new RemoteViews(getPackageName(),	R.layout.word_widget);
                    remoteViews.setTextViewText(R.id.widget_word_text, qList.get(nextInt).getCharacter()+" - "+qList.get(nextInt).getMeaning());
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }
                stopSelf();
            }
        } catch (Exception ex ){
            ex.printStackTrace();
        }

        super.onStart(intent, startId);
    }

}