package cloud.techstar.jisho.models;

public class Words {
    String id;
    String character;
    String meaning;
    String kanji;
    String level;
    boolean isMemorize;

    public Words() {
    }

    public Words(String id, String character, String meaning, String kanji, String level, boolean isMemorize) {
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

    public boolean isMemorize() {
        return isMemorize;
    }

    public void setMemorize(boolean memorize) {
        isMemorize = memorize;
    }
}
