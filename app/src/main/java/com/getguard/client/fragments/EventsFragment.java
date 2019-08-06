package com.getguard.client.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getguard.client.R;
import com.getguard.client.activities.EventDetailsActivity;
import com.getguard.client.adapters.GuardAdapter;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Config;
import com.getguard.client.utils.Consts;

public class EventsFragment extends Fragment {

    private static final String TAG = EventsFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout errorContainer;
    private TextView errorText, emptyText;
    private Button errorBtn;

    private GuardAdapter adapter;

    private int filter = 0;

    public static EventsFragment newInstance(int filter) {
        EventsFragment f = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt("filter", filter);
        f.setArguments(args);
        return f;
    }

    private EventsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filter = getArguments().getInt("filter");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        errorContainer = view.findViewById(R.id.error_container);
        errorText = view.findViewById(R.id.error_text);
        errorBtn = view.findViewById(R.id.error_btn);
        emptyText = view.findViewById(R.id.empty_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GuardAdapter(item -> {
            Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("id", item.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        errorBtn.setOnClickListener(v -> {
            getEventTypes();
        });

        getEventTypes();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void showContent() {
        if (adapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        }
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
                    getEvents();
                }
            });
        } else {
            getEvents();
        }
    }

    private void getEvents() {
        NetworkManager.getInstance().getEvents(Config.TOKEN, filter, (errorMessage, events) -> {
            if (errorMessage != null) {
                showError(errorMessage);
            }

            if (events != null) {
                Consts.events.addAll(events);
                adapter.setItems(events);
                showContent();
            }
        });
    }

}