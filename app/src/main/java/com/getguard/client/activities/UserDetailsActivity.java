package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Utils;

public class UserDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer;
    private TextView errorText;
    private Button errorBtn;
    private TextView commentsText, nameText, ratingText, workExperienceText, infoText, birthdateText, weaponText;
    private TextView guardLicenceText;
    private ImageView image;
    private AppCompatRatingBar ratingBar;

    private String id;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = AppDatabase.getInstance(this).getUserDAO().getUser();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Анкета исполнителя");

        id = getIntent().getStringExtra("id");

        commentsText = findViewById(R.id.comments_text);
        commentsText.setPaintFlags(commentsText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        contentLayout = findViewById(R.id.content_layout);
        progressBar = findViewById(R.id.progress_bar);
        errorContainer = findViewById(R.id.error_container);
        errorText = findViewById(R.id.error_text);
        errorBtn = findViewById(R.id.error_btn);
        closeContainer = findViewById(R.id.close_container);

        nameText = findViewById(R.id.name_text);
        ratingText = findViewById(R.id.rating_text);
        ratingBar = findViewById(R.id.rating_bar);
        image = findViewById(R.id.image);
        workExperienceText = findViewById(R.id.work_experience_text);
        infoText = findViewById(R.id.info_text);
        birthdateText = findViewById(R.id.birthdate_text);
        weaponText = findViewById(R.id.weapon_text);
        guardLicenceText = findViewById(R.id.guard_licence_text);

        errorBtn.setOnClickListener(v -> {
            showProgress();
            getEvent();
        });

        getEvent();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    private void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        contentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showError(String message) {
        contentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        if (message != null) {
            errorText.setText(message);
        }
    }

    private void getEvent() {
        NetworkManager.getInstance(this).getUserById(user.getToken(), id, (errorMessage, data) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {

                nameText.setText(data.getUserName());
                ratingText.setText(data.getRating() + ", " + data.getUserRatingCount() + "Оценки");
                workExperienceText.setText(data.getExperience());
                guardLicenceText.setText(data.getHasPrivateGuardLicense() ? "Да" : "Нет");
                weaponText.setText(data.getWeapon() == 1 ? "Да" : "Нет");
                infoText.setText(data.getMaritalStatus() + "\\n" + data.getAddress() + "\\nГражданство: " + data.getCitizenship());

                showContent();

            }
        });
    }

}
