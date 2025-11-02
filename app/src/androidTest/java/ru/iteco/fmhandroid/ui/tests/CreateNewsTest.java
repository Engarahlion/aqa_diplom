package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.bloco.faker.Faker;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.Utils;
import ru.iteco.fmhandroid.ui.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.CreateNews;
import ru.iteco.fmhandroid.ui.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class CreateNewsTest {
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    CreateNews createNews = new CreateNews();
    EditNews editNews = new EditNews();
    NewsPage newsPage = new NewsPage();
    Faker faker = new Faker();
    private String choosingСategory = "Объявление";
    private String invalidCategory = faker.book.genre();
    private String randomTitle = faker.book.author();
    private String addendumTime = "15:10";
    private String addendumDescription = "fortune";

    @Rule
    public ActivityScenarioRule < AppActivity > mActivityScenarioRule =
            new ActivityScenarioRule < > (AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Успешное создание новости TC-24")
    @Test
    public void successfulNewsCreation() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
    }

    @Description("Создание пустой новости TC-26")
    @Test
    public void shouldStayOnNewsCreationScreenWhenCreatingNewsWithEmptyFields() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание новости с несуществующей категорией TC-27")
    @Test
    public void creatingNewWithFictionalCategory() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(invalidCategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание новости без указания даты TC-28")
    @Test
    public void creatingNewWithoutSpecifyingTheDay() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Создание новости без указания времени TC-31")
    @Test
    public void creatingNewWithoutSpecifyingTheTime() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
    }

    @Description("Удаление новости TC-52")
    @Test
    public void shouldDeleteNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.deleteNews();
        controlPanelNews.checkDoesNotExistNews(randomTitle);
    }

    @Description("Редактирование заголовка новости и сохранение изменений TC-44")
    @Test
    public void editingTheNewsHeadline() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.pressEditPanelNews();
        editNews.editTitle(randomTitle);
        editNews.pressSave();
    }

    @Description("Редактирование категории новости и сохранение изменений TC-45")
    @Test
    public void shouldEditCategoryOfNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory(choosingСategory);
        createNews.addTitle(randomTitle);
        createNews.addDate(Utils.currentDate());
        createNews.addTime(addendumTime);
        createNews.addDescription(addendumDescription);
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.searchNewsAndCheckIsDisplayed(randomTitle);
        controlPanelNews.pressEditPanelNews();
        editNews.editCategory(randomTitle);
        editNews.pressSave();
    }

}