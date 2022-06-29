package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.UserData;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

public class StepsDefinition {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void открытаСтраницаСФормойАвторизации(String url) {
        loginPage = Selenide.open("http://localhost:9999/", LoginPage.class);
    }

    @И("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void пользовательПытаетсяАвторизоватьсяСИменемИПаролем(String login, String password) {
        verificationPage = loginPage.login(new UserData());
    }

    @И("пользователь вводит проверочный код из смс {string}")
    public void пользовательВводитПроверочныйКодИзСмс(String verifyCode) {
        dashboardPage = verificationPage.verify(new UserData());
    }


    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void пользовательПереводитРублейСКартыСНомеромНаСвоюКартуСГлавнойСтраницы(String money, String amount, String index) {
        transferPage = dashboardPage.transferClick(0);
        transferPage.transfer(new UserData(), 5_000, 1);
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей.")
    public void балансЕгоКартыИзСпискаНаГлавнойСтраницеДолженСтатьРублей(String arg0, String arg1) {
        dashboardPage.assertBalance(0, 15_000);
        dashboardPage.assertBalance(1, 5_000);
    }
}
