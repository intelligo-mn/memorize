package cloud.techstar.memorize.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "words")
public final class Words implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "character")
    private String character;

    @NonNull
    @ColumnInfo(name = "meaning")
    private List<String> meaning;

    @Nullable
    @ColumnInfo(name = "meaningMon")
    private List<String> meaningMon;

    @Nullable
    @ColumnInfo(name = "kanji")
    private String kanji;

    @Nullable
    @ColumnInfo(name = "partOfSpeech")
    private List<String> partOfSpeech;

    @Nullable
    @ColumnInfo(name = "level")
    private List<String> level;

    @Nullable
    @ColumnInfo(name = "memorized")
    private boolean isMemorize;

    @Nullable
    @ColumnInfo(name = "favorited")
    private boolean isFavorite;

    @Nullable
    @ColumnInfo(name = "created")
    private String created;

    @Nullable
    @ColumnInfo(name = "local")
    private int isLocal;

    public Words(@NonNull String id, @NonNull String character, @NonNull List<String> meaning, List<String> meaningMon, String kanji, List<String> partOfSpeech, List<String> level, boolean isMemorize, boolean isFavorite, String created, int isLocal) {
        this.id = id;
        this.character = character;
        this.meaning = meaning;
        this.meaningMon = meaningMon;
        this.kanji = kanji;
        this.partOfSpeech = partOfSpeech;
        this.level = level;
        this.isMemorize = isMemorize;
        this.isFavorite = isFavorite;
        this.created = created;
        this.isLocal = isLocal;
    }

    /**
     * Use this constructor to create an new word
     */
    @Ignore
    public Words(@NonNull String character, @NonNull List<String> meaning, List<String> meaningMon, String kanji, List<String> partOfSpeech, List<String> level, String created) {
        this(UUID.randomUUID().toString(), character, meaning, meaningMon, kanji, partOfSpeech, level, false, false, created, 1);
    }

    /**
     * Use this constructor to create an active word
     */
    @Ignore
    public Words(@NonNull String id, @NonNull String character, @NonNull List<String> meaning, List<String> meaningMon, String kanji, List<String> partOfSpeech, List<String> level, String created) {
        this(id, character, meaning, meaningMon, kanji, partOfSpeech, level, false, false, created, 1);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getCharacter() {
        return character;
    }

    public void setCharacter(@NonNull String character) {
        this.character = character;
    }

    @NonNull
    public List<String> getMeaning() {
        return meaning;
    }

    public void setMeaning(@NonNull List<String> meaning) {
        this.meaning = meaning;
    }

    @Nullable
    public List<String> getMeaningMon() {
        return meaningMon;
    }

    public void setMeaningMon(@Nullable List<String> meaningMon) {
        this.meaningMon = meaningMon;
    }

    @Nullable
    public String getKanji() {
        return kanji;
    }

    public void setKanji(@Nullable String kanji) {
        this.kanji = kanji;
    }

    @Nullable
    public List<String> getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(@Nullable List<String> partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Nullable
    public List<String> getLevel() {
        return level;
    }

    public void setLevel(@Nullable List<String> level) {
        this.level = level;
    }

    @Nullable
    public boolean isMemorize() {
        return isMemorize;
    }

    public void setMemorize(@Nullable boolean memorize) {
        isMemorize = memorize;
    }

    @Nullable
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(@Nullable boolean favorite) {
        isFavorite = favorite;
    }

    @Nullable
    public String getCreated() {
        return created;
    }

    public void setCreated(@Nullable String created) {
        this.created = created;
    }

    @Nullable
    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(@Nullable int isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Words words = (Words) o;
        return isMemorize == words.isMemorize &&
                isFavorite == words.isFavorite &&
                Objects.equal(id, words.id) &&
                Objects.equal(character, words.character) &&
                Objects.equal(meaning, words.meaning) &&
                Objects.equal(meaningMon, words.meaningMon) &&
                Objects.equal(kanji, words.kanji) &&
                Objects.equal(partOfSpeech, words.partOfSpeech) &&
                Objects.equal(level, words.level) &&
                Objects.equal(created, words.created);
    }

    @Override
    public String toString() {
        return "Words{" +
                "character='" + character + '\'' +
                ", meaning='" + meaning + '\'' +
                ", meaningMon='" + meaningMon + '\'' +
                '}';
    }

    public String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, character, meaning, meaningMon, kanji, partOfSpeech, level, isMemorize, isFavorite, created);
    }


}
