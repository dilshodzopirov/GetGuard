package com.getguard.client.fragments;

import android.app.Activity;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.getguard.client.R;
import com.getguard.client.activities.ActiveEventDetailsActivity;
import com.getguard.client.activities.MainActivity;
import com.getguard.client.activities.MyEventDetailsActivity;
import com.getguard.client.activities.EventDetailsActivity;
import com.getguard.client.adapters.RequestAdapter;
import com.getguard.client.database.AppDatabase;
import com.getguard.client.database.User;
import com.getguard.client.models.network.EventType;
import com.getguard.client.network.NetworkManager;
import com.getguard.client.utils.Consts;

public class EventsFragment extends Fragment {

    private static final String TAG = EventsFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout errorContainer;
    private TextView errorText, emptyText;
    private Button errorBtn;
    private SwipeRefreshLayout swipeRefreshLayout;

    private User user;

    private RequestAdapter adapter;

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
        user = AppDatabase.getInstance(getActivity()).getUserDAO().getUser();
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
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RequestAdapter(item -> {
            if (filter == 0) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                intent.putExtra("id", item.getId());
                if (user.isGuard()) {
                    intent.putExtra("viewState", EventDetailsActivity.ViewState.register);
                } else {
                    intent.putExtra("viewState", EventDetailsActivity.ViewState.details);
                }
                this.startActivityForResult(intent, EventDetailsActivity.REGISTERED);
            } else if (filter == 1) {
                if (user.isGuard()) {
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("viewState", EventDetailsActivity.ViewState.unregister);
                    startActivityForResult(intent, EventDetailsActivity.UNREGISTERED);
                } else {
                    Intent intent = new Intent(getActivity(), MyEventDetailsActivity.class);
                    intent.putExtra("id", item.getId());
                    startActivityForResult(intent, 7777);
                }
            } else if (filter == 2) {
                Intent intent = new Intent(getActivity(), ActiveEventDetailsActivity.class);
                intent.putExtra("id", item.getId());
                if (user.isGuard()) {
                    intent.putExtra("viewState", ActiveEventDetailsActivity.ViewState.guardActive);
                } else {
                    intent.putExtra("viewState", ActiveEventDetailsActivity.ViewState.clientInfo);
                }
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        errorBtn.setOnClickListener(v -> {
            swipeRefreshLayout.setRefreshing(true);
            getEventTypes();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> getEvents());

        getEventTypes();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == EventDetailsActivity.REGISTERED || requestCode == EventDetailsActivity.UNREGISTERED)) {
            getEvents();
        } else if (resultCode == Activity.RESULT_OK && requestCode == 7777) {

        }
        super.onActivityResult(requestCode, resultCode, data);
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

    public void getEventTypes() {
        showProgress();
        if (Consts.eventTypeMap.size() == 0) {
            NetworkManager.getInstance(getActivity()).getEventTypes(user.getToken(), (errorMessage, eventTypes) -> {
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
        NetworkManager.getInstance(getActivity()).getEvents(user.getToken(), filter, (errorMessage, events) -> {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
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