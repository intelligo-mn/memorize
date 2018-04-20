package cloud.techstar.jisho.models;

public class Words {

    public static final String TABLE_WORDS       = "words";
    public static final String WORDS_ID          = "id";
    public static final String WORDS_CHARACTER   = "character";
    public static final String WORDS_MEANING     = "meaning";
    public static final String WORDS_KANJI       = "kanji";
    public static final String WORDS_LEVEL       = "level";
    public static final String WORDS_ISMEMORIZE  = "ismemorize";

    public static final int WORDS_ID_INDEX         = 0;
    public static final int WORDS_CHARACTER_INDEX  = 1;
    public static final int WORDS_MEANING_INDEX    = 2;
    public static final int WORDS_KANJI_INDEX      = 3;
    public static final int WORDS_LEVEL_INDEX      = 4;
    public static final int WORDS_ISMEMORIZE_INDEX = 5;

    private String id;
    private String character;
    private String meaning;
    private String kanji;
    private String level;
    private String isMemorize;

    public Words() {
    }

    public Words(String id, String character, String meaning, String kanji, String level, String isMemorize) {
        this.id = id;
        this.character = character;
        this.meaning = meaning;
        this.kanji = kanji;
        this.level = level;
        this.isMemorize = isMemorize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsMemorize() {
        return isMemorize;
    }

    public void setIsMemorize(String isMemorize) {
        this.isMemorize = isMemorize;
    }
}
