package cloud.techstar.memorize;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import cloud.techstar.jisho.R;
import cloud.techstar.memorize.favorite.FavoriteFragment;
import cloud.techstar.memorize.options.OptionsFragment;
import cloud.techstar.memorize.words.WordsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setFragment(WordsFragment.newInstance());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    setFragment(WordsFragment.newInstance());
                    return true;
                case R.id.navigation_favorite:
                    setFragment(FavoriteFragment.newInstance());
                    return true;
                case R.id.navigation_options:
                    setFragment(OptionsFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment); // newInstance() is a static factory method.
        transaction.commit();
    }
}
