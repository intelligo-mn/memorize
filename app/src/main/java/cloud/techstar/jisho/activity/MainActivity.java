package cloud.techstar.jisho.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import cloud.techstar.jisho.R;
import cloud.techstar.jisho.fragments.FavoriteFragment;
import cloud.techstar.jisho.fragments.OptionsFragment;
import cloud.techstar.jisho.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, SearchFragment.newInstance()); // newInstance() is a static factory method.
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, SearchFragment.newInstance()); // newInstance() is a static factory method.
                    transaction.commit();
                    return true;
                case R.id.navigation_favorite:
                    FragmentManager newsManager = getFragmentManager();
                    FragmentTransaction transaction1 = newsManager.beginTransaction();
                    transaction1.replace(R.id.content, FavoriteFragment.newInstance()); // newInstance() is a static factory method.
                    transaction1.commit();
                    return true;
                case R.id.navigation_options:
                    FragmentManager projectManager = getFragmentManager();
                    FragmentTransaction transaction2 = projectManager.beginTransaction();
                    transaction2.replace(R.id.content, OptionsFragment.newInstance()); // newInstance() is a static factory method.
                    transaction2.commit();
                    return true;
            }
            return false;
        }

    };
}
