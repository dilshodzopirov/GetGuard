package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getguard.client.R;
import com.getguard.client.adapters.UsersAdapter;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;
import com.getguard.client.utils.Utils;

public class MyEventDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout, errorContainer, closeContainer;
    private TextView errorText;
    private Button errorBtn;
    private TextView eventText, addressText, dateText, priceText, responseText;
    private ImageView bgImg;
    private RecyclerView recyclerView;
    private CardView holder;

    private String id;
    private EventResponse.Data data;
    private User user;
    private UsersAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        user = AppDatabase.getInstance(this).getUserDAO().getUser();

        id = getIntent().getStringExtra("id");

        getSupportActionBar().setTitle("Заявка");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Подождите...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        contentLayout = findViewById(R.id.content_layout);
        progressBar = findViewById(R.id.progress_bar);
        errorContainer = findViewById(R.id.error_container);
        errorText = findViewById(R.id.error_text);
        errorBtn = findViewById(R.id.error_btn);
        holder = findViewById(R.id.holder);
        eventText = findViewById(R.id.event_text);
        addressText = findViewById(R.id.address_text);
        dateText = findViewById(R.id.date_text);
        priceText = findViewById(R.id.price_text);
        bgImg = findViewById(R.id.bg_img);
        recyclerView = findViewById(R.id.recycler_view);
        closeContainer = findViewById(R.id.close_container);
        responseText = findViewById(R.id.response_text);

        closeContainer.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Удалить")
                    .setMessage("Вы хотите удалить эту заявку?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        deleteEvent();
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });

        errorBtn.setOnClickListener(v -> {
            showProgress();
            getEvent();
        });

        holder.setOnClickListener(v -> {
            Intent intent = new Intent(MyEventDetailsActivity.this, EventDetailsActivity.class);
            intent.putExtra("id", data.getId());
            if (user.isGuard()) {
                intent.putExtra("viewState", EventDetailsActivity.ViewState.unregister);
            } else {
                intent.putExtra("viewState", EventDetailsActivity.ViewState.details);
            }
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UsersAdapter(item -> {
            Intent intent = new Intent(MyEventDetailsActivity.this, UserDetailsActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("eventId", data.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

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
                    Glide.with(MyEventDetailsActivity.this)
                            .load(Config.BASE_URL + eventType.getUrl())
                            .apply(new RequestOptions().centerCrop())
                            .into(bgImg);
                }

                int responses = 0;
                if (data.getRespondedUsers() != null) responses = data.getRespondedUsers().size();
                responseText.setText(responses + " откликов");
                adapter.setItems(data.getRespondedUsers());


                showContent();

            }
        });
    }

    private void deleteEvent() {
        progressDialog.show();
        NetworkManager.getInstance(this).deleteEvent(user.getToken(), id, (errorMessage, data) -> {
            progressDialog.hide();
            if (errorMessage != null) {
                Toast.makeText(MyEventDetailsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            if (data != null) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

}
