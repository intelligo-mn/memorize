package cloud.techstar.memorize.database.entity;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String questionId;
    private String question;
    private List<String> possiblesAnswers;
    private int rightAnswerIndex;

    public Question(String questionId, String question, List<String> possiblesAnswers, int rightAnswerIndex) {
        this.questionId = questionId;
        this.question = question;
        this.possiblesAnswers = possiblesAnswers;
        this.rightAnswerIndex = rightAnswerIndex;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

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
}
