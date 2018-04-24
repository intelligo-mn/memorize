package cloud.techstar.jisho.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.adapter.WordListAdapter;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class SearchFragment extends Fragment {

    private WordTable wordTable;
    private Handler mHandler;
    List<Words> words;
    public static android.app.Fragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordTable = new WordTable();
        mHandler = new Handler(Looper.getMainLooper());
        words = wordTable.selectAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment

        final RecyclerView mRecyclerView = root.findViewById(R.id.word_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.Adapter mAdapter = new WordListAdapter(AppMain.getContext(), words);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }
}
