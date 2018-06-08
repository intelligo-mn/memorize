package cloud.techstar.jisho.words;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.Injection;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.database.Word;
import cloud.techstar.jisho.database.Words;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordsFragment extends Fragment implements WordsContract.View{


    private MaterialSearchBar searchBar;
    private WordSuggestionsAdapter wordSuggestionsAdapter;

    private WordsPresenter wordsPresenter;

    private WordsContract.Presenter presenter;

    WordsAdapter mAdapter;

    public WordsFragment() {
    }

    public static android.app.Fragment newInstance() {
        WordsFragment fragment = new WordsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new WordsAdapter(AppMain.getContext(), new ArrayList<Words>(0));

        wordsPresenter = new WordsPresenter(Injection.provideWordsRepository(AppMain.getContext()), this);

        wordsPresenter.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words, container, false);

        wordsPresenter = new WordsPresenter(Injection.provideWordsRepository(AppMain.getContext()), this);

        searchBar = (MaterialSearchBar) root.findViewById(R.id.searchBar);
        wordSuggestionsAdapter = new WordSuggestionsAdapter(inflater);
        WordTable wordTable = new WordTable();
        Handler mHandler = new Handler(Looper.getMainLooper());
        List<Word> words = wordTable.selectAll();
        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Хайх үгээ оруул..");
        searchBar.setCustomSuggestionAdapter(wordSuggestionsAdapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                wordSuggestionsAdapter.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        // Inflate the layout for this fragment

        final RecyclerView mRecyclerView = root.findViewById(R.id.word_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void setPresenter(WordsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showWords(List<Words> words) {
        mAdapter.replaceData(words);
    }

    @Override
    public void showWordDetail(String wordId) {

    }

    @Override
    public void showLoadingWordsError() {

    }

    @Override
    public void showNoWords() {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
