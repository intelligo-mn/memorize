package cloud.techstar.memorize.database.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.utils.Converters;

@Database(entities = {Words.class}, version = 2)

@TypeConverters({Converters.class})
public abstract class MemorizeDatabase extends RoomDatabase {

    private static MemorizeDatabase INSTANCE;

    public abstract WordsDao wordsDao();

    private static final Object sLock = new Object();

    public static MemorizeDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MemorizeDatabase.class, "jisho.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }
}
