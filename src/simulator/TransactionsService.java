package simulator;

public class TransactionsService {
    private static UsersArrayList list = new UsersArrayList();
    private int counterUsers = 0;
    public void addUser(User add) {
        list.addUser(add);
        ++counterUsers;
    }
    public double getUserBalance(int id) {
        return list.findId(id).getBalance();
    }
    public void performTransaction(int senderId, int recipientId, double amount) {
        User sender = list.findId(senderId);
        User recipient = list.findId(recipientId);

        if (senderId == recipientId || sender.getBalance() < amount || amount < 0) {
            throw new IllegalTransactionException("Transaction error");
        }
        Transaction debit = new Transaction(sender, recipient, transferCategory.DEBIT, amount);
        Transaction credit =  new Transaction(recipient, sender, transferCategory.CREDIT, -amount);
        credit.changeId(debit);
        recipient.getTransactionsList().addTransaction(credit);
        sender.getTransactionsList().addTransaction(debit);
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] geTransactions(int id) {
        return list.findId(id).getTransactionsList().toArray();
    }

    public Transaction[] geTransactions(User u) {
        return list.findId(u.getId()).getTransactionsList().toArray();
    }

    public void removeTransaction(int userId, String transactionId) {
        list.findId(userId).removeTransation(transactionId);
    }

    public Transaction[] findUniqTransactions() {
        TransactionsList uniq = getAllTransactions();

        TransactionsLinkedList result = new TransactionsLinkedList();
        Transaction[] arrayFirst = uniq.toArray();
        if (arrayFirst != null) {
            int sizeArray = arrayFirst.length;
            for (int i = 0; i < sizeArray; ++i) {
                int count = 0;
                for (int j = 0; j < sizeArray; ++j) {
                    if (arrayFirst[i].getId().equals(arrayFirst[j].getId())) {
                        count++;
                    }
                }
                if (count != 2) {
                    result.addTransaction(arrayFirst[i]);
                }
            }
        }
        return result.toArray();
    }

    public TransactionsList getAllTransactions() {
        TransactionsList tmp = new TransactionsLinkedList();

        for (int i = 0; i < counterUsers; i++) {
            User user = list.byIndex(i);
            if (user != null) {
                for (int j = 0; j < user.getTransactionsList().getSize(); j++) {
                    tmp.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }
        return tmp;
    }
    public User getUserById(int id) {
        return list.findId(id);
    }

    public void printTransactions(Transaction[] a) {
        for (int i = 0; i < a.length; ++i) {
            System.out.println(a[i]);
        }
    }
    public UsersArrayList getList() {
        return list;
    }
}