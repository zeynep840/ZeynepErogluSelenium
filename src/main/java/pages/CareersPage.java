package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//h3[normalize-space()='Our Locations']")
    private WebElement locationsBlock;

    @FindBy(xpath = "//*[text()='See all teams']")
    private WebElement teamsBlock;

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']")
    private WebElement lifeAtInsiderBlock;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean areCareerSectionsDisplayed() {
        try {
            // bunları da ekle js.executeScript("arguments[0].scrollIntoView(true);", locationElement);

            wait.until(ExpectedConditions.visibilityOf(locationsBlock));
            wait.until(ExpectedConditions.visibilityOf(teamsBlock));
            wait.until(ExpectedConditions.visibilityOf(lifeAtInsiderBlock));
            return locationsBlock.isDisplayed() && teamsBlock.isDisplayed() && lifeAtInsiderBlock.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
