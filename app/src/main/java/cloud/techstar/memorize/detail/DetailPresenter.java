package cloud.techstar.memorize.detail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsRepository;
import cloud.techstar.memorize.utils.MemorizeUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract.Presenter{

    private final WordsRepository wordsRepository;

    private final DetailContract.View detailView;

    @Nullable
    private Words word;

    public DetailPresenter(Words word, WordsRepository wordsRepository, DetailContract.View detailView) {
        this.wordsRepository = wordsRepository;
        this.detailView = detailView;
        this.word = word;
        detailView.setPresenter(this);
    }

    @Override
    public void init() {
        openWord();
    }

    private void openWord() {
        if (word==null) {
            detailView.showMissingWord();
            return;
        } else {

            detailView.setData(word, false);
        }
    }

    @Override
    public void favoriteWord() {
        checkNotNull(word, "favoriteWord cannot be null!");
        wordsRepository.favWord(word);

        if (!word.isFavorite()) {
            detailView.showFavorite(true);
        }
    }

    @Override
    public void memorizeWord() {

    }

    @Override
    public void addMeaning(String meaning) {
        checkNotNull(word, "Word cannot be null!");
        List<String> meaings = new ArrayList<>();
        meaings.add(meaning);

        Words updatedWord = new Words(word.getId(), word.getCharacter(), word.getMeaning(), meaings, word.getKanji(), word.getPartOfSpeech(),
                word.getLevel(), word.getTag(), word.isMemorize(), word.isFavorite(), MemorizeUtils.getNowTime(), word.getIsLocal());
        // Хэрэв вэбийн өгөгдөлтэй ижил утгатай байвал өөрчлөгдсөн болгоно
        if (updatedWord.getIsLocal() == 0){
            updatedWord.setIsLocal(2);
        }
        wordsRepository.updateWord(updatedWord);
        detailView.showToast("Монгол орчуулга нэмэгдлээ.");
        word.setMeaningMon(Collections.singletonList(meaning));
        detailView.setData(word, true);
    }
}
