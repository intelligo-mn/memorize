package cloud.techstar.memorize.favorite;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;
import cloud.techstar.memorize.detail.DetailActivity;

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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(AppMain.getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
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
            private View mCardFrontLayout;
            private View mCardBackLayout;
            private TextView characterText;
            private TextView meaningText;
            private TextView meaningMnText;

            private boolean mIsBackVisible = false;

            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                mCardBackLayout = v.findViewById(R.id.card_back);
                mCardFrontLayout = v.findViewById(R.id.card_front);
                characterText = v.findViewById(R.id.fav_character_text);
                meaningText = v.findViewById(R.id.fav_meaning_text);
                meaningMnText = v.findViewById(R.id.fav_meaning_mn_text);

                mCardFrontLayout.setVisibility(View.VISIBLE);
                mCardBackLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onClick(View view) {
//                presenter.openWordDetails(words.get(this.getAdapterPosition()));
                if (!mIsBackVisible) {
                    mCardFrontLayout.setVisibility(View.INVISIBLE);
                    mCardBackLayout.setVisibility(View.VISIBLE);
                    mIsBackVisible = true;
                } else {
                    mCardFrontLayout.setVisibility(View.VISIBLE);
                    mCardBackLayout.setVisibility(View.INVISIBLE);
                    mIsBackVisible = false;
                }
            }
        }

        @Override
        public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flash_card, parent, false);

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
