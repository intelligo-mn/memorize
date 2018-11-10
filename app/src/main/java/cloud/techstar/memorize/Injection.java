package cloud.techstar.memorize;

import android.content.Context;

import androidx.annotation.NonNull;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.database.local.MemorizeDatabase;
import cloud.techstar.memorize.database.local.WordsLocalDataSource;
import cloud.techstar.memorize.database.remote.WordsRemoteDataSource;
import cloud.techstar.memorize.utils.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static WordsRepository provideWordsRepository(@NonNull Context context) {
        checkNotNull(context);
        MemorizeDatabase database = MemorizeDatabase.getInstance(context);
        return WordsRepository.getInstance(WordsRemoteDataSource.getInstance(),
                WordsLocalDataSource.getInstance(new AppExecutors(),
                        database.wordsDao()));
    }
}
