package com.Bank.OnlineBanking.Notification;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Component
public class SmsService {
    private static final String ACCOUNT_SID = "AC3f6465a99809a1c6cbcff96dce3df959";
    private static final String AUTH_TOKEN = "1c90235fe7de46649384a8e3588c4fcc";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String to, String message) {
        Message.creator(
                new com.twilio.type.PhoneNumber(to), // To number
                new com.twilio.type.PhoneNumber("+16087668847"), // From number
                message
        ).create();
    }
}
