package com.mtiengo.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotUtility {

    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/screenshots/";

    public static void captureScreenshot(WebDriver driver, String testName) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        File screenshotFolder = new File(SCREENSHOT_DIR);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File destination = new File(SCREENSHOT_DIR + timestamp + "_" + testName + ".png");

        try {
            FileHandler.copy(source, destination);
            System.out.println("Screenshot at: " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }
}
