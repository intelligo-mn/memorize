package cloud.techstar.memorize.database;

import java.util.ArrayList;

class Sense {
    private ArrayList<String> english_definitions;
    private ArrayList<String> parts_of_speech;
    private ArrayList<Link> links;
    private ArrayList<String> tags;
    private ArrayList<String> restrictions;
    private ArrayList<String> see_also;
    private ArrayList<String> antonyms;
    private ArrayList<Source> source;
    private ArrayList<String> info;

    public ArrayList<String> getEnglish_definitions() {
        return english_definitions;
    }

    public void setEnglish_definitions(ArrayList<String> english_definitions) {
        this.english_definitions = english_definitions;
    }

    public ArrayList<String> getParts_of_speech() {
        return parts_of_speech;
    }

    public void setParts_of_speech(ArrayList<String> parts_of_speech) {
        this.parts_of_speech = parts_of_speech;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(ArrayList<String> restrictions) {
        this.restrictions = restrictions;
    }

    public ArrayList<String> getSee_also() {
        return see_also;
    }

    public void setSee_also(ArrayList<String> see_also) {
        this.see_also = see_also;
    }

    public ArrayList<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(ArrayList<String> antonyms) {
        this.antonyms = antonyms;
    }

    public ArrayList<Source> getSource() {
        return source;
    }

    public void setSource(ArrayList<Source> source) {
        this.source = source;
    }

    public ArrayList<String> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<String> info) {
        this.info = info;
    }
}
