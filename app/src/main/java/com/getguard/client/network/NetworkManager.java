package com.getguard.client.network;

import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.utils.BiConsumer;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkManager {

    private NetworkManager() {
    }

    private static NetworkManager instance;
    private static APIService apiService;

    public static NetworkManager getInstance() {
        if (instance == null) {
            apiService = RetrofitClient.getInstance();
            instance = new NetworkManager();
        }
        return instance;
    }

    public void getEventTypes(String token, final BiConsumer<String, ArrayList<EventType>> consumer) {
        apiService.getEventTypes(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<EventType>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(ArrayList<EventType> eventTypes) {
                            consumer.accept(null, eventTypes);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        consumer.accept("No internet", null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getEvents(String token, int filter, final BiConsumer<String, ArrayList<EventsResponse.Event>> consumer) {
        apiService.getEvents(token, filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventsResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(EventsResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage(), null);
                        } else {
                            consumer.accept(null, response.getData().getList());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        consumer.accept("No internet", null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getEvent(String token, String id, final BiConsumer<String, EventResponse.Data> consumer) {
        apiService.getEvent(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(EventResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage(), null);
                        } else {
                            consumer.accept(null, response.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        consumer.accept("No internet", null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
