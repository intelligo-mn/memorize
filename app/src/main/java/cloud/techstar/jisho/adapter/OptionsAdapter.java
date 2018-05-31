package cloud.techstar.jisho.adapter;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.activity.ActivityDetail;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private final Activity context;
    private final String[] titleId;
    private final String[] subtitleId;
    private final Integer[] imageId;

    public OptionsAdapter(Activity context, String[] titleId, String[] subtitleId, Integer[] imageId) {
        this.context = context;
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
            title = (TextView) v.findViewById(R.id.title);
            subtitle = (TextView) v.findViewById(R.id.subtitle);
            imageView = (ImageView) v.findViewById(R.id.image);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
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