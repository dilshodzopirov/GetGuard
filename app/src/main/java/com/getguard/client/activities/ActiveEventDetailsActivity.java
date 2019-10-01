package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActiveEventDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer, executorContainer, messageContainer,
            callContainer, supportContainer, dateInfoLayout;
    private TextView errorText;
    private Button errorBtn;
    private TextView eventText, addressText, dateText, priceText, nameText, ratingText, titleText, closeText,
            startInfoText, timeLeftInfoText, timeStatusInfoText;
    private ImageView bgImg, guardImg;
    private RatingBar ratingBar;
    private CardView holder;

    private String id;
    private EventResponse.Data data;
    private User user;

    private ViewState viewState;
    private ProgressDialog progressDialog;

    public enum ViewState {
        clientInfo, guardMy, guardActive;
    }

    public static int STARTED = 1111;
    public static int ENDED = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_event_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Подождите...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        user = AppDatabase.getInstance(this).getUserDAO().getUser();

        id = getIntent().getStringExtra("id");

        getSupportActionBar().setTitle("Заявка");
        viewState = (ViewState) getIntent().getSerializableExtra("viewState");

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
        closeText = findViewById(R.id.close_text);
        holder = findViewById(R.id.holder);
        titleText = findViewById(R.id.title_text);
        dateInfoLayout = findViewById(R.id.date_info_layout);
        startInfoText = findViewById(R.id.start_info_text);
        timeLeftInfoText = findViewById(R.id.time_left_info_text);
        timeStatusInfoText = findViewById(R.id.time_status_info_text);

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

        switch (viewState) {
            case clientInfo:
                break;
            case guardMy:
                break;
            case guardActive:
                supportContainer.setVisibility(View.GONE);
                dateInfoLayout.setVisibility(View.VISIBLE);
                break;
        }

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

    private void setData() {

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

        if (viewState == ViewState.guardActive || viewState == ViewState.guardMy) {
            titleText.setText("Заказчик");
            ratingBar.setVisibility(View.GONE);
            ratingText.setVisibility(View.GONE);

            EventResponse.Creator creator = data.getCreator();
            if (creator != null) {
                if (creator.getPhoto() != null) {
                    Glide.with(this)
                            .load(Config.BASE_URL + "api/Upload/" + creator.getPhoto().getId())
                            .apply(new RequestOptions().centerCrop())
                            .into(guardImg);
                }
                nameText.setText(creator.getUserName());

                callContainer.setOnClickListener(v -> {
                    //                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    //                        callIntent.setData(Uri.parse("tel:" + data.get));//change the number
                    //                        startActivity(callIntent);
                });

            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", new Locale("ru"));
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", new Locale("ru"));

            if (data.getStartDate() != null && data.getEndDate() != null) {
                try {
                    Date startDate = simpleDateFormat.parse(data.getStartDate());
                    Date endDate = simpleDateFormat.parse(data.getEndDate());
                    Date currentDate = new Date();
                    if (startDate.getTime() > currentDate.getTime()) {
                        timeLeftInfoText.setText("ДО НАЧАЛА СМЕНЫ ОСТАНОСЬ:");
                        long secs = (startDate.getTime() - currentDate.getTime()) / 1000;
                        int hours = (int) secs / 3600;
                        secs = secs % 3600;
                        int mins = (int) secs / 60;
                        secs = secs % 60;
                        timeStatusInfoText.setText(getHourText(hours) + " " + getMinutesText(mins));
                        closeText.setText("Начать смену");
                        closeContainer.setOnClickListener(v -> {
                            start();
                        });
                    } else {
                        startInfoText.setVisibility(View.VISIBLE);
                        startInfoText.setText("Фиксация начала смены: " + displayFormat.format(data.getStartDate()));
                        timeLeftInfoText.setText("ДО КОНЦА СМЕНЫ ОСТАНОСЬ:");
                        if ((endDate.getTime() > currentDate.getTime())) {
                            long secs = (endDate.getTime() - currentDate.getTime()) / 1000;
                            int hours = (int) secs / 3600;
                            secs = secs % 3600;
                            int mins = (int) secs / 60;
                            secs = secs % 60;
                            timeStatusInfoText.setText(getHourText(hours) + " " + getMinutesText(mins));
                        } else {
                            timeStatusInfoText.setText("смена окончена");
                        }
                        closeText.setText("Завершить смену");
                        closeContainer.setOnClickListener(v -> {
                            end();
                        });
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
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
        }


    }

    private String getHourText(int hours) {
        int num = hours;
        if (num > 15) num %= 10;
        if (num == 1) return hours + " час";
        else if (num == 2 || num == 3 || num == 4) return hours + " часа";
        else return hours + " часов";
    }

    private String getMinutesText(int minutes) {
        int num = minutes;
        if (num > 15) num %= 10;
        if (num == 1) return minutes + " минута";
        else if (num == 2 || num == 3 || num == 4) return minutes + " минуты";
        else return minutes + " минут";
    }

    private void getEvent() {
        NetworkManager.getInstance(this).getEvent(user.getToken(), id, (errorMessage, data) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {
                this.data = data;
                setData();
                showContent();

            }
        });
    }

    private void start() {
        progressDialog.show();
        NetworkManager.getInstance(this).start(user.getToken(), id, (errorMessage, data) -> {
            progressDialog.dismiss();
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void end() {
        progressDialog.show();
        NetworkManager.getInstance(this).end(user.getToken(), id, (errorMessage, data) -> {
            progressDialog.dismiss();
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (data != null) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

}
