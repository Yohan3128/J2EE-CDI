package com.hnys.bcd.cdi;

import com.hnys.bcd.annotation.Console;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Logger {
    public void log(@Observes String message) {
        System.out.println("Logger: " + message);
    }

    public void consoleLog(@Observes @Console String message) {
        System.out.println("Console: " + message);
    }
}
