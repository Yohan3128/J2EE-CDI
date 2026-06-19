package com.hnys.bcd.ejb;

import com.hnys.bcd.cdi.MyService;
import com.hnys.bcd.ejb.remote.AppSetting;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class AppSettingSessionBean implements AppSetting {

    @Inject
    private MyService myService;

//    @PostConstruct
//    public void init() {
//        myService = new MyService();
//    }

    @Override
    public String getName() {
        myService.doSomething();
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
