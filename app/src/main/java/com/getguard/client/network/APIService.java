package com.getguard.client.network;

import androidx.room.Delete;

import com.getguard.client.models.network.CreateResponse;
import com.getguard.client.models.network.DeleteResponse;
import com.getguard.client.models.network.EventResponse;
import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;
import com.getguard.client.models.network.HireResponse;
import com.getguard.client.models.network.Register;
import com.getguard.client.models.network.SmsPhoneVerify;
import com.getguard.client.models.network.UserByIdResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/auth/smsphoneverify")
    Observable<SmsPhoneVerify> smsPhoneVerify(@Body JsonObject body);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/auth/register")
    Observable<Register> register(@Body JsonObject body);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/list")
    Observable<EventsResponse> getEvents(@Header("Authorization") String token, @Query("filter") int filter);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/types")
    Observable<ArrayList<EventType>> getEventTypes(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("api/event/{id}")
    Observable<EventResponse> getEvent(@Header("Authorization") String token, @Path("id") String id);

    @Headers({"Content-Type: application/json-patch+json"})
    @DELETE("api/event/{id}")
    Observable<DeleteResponse> deleteEvent(@Header("Authorization") String token, @Path("id") String id);

    @Headers({"Content-Type: application/json-patch+json"})
    @POST("api/event")
    Observable<CreateResponse> createEvent(@Header("Authorization") String token, @Body JsonObject body);

    @Headers({"Content-Type: application/json-patch+json"})
    @POST("/api/event/{id}/register")
    Observable<HireResponse> respond(@Header("Authorization") String token, @Path("id") String id);

    @Headers({"Content-Type: application/json-patch+json"})
    @GET("/api/User/{id}")
    Observable<UserByIdResponse> getUser(@Header("Authorization") String token, @Path("id") String id);

    @Headers({"Content-Type: application/json-patch+json"})
    @POST("/api/event/{id}/executor/{executorId}")
    Observable<HireResponse> hire(@Header("Authorization") String token, @Path("id") String id, @Path("executorId") String executorId);


}
