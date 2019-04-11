package systems.intelligo.memorize.quiz;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import systems.intelligo.memorize.database.entity.Question;
import systems.intelligo.memorize.database.entity.Words;
import systems.intelligo.memorize.database.WordsDataSource;
import systems.intelligo.memorize.database.WordsRepository;
import io.reactivex.functions.Consumer;

public class QuizPresenter implements QuizContract.Presenter {

    private final WordsDataSource wordsRepository;

    private final QuizContract.View quizView;

    private Integer currentIndexQuestion = 0;

    List<Question> quizWords;

    private int countWrongAnswer = 0;
    private int countRightAnswer = 0;

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
                            wordsRepository.memorizeWord(quizWords.get(indexAnswer).getQuestionId());
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

                            wordsRepository.favWord(quizWords.get(indexAnswer).getQuestionId());

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

    private List<Question> getQuizWords() {
        final List<Question> questions = new ArrayList<Question>();
        wordsRepository.selectWord("level", "jlpt-n5", new WordsDataSource.LoadWordsCallback() {
            @Override
            public void onWordsLoaded(List<Words> wordList) {
                List<Words> words = new ArrayList<>();
                for (Words word : wordList) {
                    if (!word.isMemorize())
                        words.add(word);

                }
                Collections.shuffle(words);
                for (int i = 0; i < 25; i++) {

                    Words currentWord = words.get(i);
                    List<String> possiblesAnswers = new ArrayList<>();

                    // Answers
                    for (int j = 0; j < 3; j++) {
                        int randomIndex = new Random().nextInt(words.size());
                        // We look for 3 wrong and different answers
                        while (possiblesAnswers.contains(words.get(randomIndex).getMeaning().get(0))
                                || words.get(randomIndex).getMeaning().get(0).equals(currentWord.getMeaning().get(0))) {
                            randomIndex = new Random().nextInt(words.size());
                        }

                        possiblesAnswers.add(words.get(randomIndex).getMeaning().get(0));

                    }

                    int rightIndexAnswer = new Random().nextInt(4);
                    String rightAnswer = currentWord.getMeaning().get(0);

                    possiblesAnswers.add(rightIndexAnswer, rightAnswer);

                    Question question = new Question(currentWord.getId(), currentWord.getKanji(), possiblesAnswers, rightIndexAnswer);
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
