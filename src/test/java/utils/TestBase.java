package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.safari.SafariDriver;
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
        }

    else if (browser.equalsIgnoreCase("safari")) {
        WebDriverManager.getInstance(SafariDriver.class).setup();
        driver = new SafariDriver();

    }
        else {
            System.setProperty("webdriver.chrome.driver", "/Users/zeyneperoglu/Desktop/chromedriver-mac-x64/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu", "--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName()); // Eğer test hata verirse screenshot al
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public void takeScreenshot(String testName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File destFile = new File("screenshots/" + testName + "_" + timestamp + ".png");
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Screenshot could not be taken: " + e.getMessage());
        }
    }
}
