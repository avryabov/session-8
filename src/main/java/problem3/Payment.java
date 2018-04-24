package problem3;

import java.util.Objects;

/**
 */
public class Payment {
    private long operationID;

    private long payerID;
    private long payerAccountID;

    private long recipientID;
    private long recipientAccountID;

    private float amount;

    public Payment(long operationID, long payerID, long payerAccountID, long recipientID, long recipientAccountID,
                   float amount) {
        this.operationID = operationID;
        this.payerID = payerID;
        this.payerAccountID = payerAccountID;
        this.recipientID = recipientID;
        this.recipientAccountID = recipientAccountID;
        this.amount = amount;
    }

    public long getPayerID() {
        return payerID;
    }

    public void setPayerID(long payerID) {
        this.payerID = payerID;
    }

    public long getPayerAccountID() {
        return payerAccountID;
    }

    public void setPayerAccountID(long payerAccountID) {
        this.payerAccountID = payerAccountID;
    }

    public long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(long recipientID) {
        this.recipientID = recipientID;
    }

    public long getRecipientAccountID() {
        return recipientAccountID;
    }

    public void setRecipientAccountID(long recipientAccountID) {
        this.recipientAccountID = recipientAccountID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getOperationID() {
        return operationID;
    }

    public void setOperationID(long operationID) {
        this.operationID = operationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Payment payment = (Payment) o;
        return operationID == payment.operationID &&
                payerID == payment.payerID &&
                payerAccountID == payment.payerAccountID &&
                recipientID == payment.recipientID &&
                recipientAccountID == payment.recipientAccountID &&
                Float.compare(payment.amount, amount) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(operationID, payerID, payerAccountID, recipientID, recipientAccountID, amount);
    }
}
