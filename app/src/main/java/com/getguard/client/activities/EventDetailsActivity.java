package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Utils;

public class EventDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer, executorContainer, messageContainer,
    callContainer, supportContainer;
    private TextView errorText;
    private Button errorBtn;
    private TextView eventText, addressText, dateText, priceText, nameText, ratingText;
    private ImageView bgImg, guardImg;
    private RatingBar ratingBar;

    private String id;
    private EventResponse.Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        id = getIntent().getStringExtra("id");

        getSupportActionBar().setTitle("Заявка Nº341588");

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
        NetworkManager.getInstance().getEvent(Config.TOKEN, id, (errorMessage, data) -> {
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
                    Glide.with(EventDetailsActivity.this)
                            .load(Config.BASE_URL + eventType.getUrl())
                            .apply(new RequestOptions().centerCrop())
                            .into(bgImg);
                }

                EventResponse.User executor = data.getExecutor();
                if (executor != null) {
                    nameText.setText(executor.getUserName());
                    ratingText.setText(executor.getRating() + ", " + executor.getUserRatingCount() + "Оценки");
                    ratingBar.setRating(executor.getRating());
                }
                showContent();

                executorContainer.setOnClickListener(v -> {
                    Intent intent = new Intent(EventDetailsActivity.this, ExecutorDetailsActivity.class);
                    intent.putExtra("event_id", data.getId());
                    intent.putExtra("executor_id", data.getExecutorId());
                    startActivity(intent);
                });

            }
        });
    }

}
