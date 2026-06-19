package com.hnys.bcd.cdi;

import com.hnys.bcd.annotation.SMS;
import jakarta.enterprise.context.Dependent;

@SMS
@Dependent
public class SMSNotifier implements NotificationService{
    @Override
    public void notify(String message) {
        System.out.println("SMSNotifier : sending SMS... " + message);
    }
}
