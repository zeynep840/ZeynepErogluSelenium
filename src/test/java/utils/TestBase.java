package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {
    protected WebDriver driver;

    public void initializeDriver(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "/Users/zeyneperoglu/Desktop/chromedriver-mac-x64/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu", "--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            driver.quit();
        }
    }
}
