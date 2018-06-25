package cloud.techstar.memorize.options;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.jisho.R;
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
    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options, container, false);


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
                    startActivity(new Intent(AppMain.getContext(), ManageActivity.class));
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
