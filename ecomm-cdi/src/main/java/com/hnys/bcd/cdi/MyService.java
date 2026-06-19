package com.hnys.bcd.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;

@Dependent
public class MyService {
    public  void doSomething(){
        System.out.println("MyService doSomething..."+this);
    }
}
