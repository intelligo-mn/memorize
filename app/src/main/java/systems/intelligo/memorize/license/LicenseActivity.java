package systems.intelligo.memorize.license;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import systems.intelligo.memorize.R;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);


        ImageButton backBtn = findViewById(R.id.back);

        backBtn.setOnClickListener(v -> finish());
    }
}
