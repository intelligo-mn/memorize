package cloud.techstar.jisho.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.database.Words;

public class DetailActivity extends AppCompatActivity {

    private WordTable wordTable;
    private Words word;
    private ImageButton favBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        wordTable = new WordTable();
        word = wordTable.select(intent.getStringExtra("word_id"));

        TextView headKanji = (TextView) findViewById(R.id.header_kanji);
        TextView headHiragana = (TextView) findViewById(R.id.header_hiragana);
        TextView meaning = (TextView) findViewById(R.id.detail_meaning);
        TextView meaningMn = (TextView) findViewById(R.id.detail_meaning_mn);
        TextView partOfSpeech = (TextView) findViewById(R.id.detail_part_of);
        TextView level = (TextView) findViewById(R.id.detail_level);
        TextView kanji = (TextView) findViewById(R.id.detail_kanji);

        ImageButton backBtn = findViewById(R.id.back);
        favBtn = findViewById(R.id.btnFav);

        headKanji.setText(word.getKanji());
        headHiragana.setText(word.getCharacter());
        meaning.setText("\u2022 ".concat(word.getMeaning()));
        meaningMn.setText("\u2022 ".concat(word.getMeaningMon()));
        partOfSpeech.setText(word.getPartOfSpeech());
        level.setText(word.getLevel());
        kanji.setText(word.getKanji());
        Logger.e(word.toString());

        if (word.getIsFavorite().equals("true"))
            favBtn.setImageResource(R.drawable.ic_favorite_full);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (word.getIsFavorite().equals("false")) {
                    word.setIsFavorite("true");
                    wordTable.update(word);
                    favBtn.setImageResource(R.drawable.ic_favorite_full);
                } else {
                    word.setIsFavorite("false");
                    wordTable.update(word);
                    favBtn.setImageResource(R.drawable.ic_favorite);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
