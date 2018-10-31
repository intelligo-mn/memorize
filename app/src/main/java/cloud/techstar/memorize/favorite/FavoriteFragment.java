package cloud.techstar.memorize.favorite;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
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
    private Animation animation;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FavoriteAdapter(new ArrayList<Words>(0));
        mAdapter.notifyDataSetChanged();
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

        mRecyclerView = root.findViewById(R.id.fav_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(AppMain.getContext(), 1));
        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.fav_swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadWords(false);
            }
        });
//        animation = AnimationUtils.loadAnimation(AppMain.getContext(), R.anim.card_in);

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
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) getView().findViewById(R.id.fav_swiper);

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
    public void showMemorize(boolean isFav) {

    }

    public void refreshRecycler(){
        mAdapter = new FavoriteAdapter(new ArrayList<Words>(0));
        mRecyclerView.setAdapter(mAdapter);
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
            private TextView kanjiText;
            private TextView meaningText;
            private TextView meaningMnText;
            private ImageButton btnMemory;

            private boolean mIsBackVisible = false;

            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                mCardBackLayout = v.findViewById(R.id.card_back);
                mCardFrontLayout = v.findViewById(R.id.card_front);
                characterText = v.findViewById(R.id.fav_character_text);
                kanjiText = v.findViewById(R.id.fav_kanji_text);
                meaningText = v.findViewById(R.id.fav_meaning_text);
                meaningMnText = v.findViewById(R.id.fav_meaning_mn_text);
                btnMemory = v.findViewById(R.id.btnMemory);
                mCardFrontLayout.setVisibility(View.VISIBLE);
                mCardBackLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onClick(View view) {

                AnimatorSet cardLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(AppMain.getContext(),
                        R.animator.flip_left_in);

                if (!mIsBackVisible) {
                    mCardFrontLayout.setVisibility(View.INVISIBLE);
                    mCardBackLayout.setVisibility(View.VISIBLE);
                    cardLeftIn.setTarget(mCardBackLayout);
                    cardLeftIn.start();
                    mIsBackVisible = true;
                } else {
                    mCardBackLayout.setVisibility(View.INVISIBLE);
                    mCardFrontLayout.setVisibility(View.VISIBLE);
                    cardLeftIn.setTarget(mCardFrontLayout);
                    cardLeftIn.start();
                    mIsBackVisible = false;
                }
            }
        }

        @Override
        public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flash_card, parent, false);

            return new FavoriteAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final FavoriteAdapter.ViewHolder holder, final int position) {
            holder.characterText.setText(words.get(position).getCharacter());
            holder.kanjiText.setText(words.get(position).getKanji());
            holder.meaningText.setText(words.get(position).getMeaning().get(0));
//            holder.meaningMnText.setText(words.get(position).getMeaningMon().get(0));

            holder.btnMemory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.memorizeWord(words.get(position).getId());
                    refreshRecycler();
                    presenter.loadWords(false);
                }
            });
        }

        @Override
        public int getItemCount() {
            return words.size();
        }
    }
}
