package com.getguard.client.utils;

import com.getguard.client.models.network.EventType;
import com.getguard.client.models.network.EventsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Consts {

    public static Map<Integer, EventType> eventTypeMap = new HashMap<>();
    public static ArrayList<EventsResponse.Event> events = new ArrayList<>();

}
