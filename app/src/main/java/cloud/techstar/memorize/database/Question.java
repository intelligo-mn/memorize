package cloud.techstar.memorize.database;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String question;
    private List<String> possiblesAnswers;
    private int rightAnswerIndex;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getPossiblesAnswers() {
        return possiblesAnswers;
    }

    public void setPossiblesAnswers(List<String> possiblesAnswers) {
        this.possiblesAnswers = possiblesAnswers;
    }

    public int getRightAnswerIndex() {
        return rightAnswerIndex;
    }

    public void setRightAnswerIndex(int rightAnswerIndex) {
        this.rightAnswerIndex = rightAnswerIndex;
    }

    public Question(String question, List<String> possiblesAnswers, int rightAnswerIndex) {
        this.question = question;
        this.possiblesAnswers = possiblesAnswers;
        this.rightAnswerIndex = rightAnswerIndex;
    }
}
