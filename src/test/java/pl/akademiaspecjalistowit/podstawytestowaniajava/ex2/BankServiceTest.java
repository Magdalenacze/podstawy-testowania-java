package pl.akademiaspecjalistowit.podstawytestowaniajava.ex2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.money.BankServicePln;
import pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.money.Currency;
import pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.money.Money;
import pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.money.MoneyException;

class BankServiceTest {

    private BankService bankServiceSuT;

    @BeforeEach
    void setUp() {
        bankServiceSuT = new BankServicePln();
    }

    @Test
    public void should_deposit_money_successfully() {
        //given
        Money money = new Money(Currency.PLN, 14.5);
        Double startingBalance = bankServiceSuT.checkBalance().get(0).amount;

        //when
        bankServiceSuT.depositMoney(money);

        //then
        assertThat(bankServiceSuT.checkBalance()).isNotEmpty();
        assertThat(bankServiceSuT.checkBalance().get(0).amount).isEqualTo
                (startingBalance + money.amount);
    }

    @Test
    public void should_withdraw_money_successfully() {
        //given
        Money money = new Money(Currency.PLN, 20.0);
        Double startingBalance = bankServiceSuT.checkBalance().get(0).amount;

        //when
        bankServiceSuT.withdrawMoney(money);

        //then
        assertThat(bankServiceSuT.checkBalance()).isNotEmpty();
        assertThat(bankServiceSuT.checkBalance().get(0).amount).isEqualTo
                (startingBalance - money.amount);
    }

    @Test
    public void should_not_allow_to_withdraw_money_on_debit() {
        //given
        Money money = new Money(Currency.PLN, 200.0);

        //when
        Throwable thrown = catchThrowable(() -> bankServiceSuT.withdrawMoney(money));

        //then
        assertThat(thrown)
                .isInstanceOf(MoneyException.class)
                .hasMessageContaining("The operation cannot be performed. " +
                        "The amount to be withdrawn is higher than your current balance!");
    }

    @Test
    public void balance_should_not_change_when_withdraw_failed() {
        //given
        Money money = new Money(Currency.PLN, 200.0);
        Double startingBalance = bankServiceSuT.checkBalance().get(0).amount;

        //when
        Throwable thrown = catchThrowable(() -> bankServiceSuT.withdrawMoney(money));

        //then
        assertThat(bankServiceSuT.checkBalance().get(0).amount).isEqualTo(startingBalance);
    }

    @Test
    public void should_not_allow_to_deposit_over_5000_at_once() {
        //given
        Money money = new Money(Currency.PLN, 5100.0);

        //when
        Throwable thrown = catchThrowable(() -> bankServiceSuT.depositMoney(money));

        //then
        assertThat(thrown)
                .isInstanceOf(MoneyException.class)
                .hasMessageContaining("The operation cannot be performed. " +
                        "The amount provided exceeds the set limit!");
    }

    @Test
    public void should_not_allow_to_withdraw_money_with_different_account_currency() {
        //given
        Money money = new Money(Currency.EUR, 100.0);

        //when
        Throwable thrown = catchThrowable(() -> bankServiceSuT.withdrawMoney(money));

        //then
        assertThat(thrown)
                .isInstanceOf(MoneyException.class)
                .hasMessageContaining("The currency of the money withdrawn is different from " +
                        "the currency of the account!");
    }

    @Test
    public void should_not_allow_to_deposit_money_with_different_account_currency() {
        //given
        Money money = new Money(Currency.EUR, 100.0);

        //when
        Throwable thrown = catchThrowable(() -> bankServiceSuT.depositMoney(money));

        //then
        assertThat(thrown)
                .isInstanceOf(MoneyException.class)
                .hasMessageContaining("The currency of the deposited money is different " +
                        "from the currency of the account!");
    }
}