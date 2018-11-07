package cloud.techstar.memorize.options;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.orhanobut.logger.Logger;

import java.util.List;

import cloud.techstar.imageloader.ImageLoader;
import cloud.techstar.memorize.AppMain;
import cloud.techstar.memorize.R;
import cloud.techstar.memorize.Injection;
import cloud.techstar.memorize.license.LicenseActivity;
import cloud.techstar.memorize.manage.ManageActivity;
import cloud.techstar.memorize.quiz.QuizActivity;
import cloud.techstar.memorize.statistic.StatisticActivity;
import cloud.techstar.progressbar.TSProgressBar;

public class OptionsFragment extends Fragment implements OptionsContract.View, View.OnClickListener {

    String[] titleId;
    String[] subtitleId;

    Integer[] imageId = {
            R.drawable.ic_add_list,
            R.drawable.ic_memory,
            R.drawable.ic_timeline,
            R.drawable.ic_language,
            R.drawable.ic_cloud_download,
            R.drawable.ic_cloud_upload,
            R.drawable.ic_menu,
            R.drawable.ic_public
    };

    private TextView userText;
    private ImageView userAvatar;
    private OptionsContract.Presenter presenter;
    private TSProgressBar prgLoading;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 901;

    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new OptionsPresenter(Injection.provideWordsRepository(AppMain.getContext()),
                this);
        presenter.init();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(getResources().getString(R.string.client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(AppMain.getContext(), gso);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options, container, false);

        prgLoading = (TSProgressBar)rootView.findViewById(R.id.options_progress);
        titleId = getResources().getStringArray(R.array.title);
        subtitleId = getResources().getStringArray(R.array.subtitle);

        userText = rootView.findViewById(R.id.user_name);
        userAvatar = rootView.findViewById(R.id.user_avatar);
        SignInButton signInButton = rootView.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        rootView.findViewById(R.id.sign_in_button).setOnClickListener(this);

        final RecyclerView mRecyclerView = rootView.findViewById(R.id.options_rv);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppMain.getContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OptionsAdapter(titleId, subtitleId, imageId);
        mRecyclerView.setAdapter(mAdapter);

        ImageLoader imageLoader = new ImageLoader(AppMain.getContext());

        // Inflate the layout for this fragment
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(AppMain.getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            userText.setText(personName);

            imageLoader.DisplayImage(personPhoto.toString(), userAvatar);
        }
        return rootView;
    }

    private void updateUI(GoogleSignInAccount account) {
        userText.setText(account.getDisplayName());
        userAvatar.setImageURI(account.getPhotoUrl());
    }

    @Override
    public void setPresenter(OptionsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(AppMain.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (active)
            prgLoading.setVisibility(View.VISIBLE);
        else
            prgLoading.setVisibility(View.GONE);
    }

    @Override
    public void manageWordShow() {
        startActivity(new Intent(AppMain.getContext(), ManageActivity.class));
    }

    @Override
    public void memorizeShow() {

    }

    @Override
    public void historyShow() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (Exception e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Logger.d("Error message: "+e.getMessage());
            userText.setText("Login Failed: ");
        }
    }

    public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

        private final String[] titleId;
        private final String[] subtitleId;
        private final Integer[] imageId;

        public OptionsAdapter(String[] titleId, String[] subtitleId, Integer[] imageId) {
            this.titleId = titleId;
            this.subtitleId = subtitleId;
            this.imageId = imageId;
        }

        @NonNull
        @Override
        public OptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_navigations, parent, false);
            OptionsAdapter.ViewHolder vh = new OptionsAdapter.ViewHolder(v);
            return vh;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView title;
            private TextView subtitle;
            private ImageView imageView;
            private ViewHolder(View v) {
                super(v);
                v.setOnClickListener(this);
                title = v.findViewById(R.id.title);
                subtitle = v.findViewById(R.id.subtitle);
                imageView = v.findViewById(R.id.image);

            }

            @Override
            public void onClick(View view) {
                int position = this.getAdapterPosition();
                if (position == 0) {
                    manageWordShow();
                } else if (position == 2) {
                    startActivity(new Intent(AppMain.getContext(), StatisticActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(AppMain.getContext(), QuizActivity.class));
                } else if (position == 4) {
                    presenter.downloadWordsRemote();
                } else if (position == 5) {
                    presenter.sendWordsRemote();
                } else if (position == 7) {
                    startActivity(new Intent(AppMain.getContext(), LicenseActivity.class));
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull OptionsAdapter.ViewHolder holder, int position) {
            holder.title.setText(titleId[position]);
            holder.subtitle.setText(subtitleId[position]);
            holder.imageView.setImageResource(imageId[position]);
        }

        @Override
        public int getItemCount() {
            return titleId.length;
        }
    }
}
