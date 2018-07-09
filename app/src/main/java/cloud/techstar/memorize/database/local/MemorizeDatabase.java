package cloud.techstar.memorize.database.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import cloud.techstar.memorize.database.Words;

@Database(entities = {Words.class}, version = 1)
public abstract class MemorizeDatabase extends RoomDatabase {

    private static MemorizeDatabase INSTANCE;

    public abstract WordsDao wordsDao();

    private static final Object sLock = new Object();

    public static MemorizeDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MemorizeDatabase.class, "jisho.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
