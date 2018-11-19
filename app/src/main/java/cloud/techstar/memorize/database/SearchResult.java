package cloud.techstar.memorize.database;

public class SearchResult {
    private Words words;
    private boolean isRemote;

    public SearchResult(Words words, boolean isRemote) {
        this.words = words;
        this.isRemote = isRemote;
    }

    public Words getWords() {
        return words;
    }

    public void setWords(Words words) {
        this.words = words;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }
}
