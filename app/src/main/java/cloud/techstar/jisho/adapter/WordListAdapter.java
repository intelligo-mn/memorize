package cloud.techstar.jisho.adapter;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import cloud.techstar.jisho.AppMain;
import cloud.techstar.jisho.R;
import cloud.techstar.jisho.activity.ActivityDetail;
import cloud.techstar.jisho.activity.MainActivity;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private Context context;
    private List<Words> words;

    public WordListAdapter(Context context, List<Words> words) {
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
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("word_id", words.get(this.getAdapterPosition()).getId());
            context.startActivity(intent);
        }
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
    public void onBindViewHolder(final WordListAdapter.ViewHolder holder, final int position) {
        holder.kanjiText.setText(words.get(position).getKanji());
        holder.characterText.setText(words.get(position).getCharacter());
        holder.meaningText.setText(words.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}