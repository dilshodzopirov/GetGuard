package com.getguard.client.network;

import android.content.Context;
import android.util.Log;

import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.models.network.Register;
import com.getguard.client.models.network.SmsPhoneVerify;
import com.getguard.client.utils.BiConsumer;
import com.getguard.client.utils.NetworkUtils;
import com.google.gson.JsonObject;

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

    public static NetworkManager getInstance(Context context) {
        if (instance == null) {
            apiService = RetrofitClient.getInstance();
            instance = new NetworkManager(context);
        }
        return instance;
    }

    private Context context;
    private NetworkManager(Context context) {
        this.context = context;
    }

    public void smsPhoneVerify(String phone, int role, final BiConsumer<String, SmsPhoneVerify> consumer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber", phone);
        jsonObject.addProperty("role", role);
        apiService.smsPhoneVerify(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SmsPhoneVerify>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(SmsPhoneVerify response) {
                        consumer.accept(null, response);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        consumer.accept(getErrorMessage(throwable), null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void register(String code, String token, final BiConsumer<String, Register> consumer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("smsCode", code);
        jsonObject.addProperty("Token", token);
        apiService.register(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Register>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Register response) {
                        consumer.accept(null, response);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        consumer.accept(getErrorMessage(throwable), null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
                        consumer.accept(getErrorMessage(throwable), null);
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
                        consumer.accept(getErrorMessage(throwable), null);
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
                        consumer.accept(getErrorMessage(throwable), null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private String getErrorMessage(Throwable throwable) {
        if (NetworkUtils.isNetworkConnected(context)) {
            return "Что пошло не так";
        } else {
            return "Отсутствует соединение с интернетом";
        }
    }

}
