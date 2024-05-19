package com.example.dztc.ui.pages.favorites;

import static com.codeborne.selenide.Selenide.elements;

import java.util.List;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.dztc.ui.Selectors;
import com.example.dztc.ui.pages.elements.ProjectElement;

public class ProjectsPage extends FavoritesPage {

    public static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--Px"));

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritesPageIsLoaded();
        return this;
    }

    public List<ProjectElement> getSubProjects() {
        return generatePageElements(subprojects, ProjectElement::new);
    }
}