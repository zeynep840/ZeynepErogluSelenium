package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "wt-cli-accept-all-btn")
    private WebElement acceptButton;
    @FindBy(xpath = "(//a[normalize-space()='Company'])[1]")
    private WebElement companyMenu;

    @FindBy(xpath = "//a[@href='https://useinsider.com/careers/']")
    private WebElement careersLink;

    @FindBy(xpath = "//a[@href='https://inone.useinsider.com/login']")
    private WebElement login;



    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openHomePage() {
        driver.get("https://useinsider.com/");
        wait.until(ExpectedConditions.elementToBeClickable(acceptButton)).click();

    }

    public boolean isHomePageOpened() {
        try {
            wait.until(ExpectedConditions.visibilityOf(login));

            return driver.getTitle().contains("Insider") && login.isDisplayed();
        }
        catch (Exception e){
            return false;
        }


    }

    public void goToCareersPage() {
        wait.until(ExpectedConditions.elementToBeClickable(companyMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(careersLink)).click();
    }
}
