package com.automation.qatest.pages;

import com.automation.qatest.base.BaseTest;
import org.openqa.selenium.By;

public class HomePage extends BaseTest {

    By ServiceNswLogo = By.cssSelector(".global-header__servicensw-logo");

    By SearchPanel = By.cssSelector(".page-hero__title input");

    By OptionDropDown = By.cssSelector("[role='option']");

    By FindLocationsLink = By.cssSelector("#block-global-header-menu [data-drupal-link-system-path='service-centre']");

    public void searchPanel(String option) {
        driver.findElement(ServiceNswLogo).click();
        waitforElement(driver.findElement(SearchPanel));
        driver.findElement(SearchPanel).clear();
        driver.findElement(SearchPanel).sendKeys(option);
        waitOnClick(OptionDropDown, driver);
    }

    public void clickOnFindLocations() {
        waitOnClick(FindLocationsLink, driver);
    }
}

