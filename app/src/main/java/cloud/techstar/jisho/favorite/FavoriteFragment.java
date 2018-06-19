package cloud.techstar.jisho.favorite;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.Injection;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.Words;
import cloud.techstar.jisho.detail.DetailActivity;
import cloud.techstar.jisho.words.WordsContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class FavoriteFragment extends Fragment implements FavoriteContract.View{

    private FavoritePresenter favoritePresenter;
    private FavoriteContract.Presenter presenter;
    private FavoriteAdapter mAdapter;
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FavoriteAdapter(new ArrayList<Words>(0));

        favoritePresenter = new FavoritePresenter(Injection.provideWordsRepository(AppMain.getContext()), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        final RecyclerView mRecyclerView = root.findViewById(R.id.fav_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.presenter = presenter;
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

    public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
        private List<Words> words;

        public FavoriteAdapter(List<Words> words) {
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
            private TextView characterText;
            private TextView meaningText;
            private TextView meaningMnText;
            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                characterText = v.findViewById(R.id.fav_character_text);
                meaningText = v.findViewById(R.id.fav_meaning_text);
                meaningMnText = v.findViewById(R.id.fav_meaning_mn_text);
            }

            @Override
            public void onClick(View view) {
                presenter.openWordDetails(words.get(this.getAdapterPosition()));
            }
        }

        @Override
        public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fav_recycler_item, parent, false);
            FavoriteAdapter.ViewHolder vh = new FavoriteAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
            holder.characterText.setText(words.get(position).getCharacter());
            holder.meaningText.setText(words.get(position).getMeaning());
            holder.meaningMnText.setText(words.get(position).getMeaningMon());
        }

        @Override
        public int getItemCount() {
            return words.size();
        }
    }
}
