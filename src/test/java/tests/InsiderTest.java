package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.CareersPage;
import pages.JobListingsPage;
import utils.TestBase;

import java.time.Duration;
import java.util.Set;

import static utils.Driver.quitDriver;

public class InsiderTest extends TestBase {
    private HomePage homePage;
    private CareersPage careersPage;
    private JobListingsPage jobListingsPage;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        initializeDriver(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        jobListingsPage = new JobListingsPage(driver);
    }

    @Test
    public void testInsiderWebsite() {
        homePage.openHomePage();
        Assert.assertTrue(homePage.isHomePageOpened(), "Insider homepage is not opened");

        homePage.goToCareersPage();
        Assert.assertTrue(careersPage.areCareerSectionsDisplayed(), "Career sections are missing");

        jobListingsPage.openQAJobsPage();
        jobListingsPage.filterJobs("Istanbul, Turkey", "Quality Assurance");
        Assert.assertTrue(jobListingsPage.areJobsFilteredCorrectly(), "Jobs are not filtered correctly");

        jobListingsPage.clickViewRole();

        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        Assert.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Application page did not open");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @AfterMethod
    public void tearDown() {
        quitDriver(); // Hata alırsa otomatik screenshot alınacak
    }
}

