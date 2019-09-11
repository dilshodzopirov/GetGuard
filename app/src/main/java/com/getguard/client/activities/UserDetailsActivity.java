package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.getguard.client.R;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer;
    private TextView errorText;
    private Button errorBtn;
    private TextView commentsText, nameText, ratingText, workExperienceText, infoText, birthdateText, weaponText;
    private TextView guardLicenceText, aboutText, driverLicenceText;
    private ImageView image;
    private AppCompatRatingBar ratingBar;

    private String id, eventId;
    private User user;
    private ProgressDialog progressDialog;

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

        eventId = getIntent().getStringExtra("eventId");
        id = getIntent().getStringExtra("id");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Подождите...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

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
        aboutText = findViewById(R.id.about_text);
        driverLicenceText = findViewById(R.id.driver_licence_text);

        if (eventId != null) {
            closeContainer.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Назначить")
                        .setMessage("Вы хотите назначить этот охранник?")
                        .setPositiveButton("Да", (dialog, which) -> {
                            hire();
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
            });
        } else {
            closeContainer.setVisibility(View.GONE);
        }

        errorBtn.setOnClickListener(v -> {
            showProgress();
            getInfo();
        });

        getInfo();

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

    private void getInfo() {
        NetworkManager.getInstance(this).getUserById(user.getToken(), id, (errorMessage, data) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {

                if (data.getPhoto() != null) {
                    Glide.with(this)
                            .load(Config.BASE_URL + "api/Upload/" + data.getPhoto().getId())
                            .apply(new RequestOptions().centerCrop())
                            .into(image);
                }

                nameText.setText(data.getUserName());
                ratingBar.setRating(data.getRating());
                ratingText.setText(data.getRating() + ", " + data.getUserRatingCount() + " Оценки");
                workExperienceText.setText(data.getExperience());
                guardLicenceText.setText(data.getHasPrivateGuardLicense() ? "Да" : "Нет");
                weaponText.setText(data.getWeapon() == 0 ? "Нет" : "Да");

                birthdateText.setText(getAge(data.getBirthDate()));
                infoText.setText("Семейное положение: " + data.getMaritalStatus() + "\n"
                        + (data.getAddress() == null ? "" : data.getAddress())
                        + "\nГражданство: " + data.getCitizenship());
                aboutText.setText(data.getAbout());
                driverLicenceText.setText(data.getDriverLicense());

                showContent();

            }
        });
    }

    private void hire() {
        progressDialog.show();
        NetworkManager.getInstance(this).hire(user.getToken(), eventId, id, (errorMessage, data) -> {
            progressDialog.hide();
            if (errorMessage != null) {
                Toast.makeText(UserDetailsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            if (data != null && data) {
                closeContainer.setVisibility(View.GONE);
            }
        });
    }

    private String getAge(String date) {

        if (date == null) return "";

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("d MMMM yyyy", new Locale("ru"));

        String dateToFormat = "";
        Date dateOfBirth;
        try {
            dateOfBirth = fromUser.parse(date);
            dateToFormat = myFormat.format(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(dateOfBirth);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        String yearString = " лет";

        if (ageS.endsWith("1")) yearString = " год";
        else if (ageS.endsWith("2") || ageS.endsWith("3") || ageS.endsWith("4"))
            yearString = " года";

        return ageS + yearString + " (родился " + dateToFormat + ")";
    }

}
