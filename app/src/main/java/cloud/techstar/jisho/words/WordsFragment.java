package cloud.techstar.jisho.words;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.Injection;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.database.Word;
import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.detail.DetailActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class WordsFragment extends Fragment implements WordsContract.View{

    private MaterialSearchBar searchBar;
    private WordSuggestionsAdapter wordSuggestionsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout = null;
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
        mAdapter = new WordsAdapter(new ArrayList<Words>(0));

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
        swipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadWords(false);
            }
        });
        wordSuggestionsAdapter = new WordSuggestionsAdapter(inflater);
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
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) getView().findViewById(R.id.swipe_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showWords(List<Words> words) {
        mAdapter.replaceData(words);
    }

    @Override
    public void showWordDetail(String wordId) {
        Intent intent = new Intent(AppMain.getContext(), DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("word_id", wordId);
        AppMain.getContext().startActivity(intent);
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


    private class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
        private List<Words> words;

        public WordsAdapter(List<Words> words) {
            this.words = words;
        }

        public void replaceData(List<Words> words) {
            setList(words);
            notifyDataSetChanged();
        }

        private void setList(List<Words> words) {
            this.words = checkNotNull(words);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView kanjiText;
            private TextView characterText;
            private TextView meaningText;
            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                kanjiText = v.findViewById(R.id.kanji_text);
                characterText = v.findViewById(R.id.character_text);
                meaningText = v.findViewById(R.id.meaning_text);
            }

            @Override
            public void onClick(View view) {
                presenter.openWordDetails(words.get(this.getAdapterPosition()));
            }
        }
        @Override
        public WordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_words, parent, false);
            WordsAdapter.ViewHolder vh = new WordsAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final WordsAdapter.ViewHolder holder, final int position) {
            holder.kanjiText.setText(words.get(position).getKanji());
            holder.characterText.setText(words.get(position).getCharacter());
            holder.meaningText.setText(words.get(position).getMeaning());
        }

        @Override
        public int getItemCount() {
            return words.size();
        }
    }
}
