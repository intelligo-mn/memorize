package systems.intelligo.memorize.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import systems.intelligo.memorize.R;

public class WidgetView extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.word_widget);
        Intent intent = new Intent(context.getApplicationContext(), WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        PendingIntent pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        context.startService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

}