package problem3;

import java.util.Objects;

/**
 */
public class Account {
    private long clientID;

    private long accountID;

    private Currency currency;

    private float balance;

    public Account(long clientID, long accountID, Currency currency, float balance) {
        this.clientID = clientID;
        this.accountID = accountID;
        this.currency = currency;
        this.balance = balance;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Account account = (Account) o;
        return clientID == account.clientID &&
                accountID == account.accountID &&
                Float.compare(account.balance, balance) == 0 &&
                currency == account.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID, accountID, currency, balance);
    }
}
