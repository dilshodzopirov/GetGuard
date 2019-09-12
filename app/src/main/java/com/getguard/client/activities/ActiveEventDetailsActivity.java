package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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

public class ActiveEventDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer, executorContainer, messageContainer,
    callContainer, supportContainer;
    private TextView errorText;
    private Button errorBtn;
    private TextView eventText, addressText, dateText, priceText, nameText, ratingText;
    private ImageView bgImg, guardImg;
    private RatingBar ratingBar;
    private CardView holder;

    private String id;
    private EventResponse.Data data;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_event_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        user = AppDatabase.getInstance(this).getUserDAO().getUser();

        id = getIntent().getStringExtra("id");

        getSupportActionBar().setTitle("Заявка");

        contentLayout = findViewById(R.id.content_layout);
        progressBar = findViewById(R.id.progress_bar);
        errorContainer = findViewById(R.id.error_container);
        errorText = findViewById(R.id.error_text);
        errorBtn = findViewById(R.id.error_btn);
        eventText = findViewById(R.id.event_text);
        addressText = findViewById(R.id.address_text);
        dateText = findViewById(R.id.date_text);
        priceText = findViewById(R.id.price_text);
        bgImg = findViewById(R.id.bg_img);
        guardImg = findViewById(R.id.guard_img);
        nameText = findViewById(R.id.name_text);
        ratingText = findViewById(R.id.rating_text);
        ratingBar = findViewById(R.id.rating_bar);
        executorContainer = findViewById(R.id.executor_container);
        messageContainer = findViewById(R.id.message_container);
        callContainer = findViewById(R.id.call_container);
        supportContainer = findViewById(R.id.support_container);
        closeContainer = findViewById(R.id.close_container);
        holder = findViewById(R.id.holder);

        holder.setOnClickListener(v -> {
            Intent intent = new Intent(ActiveEventDetailsActivity.this, EventDetailsActivity.class);
            intent.putExtra("id", data.getId());
            intent.putExtra("viewState", EventDetailsActivity.ViewState.details);
            startActivity(intent);
        });

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
        NetworkManager.getInstance(this).getEvent(user.getToken(), id, (errorMessage, data) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {
                this.data = data;

                EventType eventType = Consts.eventTypeMap.get(data.getEventType());
                if (eventType != null) {
                    eventText.setText(eventType.getName());
                    addressText.setText(data.getAddress());
                    dateText.setText(Utils.dateFormat(data.getStartDate()) + " - " + Utils.dateFormat(data.getEndDate()));
                    priceText.setText(String.valueOf(data.getRatePrice()));
                    Glide.with(ActiveEventDetailsActivity.this)
                            .load(Config.BASE_URL + eventType.getUrl())
                            .apply(new RequestOptions().centerCrop())
                            .into(bgImg);
                }

                EventResponse.Executor executor = data.getExecutor();
                if (executor != null) {
                    if (executor.getPhoto() != null) {
                        Glide.with(this)
                                .load(Config.BASE_URL + "api/Upload/" + executor.getPhoto().getId())
                                .apply(new RequestOptions().centerCrop())
                                .into(guardImg);
                    }
                    nameText.setText(executor.getUserName());
                    ratingText.setText(executor.getRating() + ", " + executor.getUserRatingCount() + "Оценки");
                    ratingBar.setRating(executor.getRating());

                    executorContainer.setOnClickListener(v -> {
                        Intent intent = new Intent(ActiveEventDetailsActivity.this, UserDetailsActivity.class);
                        intent.putExtra("id", executor.getId());
                        intent.putExtra("eventId", id);
                        startActivity(intent);
                    });

                    callContainer.setOnClickListener(v -> {
//                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                        callIntent.setData(Uri.parse("tel:" + data.get));//change the number
//                        startActivity(callIntent);
                    });

                }
                showContent();


            }
        });
    }

}
