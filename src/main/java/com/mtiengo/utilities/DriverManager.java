package com.mtiengo.utilities;

import org.openqa.selenium.WebDriver;

/**
 * Thread-safe WebDriver holder using ThreadLocal.
 * Ensures each thread gets its own isolated WebDriver instance.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {}

    public static void setDriver(WebDriver driver) {
        driverHolder.set(driver);
    }

    public static WebDriver getDriver() {
        return driverHolder.get();
    }

    public static void removeDriver() {
        driverHolder.remove();
    }
}