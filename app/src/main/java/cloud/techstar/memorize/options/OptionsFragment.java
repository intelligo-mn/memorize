package cloud.techstar.memorize.options;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cloud.techstar.imageloader.ImageLoader;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.license.LicenseActivity;
import cloud.techstar.memorize.quiz.QuizActivity;
import cloud.techstar.memorize.statistic.StatisticActivity;
import cloud.techstar.progressbar.TSProgressBar;

public class OptionsFragment extends Fragment implements OptionsContract.View {

    String[] titleId;
    String[] subtitleId;

    Integer[] imageId = {
            R.drawable.ic_add_list,
            R.drawable.ic_memory,
            R.drawable.ic_timeline,
            R.drawable.ic_language,
            R.drawable.ic_cloud_download,
            R.drawable.ic_cloud_upload,
            R.drawable.ic_menu,
            R.drawable.ic_public
    };

    private TextView userText;
    private ImageView userAvatar;
    private OptionsContract.Presenter presenter;
    private TSProgressBar prgLoading;
    private int RC_SIGN_IN = 901;

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

        prgLoading = (TSProgressBar)rootView.findViewById(R.id.options_progress);
        titleId = getResources().getStringArray(R.array.title);
        subtitleId = getResources().getStringArray(R.array.subtitle);

        userText = rootView.findViewById(R.id.user_name);
        userAvatar = rootView.findViewById(R.id.user_avatar);

        final RecyclerView mRecyclerView = rootView.findViewById(R.id.options_rv);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OptionsAdapter(titleId, subtitleId, imageId);
        mRecyclerView.setAdapter(mAdapter);

        ImageLoader imageLoader = new ImageLoader(AppMain.getContext());

        return rootView;
    }
    @Override
    public void setPresenter(OptionsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (active)
            prgLoading.setVisibility(View.VISIBLE);
        else
            prgLoading.setVisibility(View.GONE);
    }

    @Override
    public void manageWordShow() {

    }

    @Override
    public void memorizeShow() {

    }

    @Override
    public void historyShow() {

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
                } else if (position == 2) {
                    startActivity(new Intent(AppMain.getContext(), StatisticActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(AppMain.getContext(), QuizActivity.class));
                } else if (position == 4) {
                    presenter.downloadWordsRemote();
                } else if (position == 5) {
                    presenter.sendWordsRemote();
                } else if (position == 7) {
                    startActivity(new Intent(AppMain.getContext(), LicenseActivity.class));
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
