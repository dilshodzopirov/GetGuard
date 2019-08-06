package com.getguard.client.network;

import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/list")
    Observable<EventsResponse> getEvents(@Header("Authorization") String token, @Query("filter") int filter);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/types")
    Observable<ArrayList<EventType>> getEventTypes(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/{id}")
    Observable<EventResponse> getEvent(@Header("Authorization") String token, @Path("id") String id);

    @Headers({"Content-Type: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("/api/data/category")
    Observable<ArrayList<Object>> getCategories(@Query("lang") String lang);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/api/perform/prixodstatus")
    Observable<Object> incomeAction(@Header("AccessToken") String token,
                                    @Field("method") String method,
                                    @Field("id") int id);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/api/auth/signup")
    Single<Object> signUp(@Field("name") String name,
                          @Field("username") String username,
                          @Field("email") String email,
                          @Field("phone") String phone,
                          @Field("password") String pass);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/auth/login")
    Single<Object> signIn(@Field("phone") String phoneNumber,
                                  @Field("password") String password);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("api/auth/smsverify")
    Single<Object> smsVerify(@Field("phone") String phone,
                                     @Field("vercode") String code);

    @Headers({"Content-Type: application/json", "X-Requested-With: XMLHttpRequest"})
    @GET("api/auth/sendsmsver")
    Single<Object> sendSmsVer(@Header("Authorization") String token);

}
