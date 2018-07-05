package cloud.techstar.memorize.words;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.detail.DetailActivity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class WordsFragment extends Fragment implements WordsContract.View{

    private MaterialSearchBar searchBar;
    private WordSuggestionsAdapter wordSuggestionsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout = null;
    private WordsPresenter wordsPresenter;
    private Spinner viewSpinner, sortSpinner;

    private WordsContract.Presenter presenter;
//    private Word

    private WordsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public WordsFragment() {
    }

    public static android.app.Fragment newInstance() {
        WordsFragment fragment = new WordsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new WordsAdapter(new ArrayList<Words>(0), R.layout.item_word_list);

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
        searchBar.setSpeechMode(true);
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

        mRecyclerView = root.findViewById(R.id.word_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        sortSpinner = root.findViewById(R.id.sort);
        viewSpinner = root.findViewById(R.id.view);

        List<String> sorts = new LinkedList<>(Arrays.asList("Active", "All", "Recently"));

        List<String> views = new LinkedList<>(Arrays.asList("List", "Grid", "Card"));

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(AppMain.getContext(),
                R.layout.spinner_item, sorts);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> viewAdapter = new ArrayAdapter<String>(AppMain.getContext(),
                R.layout.spinner_item, views);
        viewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortSpinner.setAdapter(sortAdapter);
        viewSpinner.setAdapter(viewAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                com.orhanobut.logger.Logger.e("Selected position :"+position);
                switch (position){
                    case 0 :
                        presenter.setFilterType(WordFilterType.ACTIVE_WORDS);
                        break;
                    case 1 :
                        presenter.setFilterType(WordFilterType.ALL_WORDS);
                        break;
                    case 2 :
                        presenter.setFilterType(WordFilterType.RECENTLY);
                        break;
                    default:
                        presenter.setFilterType(WordFilterType.ACTIVE_WORDS);
                        break;
                }
                presenter.loadWords(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mAdapter = new WordsAdapter(new ArrayList<Words>(0), R.layout.item_word_list);
                        mLayoutManager = new LinearLayoutManager(AppMain.getContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 1:
                        mAdapter = new WordsAdapter(new ArrayList<Words>(0), R.layout.item_word_grid);
                        mLayoutManager = new GridLayoutManager(AppMain.getContext(), 2);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        break;
                    case 2:
                        presenter.setViewType(WordViewType.CARD);
                        break;
                    default:
                        presenter.setViewType(WordViewType.LIST);
                        break;
                }
                presenter.loadWords(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    @Override
    public void setPresenter(WordsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
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
    public void setSuggest(List<Words> words) {
//        wordSuggestionsAdapter.setSuggestions(words);
    }

    @Override
    public boolean isActive() {
        return false;
    }


    private class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
        private List<Words> words;
        private final int resource;

        public WordsAdapter(List<Words> words, int resource) {
            this.words = words;
            this.resource = resource;
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
            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                kanjiText = v.findViewById(R.id.kanji_text);
                characterText = v.findViewById(R.id.character_text);
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
                    .inflate(resource, parent, false);
            WordsAdapter.ViewHolder vh = new WordsAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final WordsAdapter.ViewHolder holder, final int position) {
            holder.kanjiText.setText(words.get(position).getKanji());
            holder.characterText.setText(words.get(position).getCharacter());
        }

        @Override
        public int getItemCount() {
            return words.size();
        }
    }
}
