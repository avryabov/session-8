package problem3;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 */
public class AccountServiceImpl implements AccountService {
    protected FraudMonitoring fraudMonitoring;

    private Map<Long, Account> accounts = new HashMap<>();
    private Set<Long> payments = new HashSet<>();
    private ReadWriteLock lockAccounts = new ReentrantReadWriteLock();
    private ReadWriteLock lockPayments = new ReentrantReadWriteLock();

    public AccountServiceImpl(FraudMonitoring fraudMonitoring) {
        this.fraudMonitoring = fraudMonitoring;
    }

    @Override
    public Result create(long clientID, long accountID, float initialBalance, Currency currency) {
        lockAccounts.writeLock().lock();
        if (fraudMonitoring.check(clientID))
            return Result.FRAUD;
        if (accounts.containsKey(accountID)) {
            return Result.ALREADY_EXISTS;
        }
        accounts.put(accountID, new Account(clientID, accountID, currency, initialBalance));
        lockAccounts.readLock().unlock();
        return Result.OK;
    }

    @Override
    public List<Account> findForClient(long clientID) {
        List<Account> list = new ArrayList<>();
        lockAccounts.readLock().lock();
        for (Map.Entry<Long, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getClientID() == clientID)
                list.add(entry.getValue());
        }
        lockAccounts.readLock().unlock();
        return list;
    }

    @Override
    public Account find(long accountID) {
        lockAccounts.readLock().lock();
        Account account = accounts.get(accountID);
        lockAccounts.readLock().unlock();
        return account;
    }

    @Override
    public Result doPayment(Payment payment) {
        lockPayments.writeLock().lock();
        if (fraudMonitoring.check(payment.getPayerID()) || fraudMonitoring.check(payment.getRecipientID()))
            return Result.FRAUD;
        if (payments.contains(payment.getOperationID()))
            return Result.ALREADY_EXISTS;
        if (payment.getPayerAccountID() == payment.getRecipientAccountID())
            return Result.ALREADY_EXISTS;
        Account payerAcc = find(payment.getPayerAccountID());
        if (payerAcc == null || payerAcc.getClientID() != payment.getPayerID())
            return Result.PAYER_NOT_FOUND;
        Account recipientAcc = find(payment.getRecipientAccountID());
        if (recipientAcc == null || recipientAcc.getClientID() != payment.getRecipientID())
            return Result.RECIPIENT_NOT_FOUND;

        payerAcc.setBalance(payerAcc.getBalance() - payment.getAmount());
        if (recipientAcc.getCurrency() == payerAcc.getCurrency()) {
            recipientAcc.setBalance(recipientAcc.getBalance() + payment.getAmount());
        } else {
            recipientAcc.setBalance(recipientAcc.getBalance() + payerAcc.getCurrency().to(payment.getAmount(), recipientAcc.getCurrency()));
        }
        payments.add(payment.getOperationID());
        lockPayments.writeLock().unlock();
        return Result.OK;
    }
}
