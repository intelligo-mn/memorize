package cloud.techstar.memorize.database;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private boolean is_common;
    private List<String> tags;
    private List<Japanese> japanese;
    private List<Sense> senses;

    public Data() {
    }

    public boolean isIs_common() {
        return is_common;
    }

    public void setIs_common(boolean is_common) {
        this.is_common = is_common;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Japanese> getJapanese() {
        return japanese;
    }

    public void setJapanese(List<Japanese> japanese) {
        this.japanese = japanese;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }
}
