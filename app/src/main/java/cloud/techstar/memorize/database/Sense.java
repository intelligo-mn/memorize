package cloud.techstar.memorize.database;

import java.util.ArrayList;
import java.util.List;

public class Sense {
    private List<String> english_definitions;
    private List<String> parts_of_speech;
    private List<Link> links;
    private List<String> tags;
    private List<String> restrictions;
    private List<String> see_also;
    private List<String> antonyms;
    private List<Source> source;
    private List<String> info;

    public List<String> getEnglish_definitions() {
        return english_definitions;
    }

    public void setEnglish_definitions(List<String> english_definitions) {
        this.english_definitions = english_definitions;
    }

    public List<String> getParts_of_speech() {
        return parts_of_speech;
    }

    public void setParts_of_speech(List<String> parts_of_speech) {
        this.parts_of_speech = parts_of_speech;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public List<String> getSee_also() {
        return see_also;
    }

    public void setSee_also(List<String> see_also) {
        this.see_also = see_also;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public List<Source> getSource() {
        return source;
    }

    public void setSource(List<Source> source) {
        this.source = source;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
