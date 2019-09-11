package com.getguard.client.network;

import android.content.Context;
import android.util.Log;

import com.getguard.client.models.network.CreateResponse;
import com.getguard.client.models.network.DeleteResponse;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.models.network.HireResponse;
import com.getguard.client.models.network.Register;
import com.getguard.client.models.network.SmsPhoneVerify;
import com.getguard.client.models.network.UserByIdResponse;
import com.getguard.client.utils.BiConsumer;
import com.getguard.client.utils.NetworkUtils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

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
                            consumer.accept(response.getErrorMessage().get(0), null);
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

    public void deleteEvent(String token, String id, final BiConsumer<String, String> consumer) {
        apiService.deleteEvent(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(DeleteResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage()[0], null);
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

    public void createEvent(String token,
                            String city,
                            String address,
                            String startDate,
                            String endDate,
                            int ratePrice,
                            int dressCode,
                            boolean hasPrivateGuardLicense,
                            int weapon,
                            String photoId,
                            int personalCar,
                            String additionalInformation,
                            int eventType,
                            final BiConsumer<String, EventsResponse.Data> consumer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("city", city);
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("startDate", startDate);
        jsonObject.addProperty("endDate", endDate);
        jsonObject.addProperty("ratePrice", ratePrice);
        jsonObject.addProperty("dressCode", dressCode);
        jsonObject.addProperty("hasPrivateGuardLicense", hasPrivateGuardLicense);
        jsonObject.addProperty("weapon", weapon);
        jsonObject.addProperty("photoId", photoId);
        jsonObject.addProperty("personalCar", personalCar);
        jsonObject.addProperty("additionalInformation", additionalInformation);
        jsonObject.addProperty("eventType", eventType);
        apiService.createEvent(token, jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CreateResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(CreateResponse response) {
                        consumer.accept(null, response.getData());
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

    public void respond(String token, String id, final BiConsumer<String, Boolean> consumer) {
        apiService.respond(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HireResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(HireResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage()[0], null);
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

    public void getUserById(String token, String id, final BiConsumer<String, UserByIdResponse.Data> consumer) {
        apiService.getUser(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserByIdResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(UserByIdResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage().get(0), null);
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

    public void hire(String token, String id, String executorId, final BiConsumer<String, Boolean> consumer) {
        apiService.hire(token, id, executorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HireResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(HireResponse response) {
                        if (response.getErrorMessage() != null) {
                            consumer.accept(response.getErrorMessage()[0], null);
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

    private String getErrorMessage(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException)e).response().errorBody();
            return getErrorMessage(responseBody);
        } else if (e instanceof SocketTimeoutException) {
            return "Время ожидания запроса истекло";
        } else if (e instanceof IOException) {
            if (NetworkUtils.isNetworkConnected(context)) {
                return "Что пошло не так";
            } else {
                return "Отсутствует соединение с интернетом";
            }
        } else {
            return "Что пошло не так";
        }
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            JSONArray errorMessages = jsonObject.getJSONArray("errorMessage");
            return errorMessages.join(", ");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
