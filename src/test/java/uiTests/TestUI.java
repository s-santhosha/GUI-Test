package test.java.uiTests;

import com.automation.qatest.base.BaseTest;
import com.automation.qatest.pages.HomePage;
import com.automation.qatest.pages.ServiceCentrePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Reporter.log;


public class TestUI extends BaseTest {

    public static final Logger log = Logger.getLogger(TestUI.class.getName());

    HomePage homepage;
    ServiceCentrePage serviceCentrePage;

    @BeforeTest
    public void setUp() {
        initialization();
        homepage = new HomePage();
        serviceCentrePage = new ServiceCentrePage();
    }


    @DataProvider(name = "Locations")
    public Object[] getTestData() {
        String[][] testRecords = getData("Locations", "TestData.xlsx");
        return testRecords;
    }

    @Test(dataProvider = "Locations")
    public void selectSrvCentre(String locationName, String serviceCentre) throws Exception {
        log("------------Starting select service centre test---------------");

        log("-----------Enter option in search panel--------");
        homepage.searchPanel("Apply for a number plate");

        Assert.assertTrue(driver.getCurrentUrl().contains("apply+for+a+number+plate"), "Apply for a number plate");

        homepage.clickOnFindLocations();

        log("---------Click on Locate us and enter the suburb----------");
        serviceCentrePage.searchLocation(locationName);

        log("---------Select the suburb from the options----------");
        serviceCentrePage.selectLocationAddress(serviceCentre);

        Assert.assertEquals(driver.findElement(By.cssSelector("h1")).getText(),serviceCentre);

    }

    @AfterTest
    public void tearDown() {
        tearDownBrowserDriver();
    }


}
