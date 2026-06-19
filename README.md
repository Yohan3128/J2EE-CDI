# Ecomm CDI Sample Project

## Overview

This sample project demonstrates **Contexts and Dependency Injection (CDI)** in a Jakarta EE application. It combines a Jakarta EE EJB module with a web module to show how CDI integrates:

- `ecomm-cdi`: an EJB module containing CDI beans, qualifiers, event observers, and a stateless session bean
- `ecomm-cdi-web`: a web module containing a servlet and a session-scoped managed bean that injects EJB and CDI components

The project is built for Jakarta EE 10 and Java 17.

## Key Concepts Demonstrated

- CDI injection with `@Inject`
- Qualifier-based bean selection with custom qualifiers (`@Email`, `@SMS`, `@Console`)
- CDI event firing and observation with `Event<T>` and `@Observes`
- Bean scopes: `@Dependent`, `@ApplicationScoped`, `@SessionScoped`
- EJB remote interface injection with `@EJB`
- Servlet integration with CDI and EJB

## Project Structure

```
CDI/
  ecomm-cdi/             # EJB module
    pom.xml
    src/main/java/
      com/hnys/bcd/annotation/  # CDI qualifiers
      com/hnys/bcd/cdi/         # CDI services and observers
      com/hnys/bcd/ejb/         # EJB session bean
      com/hnys/bcd/ejb/remote/  # Remote EJB interface
  ecomm-cdi-web/         # Web module
    pom.xml
    src/main/java/
      com/hnys/bcd/web/servlet/ # Servlet and session-scoped bean
    src/main/webapp/
      index.jsp
```

## Module Details

### `ecomm-cdi` module

This module is packaged as `ejb` and contains the CDI implementation and business logic.

#### Core CDI interfaces and beans

- `NotificationService` — CDI interface for notification delivery.
- `EmailNotifier` — `@Email` qualified implementation that simulates email notifications.
- `SMSNotifier` — `@SMS` qualified implementation that simulates SMS notifications.
- `Logger` — `@ApplicationScoped` event observer for logging messages.
- `MyService` — `@Dependent` CDI bean used as a simple utility service.

#### EJB session bean

- `AppSettingSessionBean` — `@Stateless` session bean implementing `AppSetting`.
  - Injects `MyService` using CDI
  - Injects `NotificationService` with `@SMS` qualifier
  - Injects `Event<String>` with `@Console` qualifier to publish log events

#### Remote interface

- `AppSetting` — `@Remote` EJB interface with application metadata methods.

### `ecomm-cdi-web` module

This module is packaged as `war` and contains the servlet layer.

- `Test` servlet (`/test`) uses:
  - `@EJB` injection of the `AppSetting` remote interface
  - `@Inject` injection of the session-scoped `MyApp` bean
- `MyApp` — `@SessionScoped` CDI bean demonstrating session lifecycle
- `index.jsp` — simple JSP landing page

## Example Code

### Qualifier and service example

```java
package com.hnys.bcd.cdi;

import com.hnys.bcd.annotation.SMS;
import jakarta.enterprise.context.Dependent;

@SMS
@Dependent
public class SMSNotifier implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("SMSNotifier : sending SMS... " + message);
    }
}
```

### Event observer example

```java
package com.hnys.bcd.cdi;

import com.hnys.bcd.annotation.Console;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Logger {
    public void consoleLog(@Observes @Console String message) {
        System.out.println("Console: " + message);
    }
}
```

### EJB session bean example

```java
package com.hnys.bcd.ejb;

import com.hnys.bcd.annotation.Console;
import com.hnys.bcd.annotation.SMS;
import com.hnys.bcd.cdi.MyService;
import com.hnys.bcd.cdi.NotificationService;
import com.hnys.bcd.ejb.remote.AppSetting;
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

    @Override
    public String getName() {
        notificationService.notify("Hello, This is the app setting session bean");
        logEvent.fire("AppSettingSessionBean: getName()");
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
```

### Servlet example

```java
package com.hnys.bcd.web.servlet;

import com.hnys.bcd.ejb.remote.AppSetting;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/test", loadOnStartup = 1)
public class Test extends HttpServlet {
    @EJB
    private AppSetting appSetting;

    @Inject
    private MyApp myApp;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("Ecomm Web Module Test <br>");
        req.getSession();
        myApp.doSomething();
        resp.getWriter().write("App Name: " + appSetting.getName());
    }
}
```

## Build Instructions

### Prerequisites

- Java 17 SDK
- Apache Maven 3.8+ or newer
- Jakarta EE 10 compatible application server (example: Payara 7, Open Liberty 24, WildFly 30+, GlassFish 7)

### Build the EJB module

From the workspace root:

```bash
cd ecomm-cdi
mvn clean install
```

### Build the web module

```bash
cd ../ecomm-cdi-web
mvn clean package
```

## Deployment Instructions

1. Deploy the EJB module to your Jakarta EE server if required by your server profile.
   - The compiled EJB JAR is located at `ecomm-cdi/target/ecomm-cdi.jar`.
2. Deploy the web module WAR file.
   - The WAR is located at `ecomm-cdi-web/target/ecomm-cdi-web.war`.
3. Access the servlet in a browser:

```text
http://localhost:8080/ecomm-cdi-web/test
```

4. Optionally view the JSP page:

```text
http://localhost:8080/ecomm-cdi-web/index.jsp
```

> Note: The `ecomm-cdi-web` module depends on `ecomm-cdi` via the `ejb-client` artifact. In some Jakarta EE server environments, you may need to deploy the EJB module first or package both modules in an EAR.

## CDI Concepts in This Project

- `@Dependent`: bean lifecycle tied to injection point
- `@ApplicationScoped`: shared singleton across the application
- `@SessionScoped`: bean tied to an HTTP session
- `@Inject`: CDI injection of managed beans
- `@EJB`: EJB injection for remote interfaces
- `@Qualifier`: custom qualifiers used to choose the correct implementation
- `Event<T>` and `@Observes`: CDI event bus for loosely coupled notifications

## Custom Qualifiers

The project defines these qualifiers:

- `@Email`
- `@SMS`
- `@Console`

Example qualifier source:

```java
package com.hnys.bcd.annotation;

import jakarta.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface Console {
}
```

## How the Sample Works

- `Test` servlet injects the remote EJB `AppSetting` via `@EJB`.
- `AppSettingSessionBean` fires a console log event and sends an SMS notification using CDI qualifiers.
- `Logger` observes events and prints application log messages.
- `MyApp` is a `@SessionScoped` bean demonstrating a web session-managed CDI bean.

## Recommended Next Steps

- Add another servlet or JSF page to inject additional CDI beans
- Add a new qualified implementation for `NotificationService`
- Convert the project to a Maven multi-module aggregator for simpler root-level builds
- Extend the EJB interface with transactional operations and database persistence
