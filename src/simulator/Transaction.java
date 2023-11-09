package simulator;

import java.util.UUID;

enum transferCategory {
    DEBIT,
    CREDIT
}

class Transaction {

    private String identifier;
    private User recipient;
    private User sender;
    private transferCategory category;
    private double transferAmount;
    
    public Transaction(User recipient_, User sender_, transferCategory category_, double payment_) {
        identifier = UUID.randomUUID().toString();
        recipient = recipient_;
        sender = sender_;
        category = category_;
        setPayment(payment_);;
    }

    public String getId() {
        return identifier;
    }

    public void generateId() {
        identifier = UUID.randomUUID().toString();
    }

    public void changeId(Transaction change) {
        identifier = change.identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User newRecipient) {
        recipient = newRecipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User newSender) {
        sender = newSender;
    }

    public transferCategory getCategory() {
        return category;
    }

    public void setCategory(transferCategory newCategory) {
        category = newCategory;
    }

    public double getPayment() {
        return transferAmount;
    }

    public void setPayment(double val) {
        if (category == transferCategory.CREDIT && (val > 0 || sender.getBalance() < val)) {
            transferAmount = 0;
        } else if (category == transferCategory.DEBIT && (val < 0 || recipient.getBalance() < val)) {
            transferAmount = 0;
        } else {
            transferAmount = val;
        }

    }
    public void changeUsersBalance(double val) {
        if (category == transferCategory.CREDIT) {
            sender.setBalance(sender.getBalance() - val);
            recipient.setBalance(recipient.getBalance() + val);
        } else {
            sender.setBalance(sender.getBalance() + val);
            recipient.setBalance(recipient.getBalance() - val);
        }
    }

    @Override
    public String toString() {
        if (category == transferCategory.CREDIT) {
            return recipient.getName() + " -> " + sender.getName() + ", " + transferAmount + ", INCOME, " + identifier;
        } else {
            return recipient.getName() + " -> " + sender.getName() + ", +" + transferAmount + ", OUTCOME, " + identifier;
        }
        
    }
}