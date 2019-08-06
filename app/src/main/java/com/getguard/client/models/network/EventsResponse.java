package com.getguard.client.models.network;

import java.util.ArrayList;

public class EventsResponse {

    String errorMessage;
    Data data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        ArrayList<Event> list;

        public ArrayList<Event> getList() {
            return list;
        }
    }

    public static class Event {
        String id, city, address, startDate, endDate, executorId;
        int eventType;
        double ratePrice;

        public String getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public String getAddress() {
            return address;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getExecutorId() {
            return executorId;
        }

        public int getEventType() {
            return eventType;
        }

        public double getRatePrice() {
            return ratePrice;
        }
    }

}
