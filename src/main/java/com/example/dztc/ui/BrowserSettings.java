package com.example.dztc.ui;

import java.util.HashMap;
import java.util.Map;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserSettings {
    public static void setup(String browser) {
        Configuration.browser = browser;
        switch (browser) {
            case "chrome":
                setChromeOptions();
                break;
            case "firefox":
                setFirefoxOptions();
                break;
        }
    }

    private static void setFirefoxOptions() {
        Configuration.browserCapabilities = new FirefoxOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", setSelenoidOptions());
    }

    private static void setChromeOptions() {
        Configuration.browserCapabilities = new ChromeOptions();
        Configuration.browserCapabilities.setCapability("selenoid:options", setSelenoidOptions());
    }

    private static Map<String, Boolean> setSelenoidOptions() {
        Map<String, Boolean> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);
        return options;
    }
}