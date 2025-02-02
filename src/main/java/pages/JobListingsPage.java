package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JobListingsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions actions;

    private By acceptButton = By.id("wt-cli-accept-all-btn");
    private By seeAllQAJobsButton = By.xpath("//*[@href='https://useinsider.com/careers/open-positions/?department=qualityassurance']");
    private By locationFilter = By.xpath("//span[@id='select2-filter-by-location-container']");
    private By departmentFilter = By.xpath("//span[@id='select2-filter-by-department-container']");
    private By locationDropdownOptions = By.xpath("//li[contains(@class, 'select2-results__option')]");
    private By departmentDropdownOptions = By.xpath("//li[contains(@class, 'select2-results__option')]");
    private By jobPositions = By.cssSelector(".position-title");
    private By jobDepartments = By.xpath("//*[@data-select2-id='select2-filter-by-department-result-zqpf-Quality Assurance']");
    private By jobLocations = By.xpath("//*[@data-select2-id='select2-filter-by-location-result-q0ry-Istanbul, Turkey']");
    private By jobListingWrapper = By.xpath("//div[@class='position-list-item-wrapper bg-light']");
    private By viewRoleButton = By.xpath("//a[text()='View Role']");

    public JobListingsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    public void openQAJobsPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        wait.until(ExpectedConditions.elementToBeClickable(acceptButton)).click();
        WebElement qaJobsButton = wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsButton));
     //   js.executeScript("arguments[0].scrollIntoView(true);", qaJobsButton);
        wait.until(ExpectedConditions.elementToBeClickable(qaJobsButton)).click();
    }

    public void filterJobs(String location, String department) {
        WebElement locationElement = wait.until(ExpectedConditions.presenceOfElementLocated(locationFilter));
        js.executeScript("arguments[0].scrollIntoView(true);", locationElement);

        js.executeScript("setTimeout(function(){ console.log('Bekleme tamamlandı'); }, 3000);");
        wait.until(ExpectedConditions.elementToBeClickable(locationElement)).click();
        js.executeScript("setTimeout(function(){ console.log('Bekleme tamamlandı'); }, 3000);");

        List<WebElement> locationOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locationDropdownOptions));
        for (WebElement option : locationOptions) {
            if (option.getText().equalsIgnoreCase(location)) {
                option.click();
                break;
            }
        }

        WebElement departmentElement = wait.until(ExpectedConditions.presenceOfElementLocated(departmentFilter));
        js.executeScript("arguments[0].scrollIntoView(true);", departmentElement);
        wait.until(ExpectedConditions.elementToBeClickable(departmentElement)).click();

        List<WebElement> departmentOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(departmentDropdownOptions));
        for (WebElement option : departmentOptions) {
            if (option.getText().equalsIgnoreCase(department)) {
                option.click();
                break;
            }
        }
    }

    public boolean areJobsFilteredCorrectly() {
        List<WebElement> positions = driver.findElements(jobPositions);
        List<WebElement> departments = driver.findElements(jobDepartments);
        List<WebElement> locations = driver.findElements(jobLocations);

        for (WebElement department : departments) {
            if (!department.getText().contains("Quality Assurance")) return false;
        }
        for (WebElement location : locations) {
            if (!location.getText().contains("Istanbul, Turkey")) return false;
        }
        return true;
    }

    public void clickViewRole() {
        WebElement jobWrapper = wait.until(ExpectedConditions.presenceOfElementLocated(jobListingWrapper));
        actions.moveToElement(jobWrapper).perform(); // İlan bloğunun üstüne gelerek "View Role" butonunun görünmesini sağla
        wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton)).click();
    }
}
