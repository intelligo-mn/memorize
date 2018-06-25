package cloud.techstar.memorize.options;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.jisho.R;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.manage.ManageActivity;

public class OptionsFragment extends Fragment implements OptionsContract.View{

    String[] titleId;
    String[] subtitleId;

    Integer[] imageId = {
            R.drawable.ic_add_list,
            R.drawable.ic_memory,
            R.drawable.ic_history,
            R.drawable.ic_language,
            R.drawable.ic_cloud_download,
            R.drawable.ic_menu,
            R.drawable.ic_public
    };

    private SwipeRefreshLayout swipeRefreshLayout = null;
    private OptionsContract.Presenter presenter;

    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new OptionsPresenter(Injection.provideWordsRepository(AppMain.getContext()),
                this);

        presenter.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        titleId = getResources().getStringArray(R.array.title);
        subtitleId = getResources().getStringArray(R.array.subtitle);
        final RecyclerView mRecyclerView = rootView.findViewById(R.id.options_rv);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OptionsAdapter(titleId, subtitleId, imageId);
        mRecyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void setPresenter(OptionsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) getView().findViewById(R.id.options_swiper);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void manageWordShow() {
        startActivity(new Intent(AppMain.getContext(), ManageActivity.class));
    }

    @Override
    public void memorizeShow() {

    }

    @Override
    public void historyShow() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

        private final String[] titleId;
        private final String[] subtitleId;
        private final Integer[] imageId;

        public OptionsAdapter(String[] titleId, String[] subtitleId, Integer[] imageId) {
            this.titleId = titleId;
            this.subtitleId = subtitleId;
            this.imageId = imageId;
        }

        @NonNull
        @Override
        public OptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_navigations, parent, false);
            OptionsAdapter.ViewHolder vh = new OptionsAdapter.ViewHolder(v);
            return vh;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView title;
            private TextView subtitle;
            private ImageView imageView;
            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                title = v.findViewById(R.id.title);
                subtitle = v.findViewById(R.id.subtitle);
                imageView = v.findViewById(R.id.image);

            }

            @Override
            public void onClick(View view) {
                int position = this.getAdapterPosition();
                if (position == 0) {
                    manageWordShow();
                } else if (position == 4) {
                    presenter.downloadWordsRemote();
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull OptionsAdapter.ViewHolder holder, int position) {
            holder.title.setText(titleId[position]);
            holder.subtitle.setText(subtitleId[position]);
            holder.imageView.setImageResource(imageId[position]);
        }

        @Override
        public int getItemCount() {
            return titleId.length;
        }
    }
}
