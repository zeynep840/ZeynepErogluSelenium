package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
    private By locationDropdownOptions = By.xpath("//*[@id='select2-filter-by-location-container']");
    private By departmentDropdownOptions = By.xpath("//li[contains(@class, 'select2-results__option')]");
    private By jobPositions = By.cssSelector(".position-title");
    private By jobDepartments = By.xpath("//*[@data-select2-id='select2-filter-by-department-result-zqpf-Quality Assurance']");
    private By jobLocations = By.xpath("//*[@id='select2-filter-by-location-result-exun-Istanbul, Turkey']");
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
        WebElement qaJobsButton = wait.until(ExpectedConditions.elementToBeClickable(seeAllQAJobsButton));
        //   js.executeScript("arguments[0].scrollIntoView(true);", qaJobsButton);
        wait.until(ExpectedConditions.elementToBeClickable(qaJobsButton)).click();
    }

    public void filterJobs(String location, String department) throws InterruptedException {

        WebElement locationElement = wait.until(ExpectedConditions.presenceOfElementLocated(locationFilter));
        js.executeScript("arguments[0].scrollIntoView(true);", locationElement);
        Thread.sleep(20000); //Method olarak yazılabılır
        wait.until(ExpectedConditions.elementToBeClickable(locationElement)).click();
        //  WebElement locationElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(jobLocations));
        // locationElement1.click();
        List<WebElement> locationOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(departmentDropdownOptions));
        for (WebElement option : locationOptions) {
            if (option.getText().equalsIgnoreCase(location)) {
                option.click();
                break;
            }
           // if kaldır  düzenle

        }

        WebElement departmentElement = wait.until(ExpectedConditions.presenceOfElementLocated(departmentFilter));
        js.executeScript("arguments[0].scrollIntoView(true);", departmentElement);
        wait.until(ExpectedConditions.elementToBeClickable(departmentElement)).click();

        List<WebElement> departmentOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(departmentDropdownOptions));
        for (WebElement option : departmentOptions) {
            if (option.getText().equalsIgnoreCase(department)) {
                option.click();
                break;
                //if kaldır
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

    public boolean areJobsFilteredIsIlanlari() {
        List<WebElement> jobListings = driver.findElements(By.cssSelector(".position-list-item"));

        if (jobListings.isEmpty()) {
            System.out.println("İlan bulunamadı!");
            return false;
        }

        int validJobCount = 0;

        for (WebElement job : jobListings) {
            String location = job.getAttribute("data-location");
            String team = job.getAttribute("data-team");

            if ("istanbul-turkey".equalsIgnoreCase(location) && "qualityassurance".equalsIgnoreCase(team)) {
                validJobCount++;
            } else {
                System.out.println("Yanlış ilan bulundu! Location: " + location + ", Team: " + team);
                return false; // Yanlış bir ilan varsa test başarısız olmalı
            }
        }

        System.out.println("Doğru ilan sayısı: " + validJobCount);
        return validJobCount == jobListings.size(); // Listedeki tüm ilanlar uygunsa true döndür
    }



    public void clickViewRole() {
        WebElement jobWrapper = wait.until(ExpectedConditions.presenceOfElementLocated(jobListingWrapper));
        actions.moveToElement(jobWrapper).perform(); // İlan bloğunun üstüne gelerek "View Role" butonunun görünmesini sağla
        wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton)).click();
    }
}