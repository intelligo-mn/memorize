package cloud.techstar.jisho.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.database.WordTable;
import cloud.techstar.jisho.models.Words;

public class ActivityDetail extends AppCompatActivity {

    private TextView headKanji;
    private TextView headHiragana;
    private TextView meaning;
    private TextView meaningMn;
    private TextView partOfSpeech;
    private TextView level;
    private TextView kanji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        headKanji = (TextView) findViewById(R.id.header_kanji);
        headHiragana = (TextView) findViewById(R.id.header_hiragana);
        meaning = (TextView) findViewById(R.id.detail_meaning);
        meaningMn = (TextView) findViewById(R.id.detail_meaning_mn);
        partOfSpeech = (TextView) findViewById(R.id.detail_part_of);
        level = (TextView) findViewById(R.id.detail_level);
        kanji = (TextView) findViewById(R.id.detail_kanji);

        setDetails();
    }

    private void setDetails(){
        Intent intent = getIntent();
        WordTable wordTable = new WordTable();
        Words words = wordTable.select(intent.getStringExtra("word_id"));

        headKanji.setText(words.getKanji());
        headHiragana.setText(words.getCharacter());
        meaning.setText(words.getMeaning());
        meaningMn.setText(words.getMeaningMon());
        partOfSpeech.setText(words.getPartOfSpeech());
        level.setText(words.getLevel());
        kanji.setText(words.getKanji());
    }
}
