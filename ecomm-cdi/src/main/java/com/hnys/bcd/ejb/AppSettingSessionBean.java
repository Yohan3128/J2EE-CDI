package com.hnys.bcd.ejb;

import com.hnys.bcd.annotation.Console;
import com.hnys.bcd.annotation.Email;
import com.hnys.bcd.annotation.SMS;
import com.hnys.bcd.cdi.MyService;
import com.hnys.bcd.cdi.NotificationService;
import com.hnys.bcd.ejb.remote.AppSetting;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@Stateless
public class AppSettingSessionBean implements AppSetting {

    @Inject
    private MyService myService;

    @Inject
    @SMS
    private NotificationService notificationService;

    @Inject
    @Console
    private Event<String> logEvent;


//    @PostConstruct
//    public void init() {
//        myService = new MyService();
//    }

    @Override
    public String getName() {

        notificationService.notify("Hello, This is the app setting session bean");

        logEvent.fire("AppSettingSessionBean: getName()");

//        myService.doSomething();

        return "Ecomm EE App";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getDescription() {
        return "This is the Ecomm EE App setting bean.";
    }
}
