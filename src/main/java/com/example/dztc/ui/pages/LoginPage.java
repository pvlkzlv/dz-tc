package com.example.dztc.ui.pages;

import static com.codeborne.selenide.Selenide.element;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.dztc.api.models.User;
import com.example.dztc.ui.Selectors;
import io.qameta.allure.Step;
import lombok.Getter;

@Getter
public class LoginPage extends Page{
    static final String LOGIN_PAGE = "/login.html";
    private SelenideElement usernameInput = element(Selectors.byId("username"));
    private SelenideElement passwordInput = element(Selectors.byId("password"));

    @Step("Open login page")
    public LoginPage open() {
        Selenide.open(LOGIN_PAGE);
        return this;
    }

    @Step("Login as user {0}")
    public void login(User user) {
        open();
        usernameInput.sendKeys(user.getUsername());
        passwordInput.sendKeys(user.getPassword());
        submit();
    }
}