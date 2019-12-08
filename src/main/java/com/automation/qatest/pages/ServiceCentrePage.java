package com.automation.qatest.pages;

import com.automation.qatest.base.BaseTest;
import org.openqa.selenium.By;

public class ServiceCentrePage extends BaseTest {
    By LocationSearchField = By.cssSelector("input#locatorTextSearch");

    By LocationSubmit = By.cssSelector(".form--search-group [type='submit']");

    By LocationAddressList = By.cssSelector("locator__results-list");

    public void searchLocation(String location) {
        waitforElement(driver.findElement(LocationSearchField));
        driver.findElement(LocationSearchField).clear();
        driver.findElement(LocationSearchField).sendKeys(location);
        driver.findElement(LocationSubmit).click();
    }

    public void selectLocationAddress(String serviceCentre) throws Exception {
        waitforElement(driver.findElement(By.linkText(serviceCentre)));
        driver.findElement(By.linkText(serviceCentre)).click();

    }
}



