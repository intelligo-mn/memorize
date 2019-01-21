package cloud.techstar.memorize.quiz;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.entity.Question;

public interface QuizContract {
    interface View extends BaseView<QuizContract.Presenter> {

        void showSuccess(Integer choice);

        void showWrongAnswer(Integer choice, Integer rightAnswer);

        void setRightAndWrongAnswer(int right, int wrong);

        void enableClicks();

        void disableClicks();

        void updateQuestion(Question currentQuestion);

        void tryAgain(int score);

        io.reactivex.Observable<Integer> onAnswer();
    }

    interface Presenter extends BasePresenter {

        void nextQuestion();

        Question getCurrentQuestion();

        Boolean isRightAnswer(int indexAnswer);
    }
}
