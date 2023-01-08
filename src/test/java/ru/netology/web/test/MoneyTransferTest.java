package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV2;
//import ru.netology.web.page.LoginPageV2;
//import ru.netology.web.page.LoginPageV3;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.CardInfo.getFirstCardInfo;
import static ru.netology.web.data.DataHelper.generateValidAmount;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;


class MoneyTransferTest {

    @Test
    void mustTransferMoneyFromFirstCardToTheSecond() {
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);

        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.
                getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.
                getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    }

    @Test
    void mustTransferMoneyFromSecondCardToTheFirst() {
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var amount = generateValidAmount(secondCardBalance);

        var expectedBalanceSecondCard = secondCardBalance - amount;
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
        var actualBalanceSecondCard = dashboardPage.
                getCardBalance(secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.
                getCardBalance(firstCardInfo);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);

    }
}