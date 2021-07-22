package com.selenium.databasetesting.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class Login {

    @FindBy(xpath = "//a[contains(text(), 'Log in')]")
    WebElement loginLink;
    @FindBy(id = "email")
    WebElement emailId;
    @FindBy(id = "password")
    WebElement password;
    @FindBy(id = "submit-button")
    WebElement submitButton;

    //parameterized constructor is used
    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this); //initElements method is used to initialize web elements
    }
    public void signInUser() throws InterruptedException, IOException {
        Thread.sleep(2000);
        loginLink.click();
        Thread.sleep(1000);
    }

    public void setEmailId(String username) throws InterruptedException {
        Thread.sleep(1000);
        emailId.sendKeys(username);
    }

    public void setPassword(String passwd) throws InterruptedException {
        Thread.sleep(1000);
        password.sendKeys(passwd);
    }

    public void clickLogin() throws InterruptedException {
        Thread.sleep(1000);
        submitButton.click();
    }
}
