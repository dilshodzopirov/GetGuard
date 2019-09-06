package com.getguard.client.models.network;

public class SmsPhoneVerify {

    String[] errorMessage;
    SmsPhoneData data;

    public String[] getErrorMessage() {
        return errorMessage;
    }

    public SmsPhoneData getData() {
        return data;
    }

    public class SmsPhoneData {
        String token;

        public String getToken() {
            return token;
        }
    }

}
