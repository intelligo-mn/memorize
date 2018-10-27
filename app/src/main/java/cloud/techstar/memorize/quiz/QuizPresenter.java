package cloud.techstar.memorize.quiz;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cloud.techstar.memorize.database.Question;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.database.WordsDataSource;
import cloud.techstar.memorize.database.WordsRepository;
import io.reactivex.functions.Consumer;

public class QuizPresenter implements QuizContract.Presenter {

    private final WordsRepository wordsRepository;

    private final QuizContract.View quizView;

    public Integer currentIndexQuestion = 0;

    List<Question> quizWords;

    int countWrongAnswer = 0;
    int countRightAnswer = 0;

    public QuizPresenter(WordsRepository wordsRepository, QuizContract.View quizView) {
        this.wordsRepository = wordsRepository;
        this.quizView = quizView;
        quizView.setPresenter(this);
    }

    @Override
    public void nextQuestion() {
        currentIndexQuestion += 1;
        if (currentIndexQuestion == 25) {

            quizView.tryAgain(countRightAnswer);
            currentIndexQuestion = 0;
            return;
        }

        quizView.updateQuestion(getCurrentQuestion());
    }

    @Override
    public Question getCurrentQuestion() {
        return quizWords.get(currentIndexQuestion);
    }

    @Override
    public Boolean isRightAnswer(int indexAnswer) {
        return indexAnswer == getCurrentQuestion().getRightAnswerIndex();
    }

    @SuppressLint("CheckResult")
    @Override
    public void init() {
        quizWords = getQuizWords();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                quizView.updateQuestion(getCurrentQuestion());
            }
        }, 400);

        quizView.onAnswer()
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull Integer indexAnswer) throws Exception {
                    quizView.disableClicks();
                    Boolean isRight = isRightAnswer(indexAnswer);
                    if (isRight) {
                        countRightAnswer++;
                        quizView.showSuccess(indexAnswer);
                        quizView.setRightAndWrongAnswer(countRightAnswer, countWrongAnswer);


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextQuestion();
                            }
                        }, 500);

                    } else {
                        countWrongAnswer++;
                        quizView.showWrongAnswer(indexAnswer, getCurrentQuestion().getRightAnswerIndex());
                        quizView.setRightAndWrongAnswer(countRightAnswer, countWrongAnswer);
                        // 2 instead 1 seconds so the user can analyse the right answer
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextQuestion();
                            }
                        }, 1000);
                    }
                }
            });
    }

    private List<Question> getQuizWords(){
        final List<Question> questions = new ArrayList<Question>();
        wordsRepository.getWords(new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> words) {
                Collections.shuffle(words);
                for(int i  = 0; i < 25; i++) {

                    Words currentWord = words.get(i);
                    List<String> possiblesAnswers = new ArrayList<>();

                    // Answers
                    for(int j  = 0; j < 3; j++) {
                        int randomIndex = new Random().nextInt(words.size());
                        // We look for 3 wrong and different answers
                        while(possiblesAnswers.contains(words.get(randomIndex).getMeaningMon())
                                ||  words.get(randomIndex).getMeaningMon().equals(currentWord.getMeaningMon())) {
                            randomIndex = new Random().nextInt(words.size());
                        }

                        possiblesAnswers.add(words.get(randomIndex).getMeaningMon());

                    }

                    int rightIndexAnswer = new Random().nextInt(4);
                    String rightAnswer = currentWord.getMeaningMon();

                    possiblesAnswers.add(rightIndexAnswer, rightAnswer);

                    Question question = new Question(currentWord.getKanji(), possiblesAnswers, rightIndexAnswer);
                    questions.add(question);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return questions;
    }

}
