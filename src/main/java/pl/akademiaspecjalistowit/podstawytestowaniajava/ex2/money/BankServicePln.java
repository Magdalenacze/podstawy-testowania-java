package pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.money;

import pl.akademiaspecjalistowit.podstawytestowaniajava.ex2.BankService;

import java.util.List;
import java.util.Objects;

public class BankServicePln implements BankService {

    private Money accountBalance;
    private static final Integer oneTimeDepositLimit = 5000;

    public BankServicePln() {
        this.accountBalance = new Money(Currency.PLN, 100.0);
    }

    @Override
    public Money withdrawMoney(Money amount) {
        if (amount.amount > accountBalance.amount) {
            throw new MoneyException("The operation cannot be performed. The amount to be " +
                    "withdrawn is higher than your current balance!");
        }
        if (!accountBalance.currency.equals(amount.currency)) {
            throw new MoneyException("The currency of the money withdrawn is different from " +
                    "the currency of the account!");
        }
        return accountBalance = new Money(accountBalance.currency,
                accountBalance.amount - amount.amount);
    }

    @Override
    public void depositMoney(Money amount) {
        if (amount.amount > oneTimeDepositLimit) {
            throw new MoneyException("The operation cannot be performed. The amount provided " +
                    "exceeds the set limit!");
        }
        if (!accountBalance.currency.equals(amount.currency)) {
            throw new MoneyException("The currency of the deposited money is different from " +
                    "the currency of the account!");
        }
        accountBalance = new Money(accountBalance.currency,
                accountBalance.amount + amount.amount);
    }

    @Override
    public List<Money> checkBalance() {
        return List.of(accountBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankServicePln that = (BankServicePln) o;
        return Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalance);
    }
}
