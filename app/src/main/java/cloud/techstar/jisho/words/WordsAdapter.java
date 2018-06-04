package cloud.techstar.jisho.words;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.detail.DetailActivity;
import cloud.techstar.jisho.database.Words;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
    private Context context;
    private List<Words> words;

    public WordsAdapter(Context context, List<Words> words) {
        this.context = context;
        this.words = words;

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView kanjiText;
        private TextView characterText;
        private TextView meaningText;
        private ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            kanjiText = v.findViewById(R.id.kanji_text);
            characterText = v.findViewById(R.id.character_text);
            meaningText = v.findViewById(R.id.meaning_text);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("word_id", words.get(this.getAdapterPosition()).getId());
            context.startActivity(intent);
        }
    }
    @Override
    public WordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_words, parent, false);
        WordsAdapter.ViewHolder vh = new WordsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final WordsAdapter.ViewHolder holder, final int position) {
        holder.kanjiText.setText(words.get(position).getKanji());
        holder.characterText.setText(words.get(position).getCharacter());
        holder.meaningText.setText(words.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}