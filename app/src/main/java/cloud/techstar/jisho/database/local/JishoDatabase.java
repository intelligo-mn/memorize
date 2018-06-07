package cloud.techstar.jisho.database.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import cloud.techstar.jisho.database.Words;

@Database(entities = {Words.class}, version = 1)
public abstract class JishoDatabase extends RoomDatabase {

    private static JishoDatabase INSTANCE;

    public abstract WordsDao wordsDao();

    private static final Object sLock = new Object();

    public static JishoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        JishoDatabase.class, "jishoroom.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
