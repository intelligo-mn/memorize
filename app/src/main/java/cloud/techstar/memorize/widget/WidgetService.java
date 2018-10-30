package cloud.techstar.memorize.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Random;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;

public class WidgetService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Random random = new Random();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        WidgetPresenter widgetPresenter = new WidgetPresenter(Injection.provideWordsRepository(AppMain.getContext()));

        int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        try {
            if (appWidgetIds.length > 0) {
                for (int widgetId : appWidgetIds) {
                    List<Words> qList = widgetPresenter.loadWords();
                    int nextInt = random.nextInt(qList.size());
                    RemoteViews remoteViews = new RemoteViews(getPackageName(),	R.layout.word_widget);
//                    remoteViews.setTextViewText(R.id.widget_character, qList.get(nextInt).getCharacter());
//                    remoteViews.setTextViewText(R.id.widget_meaning, qList.get(nextInt).getMeaning());
//                    remoteViews.setTextViewText(R.id.widget_meaning_mn, qList.get(nextInt).getMeaningMon());
//                    remoteViews.setTextViewText(R.id.widget_kanji, qList.get(nextInt).getKanji());
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