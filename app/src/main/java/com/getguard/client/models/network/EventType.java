package com.getguard.client.models.network;

public class EventType {

    int type;
    String name, description, url;
    double minPrice, avgPrice;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getAvgPrice() {
        return avgPrice;
    }
}
