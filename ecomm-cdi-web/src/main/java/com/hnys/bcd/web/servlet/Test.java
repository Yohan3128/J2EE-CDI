package com.hnys.bcd.web.servlet;

import com.hnys.bcd.ejb.remote.AppSetting;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/test",loadOnStartup = 1)
public class Test extends HttpServlet {

//    @EJB
//    private AppSetting appSetting;

    @Inject
    private MyApp myApp;

    @Override
    public void init() throws ServletException {
        System.out.println("Test init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("Ecomm Web Module Test <br>");

        req.getSession();
        myApp.doSomething();

//        resp.getWriter().write("App Name: "+appSetting.getName());

    }
}
