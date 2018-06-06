package cloud.techstar.jisho.database;

import android.support.annotation.NonNull;

public class WordsRepository implements WordsDataSource {
    @Override
    public void getWords(@NonNull LoadWordsCallback callback) {

    }

    @Override
    public void getWord(@NonNull String wordId, @NonNull GetWordCallback callback) {

    }

    @Override
    public void saveWord(@NonNull Words word) {

    }

    @Override
    public void memorizeWord(@NonNull Words word) {

    }

    @Override
    public void memorizeWord(@NonNull String wordId) {

    }

    @Override
    public void favWord(@NonNull Words word) {

    }

    @Override
    public void favWord(@NonNull String wordId) {

    }

    @Override
    public void refreshWords() {

    }

    @Override
    public void deleteWord(@NonNull String wordId) {

    }
}
