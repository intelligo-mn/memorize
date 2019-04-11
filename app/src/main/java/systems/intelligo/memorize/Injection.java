package systems.intelligo.memorize;

import android.content.Context;

import androidx.annotation.NonNull;
import systems.intelligo.memorize.database.WordsRepository;
import systems.intelligo.memorize.database.local.MemorizeDatabase;
import systems.intelligo.memorize.database.local.WordsLocalDataSource;
import systems.intelligo.memorize.database.remote.WordsRemoteDataSource;
import systems.intelligo.memorize.utils.AppExecutors;

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
