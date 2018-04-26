package cloud.techstar.jisho.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private final Context context;
    private List<Words> words;
    private WordTable wordTable;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView characterText;
        private TextView meaningText;
        private TextView meaningMnText;
        private ImageButton favButton;
        private ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            wordTable = new WordTable();
            characterText = v.findViewById(R.id.character_text);
            meaningText = v.findViewById(R.id.meaning_text);
            meaningMnText = v.findViewById(R.id.meaning_mn_text);
            favButton = v.findViewById(R.id.fav_button);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public WordListAdapter(Context context, List<Words> words) {
        this.context = context;
        this.words = words;
    }

    @Override
    public WordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_recycler_item, parent, false);
        WordListAdapter.ViewHolder vh = new WordListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WordListAdapter.ViewHolder holder, final int position) {
        holder.characterText.setText(words.get(position).getCharacter());
        holder.meaningText.setText(words.get(position).getMeaning());
        holder.meaningMnText.setText(words.get(position).getMeaningMon());
        if (words.get(position).getIsMemorize() == "true") {
            holder.favButton.setImageResource(R.drawable.ic_favorite_full);
        }
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Words word = words.get(position);
                word.setIsMemorize("true");
                wordTable.update(word);
                Snackbar.make(v, "Added to favorite", Snackbar.LENGTH_LONG)
                        .setAction("Undo", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}