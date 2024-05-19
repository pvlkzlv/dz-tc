package com.example.dztc.ui.pages.favorites;

import static com.codeborne.selenide.Selenide.element;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.dztc.ui.Selectors;
import com.example.dztc.ui.pages.Page;

public class FavoritesPage extends Page {
    private final SelenideElement header = element(Selectors.byClass("ProjectPageHeader__title--ih"));

    public void waitUntilFavoritesPageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofMinutes(1));
    }
}
