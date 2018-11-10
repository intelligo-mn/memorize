package cloud.techstar.memorize.quiz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Question;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class QuizActivity extends AppCompatActivity implements QuizContract.View{

    private QuizContract.Presenter presenter;

    protected TextView question, quizTitle;

    protected CardView cardAnswer1;
    protected CardView cardAnswer2;
    protected CardView cardAnswer3;
    protected CardView cardAnswer4;

    protected TextView answer1;
    protected TextView answer2;
    protected TextView answer3;
    protected TextView answer4;

    protected ImageButton backBtn;
    protected ProgressBar quizProgress;
    private int progressBarValue = 0;
    private Handler handler;
    private Button tryButton;
    private ScrollView quizScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizTitle = findViewById(R.id.quiz_title);
        question = findViewById(R.id.question);
        cardAnswer1 = findViewById(R.id.card_answer_1);
        cardAnswer2 = findViewById(R.id.card_answer_2);
        cardAnswer3 = findViewById(R.id.card_answer_3);
        cardAnswer4 = findViewById(R.id.card_answer_4);
        answer1 = findViewById(R.id.answer_1);
        answer2 = findViewById(R.id.answer_2);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);
        backBtn = (ImageButton)findViewById(R.id.quiz_back);
        quizProgress = findViewById(R.id.quiz_progress);
        quizScroll = findViewById(R.id.quiz_scroll);
        tryButton = findViewById(R.id.quiz_again);
        tryButton.setVisibility(View.INVISIBLE);
        handler = new Handler(Looper.getMainLooper());
        new QuizPresenter(Injection.provideWordsRepository(AppMain.getContext()), this);
        presenter.init();
        question.setTextSize(50);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    CardView getCorrectCardView(int indexRightAnswer) {
        String rightAnswerTag = String.valueOf(indexRightAnswer);

        if (cardAnswer1.getTag().equals(rightAnswerTag)) {
            return cardAnswer1;

        } else if (cardAnswer2.getTag().equals(rightAnswerTag)) {
            return cardAnswer2;

        } else if (cardAnswer3.getTag().equals(rightAnswerTag)) {
            return cardAnswer3;

        } else if (cardAnswer4.getTag().equals(rightAnswerTag)) {
            return cardAnswer4;
        }

        return null;
    }

    TextView getCorrectTextView(int indexRightAnswer) {
        String rightAnswerTag = String.valueOf(indexRightAnswer);

        if (cardAnswer1.getTag().equals(rightAnswerTag)) {
            return answer1;

        } else if (cardAnswer2.getTag().equals(rightAnswerTag)) {
            return answer2;

        } else if (cardAnswer3.getTag().equals(rightAnswerTag)) {
            return answer3;

        } else if (cardAnswer4.getTag().equals(rightAnswerTag)) {
            return answer4;
        }

        return null;
    }
    @Override
    public void showSuccess(Integer choice) {
        getCorrectCardView(choice).setBackgroundColor(ContextCompat.getColor(this, R.color.chartGreen));
        getCorrectTextView(choice).setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void showWrongAnswer(Integer choice, Integer rightAnswer) {
        getCorrectCardView(choice).setBackgroundColor(ContextCompat.getColor(this, R.color.chartPink));
        getCorrectTextView(choice).setTextColor(ContextCompat.getColor(this, R.color.white));
        getCorrectCardView(rightAnswer).setBackgroundColor(ContextCompat.getColor(this, R.color.chartGreen));
        getCorrectTextView(rightAnswer).setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void setRightAndWrongAnswer(int right, int wrong) {

        int count = right + wrong;
        right = right * 4;
        wrong = wrong * 4;
        quizProgress.setProgress(right);
        quizProgress.setSecondaryProgress(right+wrong);
        quizTitle.setText("Асуулт "+count+" / 25");
    }

    @Override
    public void enableClicks() {
        cardAnswer1.setClickable(true);
        cardAnswer2.setClickable(true);
        cardAnswer3.setClickable(true);
        cardAnswer4.setClickable(true);
    }

    @Override
    public void disableClicks() {
        cardAnswer1.setClickable(false);
        cardAnswer2.setClickable(false);
        cardAnswer3.setClickable(false);
        cardAnswer4.setClickable(false);
    }

    @Override
    public void updateQuestion(Question currentQuestion) {
        enableClicks();

        cardAnswer1.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cardAnswer2.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cardAnswer3.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cardAnswer4.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        answer1.setTextColor(ContextCompat.getColor(this, R.color.intelligoBlack));
        answer2.setTextColor(ContextCompat.getColor(this, R.color.intelligoBlack));
        answer3.setTextColor(ContextCompat.getColor(this, R.color.intelligoBlack));
        answer4.setTextColor(ContextCompat.getColor(this, R.color.intelligoBlack));

        question.setText(currentQuestion.getQuestion());
        answer1.setText(currentQuestion.getPossiblesAnswers().get(0));
        answer2.setText(currentQuestion.getPossiblesAnswers().get(1));
        answer3.setText(currentQuestion.getPossiblesAnswers().get(2));
        answer4.setText(currentQuestion.getPossiblesAnswers().get(3));

        int duration = 500;
    }

    @Override
    public void tryAgain(int score){
        quizScroll.setVisibility(View.INVISIBLE);
        tryButton.setVisibility(View.VISIBLE);
        question.setText("Зөв хариулсан "+score+" / 25");
    }

    @Override
    public Observable<Integer> onAnswer() {
        return Observable.merge(
                onOneAnswer(cardAnswer1),
                onOneAnswer(cardAnswer2),
                onOneAnswer(cardAnswer3),
                onOneAnswer(cardAnswer4));
    }

    private Observable<Integer> onOneAnswer(final View view) {
        return RxView.clicks(view)
                .map(new Function<Object, Integer>() {
                    @Override
                    public Integer apply(@NonNull Object object) throws Exception {
                        return Integer.parseInt((String) view.getTag());
                    }
                });
    }

    @Override
    public void setPresenter(QuizContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {

    }
}
