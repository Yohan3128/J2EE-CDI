package com.hnys.bcd.cdi;

import com.hnys.bcd.annotation.Email;
import jakarta.enterprise.context.Dependent;

@Email
@Dependent
public class EmailNotifier implements NotificationService{
    @Override
    public void notify(String message) {
        System.out.println("EmailNotifier : Sending email... " + message);
    }
}
