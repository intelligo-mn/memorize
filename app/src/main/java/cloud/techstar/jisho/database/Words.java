package cloud.techstar.jisho.database;

public class Words {

    public static final String TABLE_WORDS       = "words";
    public static final String WORDS_ID          = "id";
    public static final String WORDS_CHARACTER   = "character";
    public static final String WORDS_MEANING     = "meaning";
    public static final String WORDS_MEANING_MN  = "meaningMon";
    public static final String WORDS_KANJI       = "kanji";
    public static final String WORDS_PART_OF_SPEECH = "partOfSpeech";
    public static final String WORDS_LEVEL       = "level";
    public static final String WORDS_IS_MEMORIZE = "isMemorize";
    public static final String WORDS_IS_FAVORITE = "isFavorite";
    public static final String WORDS_CREATED = "created";

    public static final int WORDS_ID_INDEX         = 0;
    public static final int WORDS_CHARACTER_INDEX  = 1;
    public static final int WORDS_MEANING_INDEX    = 2;
    public static final int WORDS_MEANING_MN_INDEX = 3;
    public static final int WORDS_KANJI_INDEX      = 4;
    public static final int WORDS_PART_OF_SPEECH_INDEX = 5;
    public static final int WORDS_LEVEL_INDEX      = 6;
    public static final int WORDS_IS_MEMORIZE_INDEX = 7;
    public static final int WORDS_IS_FAVORITE_INDEX = 8;
    public static final int WORDS_CREATED_INDEX = 9;

    private String id;
    private String character;
    private String meaning;
    private String meaningMon;
    private String kanji;
    private String partOfSpeech;
    private String level;
    private String isMemorize;
    private String isFavorite;
    private String created;

    public Words() {
    }

    public Words(String id, String character, String meaning, String meaningMon, String kanji, String partOfSpeech, String level, String isMemorize, String isFavorite, String created) {
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

    public String getMeaningMon() {
        return meaningMon;
    }

    public void setMeaningMon(String meaningMon) {
        this.meaningMon = meaningMon;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
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

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Words{" +
                "id='" + id + '\'' +
                ", character='" + character + '\'' +
                ", meaning='" + meaning + '\'' +
                ", meaningMon='" + meaningMon + '\'' +
                ", kanji='" + kanji + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", level='" + level + '\'' +
                ", isMemorize='" + isMemorize + '\'' +
                ", isFavorite='" + isFavorite + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
