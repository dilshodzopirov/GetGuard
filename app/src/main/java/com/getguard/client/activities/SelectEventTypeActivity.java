package com.getguard.client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.getguard.client.R;
import com.getguard.client.adapters.GuardTypeAdapter;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;

import java.util.ArrayList;

public class SelectEventTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout errorContainer;
    private TextView errorText, emptyText;
    private Button errorBtn;

    private GuardTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_event_type);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Выберите тип охраны");

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        errorContainer = findViewById(R.id.error_container);
        errorText = findViewById(R.id.error_text);
        errorBtn = findViewById(R.id.error_btn);
        emptyText = findViewById(R.id.empty_text);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GuardTypeAdapter(item -> {
            Intent intent = new Intent(SelectEventTypeActivity.this, NewEventActivity.class);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        errorBtn.setOnClickListener(v -> getEventTypes());

        getEventTypes();

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
        ArrayList<EventType> eventTypes = new ArrayList<>();
        for (EventType eventType : Consts.eventTypeMap.values()) {
            eventTypes.add(eventType);
        }
        adapter.setItems(eventTypes);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showProgress() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
    }

    private void showError(String message) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        if (message != null) {
            errorText.setText(message);
        }
    }

    private void getEventTypes() {
        showProgress();
        if (Consts.eventTypeMap.size() == 0) {
            NetworkManager.getInstance().getEventTypes(Config.TOKEN, (errorMessage, eventTypes) -> {
                if (errorMessage != null) {
                    showError(errorMessage);
                }

                if (eventTypes != null) {
                    Consts.eventTypeMap.clear();
                    for (EventType eventType : eventTypes) {
                        Consts.eventTypeMap.put(eventType.getType(), eventType);
                    }
                    showContent();
                }
            });
        } else {
            showContent();
        }
    }

}
