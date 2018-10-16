package cloud.techstar.memorize.quiz;

import android.database.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Question;
import cloud.techstar.memorize.options.OptionsContract;

public class QuizActivity extends AppCompatActivity implements QuizContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    @Override
    public void showSuccess(Integer choice) {

    }

    @Override
    public void showWrongAnswer(Integer choice, Integer rightAnswer) {

    }

    @Override
    public void setNumberOfRightAnswer(Integer x) {

    }

    @Override
    public void enableClicks() {

    }

    @Override
    public void disableClicks() {

    }

    @Override
    public void setNumberOfWrongAnswer(Integer x) {

    }

    @Override
    public void updateQuestion(Question currentQuestion) {

    }

    @Override
    public Observable<Integer> onAnswer() {
        return null;
    }

    @Override
    public void setPresenter(OptionsContract.Presenter presenter) {

    }

    @Override
    public void showToast(String message) {

    }
}
