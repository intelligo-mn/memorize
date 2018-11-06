package cloud.techstar.memorize.detail;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.MainActivity;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.database.Words;

public class DetailActivity extends AppCompatActivity implements DetailContract.View{

    private ImageButton favBtn;
    private LinearLayout meaningLayout;
    private RelativeLayout tagsLayout;
    private TextView headKanji;
    private TextView headHiragana;
    private TextView kanji;

    private DetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        meaningLayout = findViewById(R.id.meanings_layout);
        tagsLayout = findViewById(R.id.tags_layout);

        headKanji = (TextView) findViewById(R.id.header_kanji);
        headHiragana = (TextView) findViewById(R.id.header_hiragana);
        kanji = (TextView) findViewById(R.id.detail_kanji);

        ImageButton backBtn = findViewById(R.id.back);
        favBtn = findViewById(R.id.btnFav);
        Intent intent = getIntent();

        new DetailPresenter((Words) intent.getSerializableExtra("word_detail"),
                Injection.provideWordsRepository(getApplicationContext()),
                this);

        presenter.init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.favoriteWord();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMissingWord() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void setData(Words word) {
        try {
            headKanji.setText(word.getKanji());
            headHiragana.setText(word.getCharacter());
            for (int i=0; i < word.getMeaning().size(); i++){
                TextView text = new TextView(this);
                text.setText(word.getMeaning().get(i)); // <-- does it really compile without the + sign?
                text.setTextSize(20);
                text.setMaxLines(2);
                text.setGravity(Gravity.START);
                text.setLayoutParams(new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                meaningLayout.addView(text);
            }

            assert word.getMeaningMon() != null;
            if (word.getMeaningMon().size() > 0){
                for (int i=0; i < word.getMeaningMon().size(); i++){
                    TextView text = new TextView(this);
                    text.setText(word.getMeaningMon().get(i)); // <-- does it really compile without the + sign?
                    text.setTextSize(20);
                    text.setMaxLines(2);
                    text.setGravity(Gravity.START);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    meaningLayout.addView(text);
                }
            } else {
                AppCompatButton newBtn = new AppCompatButton(this);
                newBtn.setText(getString(R.string.mongolian_meaning));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                meaningLayout.addView(newBtn, params);

                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createDialog();
                    }
                });

            }

            assert word.getPartOfSpeech() != null;
            for (int i = 0; i < word.getPartOfSpeech().size(); i++){
                TextView text = new TextView(this);
                text.setText(word.getPartOfSpeech().get(i)); // <-- does it really compile without the + sign?
                text.setTextSize(15);
                text.setPadding(5,5,5,5);
                text.setGravity(Gravity.START);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text.setTextColor(getColor(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    text.setBackground(getDrawable(R.drawable.ic_round_rectangle));
                }
                text.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                tagsLayout.addView(text);
            }

            assert word.getLevel() != null;
            for (int i = 0; i < word.getLevel().size(); i++){
                TextView text = new TextView(this);
                text.setText(word.getLevel().get(i)); // <-- does it really compile without the + sign?
                text.setTextSize(15);
                text.setPadding(5,5,5,5);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    text.setTextColor(getColor(R.color.white));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    text.setBackground(getDrawable(R.drawable.ic_round_rectangle));
                }
                text.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                tagsLayout.addView(text);
            }

            kanji.setText(word.getKanji());
            if (word.isFavorite())
                favBtn.setImageResource(R.drawable.ic_favorite_full);
        } catch (Exception ex){
            showToast("Алдаа :"+ex);
        }
    }
    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Монгол орчуулга нэмэх");
        LinearLayout layout  = new LinearLayout(DetailActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final AppCompatEditText meaningEditText = new AppCompatEditText(DetailActivity.this);
        meaningEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        meaningEditText.setHint("Орчуулга оруулна уу");
        layout.addView(meaningEditText);
        builder.setView(layout);

        builder.setPositiveButton("Хадгалах", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.addMeaning(meaningEditText.getText().toString());
            }
        });
        builder.setNegativeButton("Буцах", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    @Override
    public void showFavorite(boolean isFav) {
        if (isFav)
            favBtn.setImageResource(R.drawable.ic_favorite_full);
        else
            favBtn.setImageResource(R.drawable.ic_favorite);
    }

}
