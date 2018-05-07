package cloud.techstar.jisho.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.models.Words;

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.ViewHolder> {
    private List<Words> words;

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

        }
    }

    public FavListAdapter(Context context, List<Words> words) {
        Context context1 = context;
        this.words = words;
    }

    @Override
    public FavListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_recycler_item, parent, false);
        FavListAdapter.ViewHolder vh = new FavListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavListAdapter.ViewHolder holder, int position) {
        holder.characterText.setText(words.get(position).getCharacter());
        holder.meaningText.setText(words.get(position).getMeaning());
        holder.meaningMnText.setText(words.get(position).getMeaningMon());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}