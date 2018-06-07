package cloud.techstar.jisho.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity(tableName = "words")
public final class Words {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;

    @NonNull
    @ColumnInfo(name = "character")
    private final String character;

    @NonNull
    @ColumnInfo(name = "meaning")
    private final String meaning;

    @Nullable
    @ColumnInfo(name = "meaningMon")
    private final String meaningMon;

    @Nullable
    @ColumnInfo(name = "kanji")
    private final String kanji;

    @Nullable
    @ColumnInfo(name = "partOfSpeech")
    private final String partOfSpeech;

    @Nullable
    @ColumnInfo(name = "level")
    private final String level;

    @Nullable
    @ColumnInfo(name = "memorized")
    private final boolean isMemorize;

    @Nullable
    @ColumnInfo(name = "favorited")
    private final boolean isFavorite;

    @Nullable
    @ColumnInfo(name = "created")
    private final String created;

    public Words(@NonNull String id, @NonNull String character, @NonNull String meaning, String meaningMon, String kanji, String partOfSpeech, String level, boolean isMemorize, boolean isFavorite, String created) {
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
    }

    @Ignore
    public Words(@NonNull String character, @NonNull String meaning, String meaningMon, String kanji, String partOfSpeech, String level, boolean isMemorize, boolean isFavorite, String created) {
        this(UUID.randomUUID().toString(), character, meaning, meaningMon, kanji, partOfSpeech, level, isMemorize, isFavorite, created);
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getCharacter() {
        return character;
    }

    @NonNull
    public String getMeaning() {
        return meaning;
    }

    @Nullable
    public String getMeaningMon() {
        return meaningMon;
    }

    @Nullable
    public String getKanji() {
        return kanji;
    }

    @Nullable
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    @Nullable
    public String getLevel() {
        return level;
    }

    @Nullable
    public boolean isMemorize() {
        return isMemorize;
    }

    @Nullable
    public boolean isFavorite() {
        return isFavorite;
    }

    @Nullable
    public String getCreated() {
        return created;
    }
}
