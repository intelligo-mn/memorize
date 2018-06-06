package cloud.techstar.jisho.database.local;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public abstract class JishoDatabase extends RoomDatabase {

    private static JishoDatabase INSTANCE;

    private static final Object sLock = new Object();

    public static JishoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        JishoDatabase.class, "jisho.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
