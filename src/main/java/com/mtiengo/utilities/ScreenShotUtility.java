package com.mtiengo.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotUtility {

    private static final Logger log = LoggerFactory.getLogger(ScreenShotUtility.class);
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
            log.info("Screenshot at: {}", destination.getAbsolutePath());
        } catch (IOException e) {
            log.error("Screenshot failed", e);
        }
    }
}
