package com.getguard.client.models.network;

public class CreateResponse {

    String[] errorMessage;
    EventsResponse.Data data;

    public String[] getErrorMessage() {
        return errorMessage;
    }

    public EventsResponse.Data getData() {
        return data;
    }


}
