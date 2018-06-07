package cloud.techstar.jisho;

import android.content.Context;
import android.support.annotation.NonNull;

import cloud.techstar.jisho.database.WordsRepository;
import cloud.techstar.jisho.database.local.JishoDatabase;
import cloud.techstar.jisho.database.local.WordsLocalDataSource;
import cloud.techstar.jisho.database.remote.WordsRemoteDataSource;
import cloud.techstar.jisho.utils.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static WordsRepository provideWordsRepository(@NonNull Context context) {
        checkNotNull(context);
        JishoDatabase database = JishoDatabase.getInstance(context);
        return WordsRepository.getInstance(WordsRemoteDataSource.getInstance(),
                WordsLocalDataSource.getInstance(new AppExecutors(),
                        database.wordsDao()));
    }
}
