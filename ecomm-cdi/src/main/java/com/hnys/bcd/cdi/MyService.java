package com.hnys.bcd.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

@ApplicationScoped
public class MyService implements Serializable {
    public  void doSomething(){
        System.out.println("MyService doSomething..."+this);
    }
}
