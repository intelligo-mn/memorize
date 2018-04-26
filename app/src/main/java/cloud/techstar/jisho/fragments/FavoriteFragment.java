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
import cloud.techstar.jisho.adapter.FavListAdapter;
import cloud.techstar.jisho.adapter.WordListAdapter;
import cloud.techstar.jisho.adapter.WordSuggestionsAdapter;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class FavoriteFragment extends Fragment {
    private WordTable wordTable;
    private Handler mHandler;
    private List<Words> words = new ArrayList<>();

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        wordTable = new WordTable();
        mHandler = new Handler(Looper.getMainLooper());
        words = wordTable.selectFavorite();
        final RecyclerView mRecyclerView = root.findViewById(R.id.fav_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new FavListAdapter(AppMain.getContext(), words);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }
}
