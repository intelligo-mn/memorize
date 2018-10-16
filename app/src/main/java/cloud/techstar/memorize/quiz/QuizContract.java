package cloud.techstar.memorize.quiz;

import cloud.techstar.memorize.BasePresenter;
import cloud.techstar.memorize.BaseView;
import cloud.techstar.memorize.database.Question;

public interface QuizContract {
    interface View extends BaseView<QuizContract.Presenter> {

        void showSuccess(Integer choice);

        void showWrongAnswer(Integer choice, Integer rightAnswer);

        void setNumberOfRightAnswer(Integer x);

        void enableClicks();

        void disableClicks();

        void setNumberOfWrongAnswer(Integer x);

        void updateQuestion(Question currentQuestion);

        io.reactivex.Observable<Integer> onAnswer();
    }

    interface Presenter extends BasePresenter {

        void nextQuestion();

        Question getCurrentQuestion();

        Boolean isRightAnswer(int indexAnswer);
    }
}
