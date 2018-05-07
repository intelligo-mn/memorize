package cloud.techstar.jisho.fragments;

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
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.adapter.WordListAdapter;
import cloud.techstar.jisho.adapter.WordSuggestionsAdapter;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class SearchFragment extends Fragment {

    private MaterialSearchBar searchBar;
    private WordSuggestionsAdapter wordSuggestionsAdapter;

    public static android.app.Fragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        searchBar = (MaterialSearchBar) root.findViewById(R.id.searchBar);
        wordSuggestionsAdapter = new WordSuggestionsAdapter(inflater);
        WordTable wordTable = new WordTable();
        Handler mHandler = new Handler(Looper.getMainLooper());
        List<Words> words = wordTable.selectAll();
        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find words..");

        wordSuggestionsAdapter.setSuggestions(words);
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
        RecyclerView.Adapter mAdapter = new WordListAdapter(AppMain.getContext(), words);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }
}
