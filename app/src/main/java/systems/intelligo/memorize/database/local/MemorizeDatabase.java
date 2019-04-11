package systems.intelligo.memorize.database.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.utils.Converters;

@Database(entities = {Words.class}, version = 4, exportSchema = false)
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
