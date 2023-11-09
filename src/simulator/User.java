package simulator;

public class User {
    private final int identifier;
    private String name;
    private double balance;
    private TransactionsLinkedList listTransactions;

    public User() {
      identifier = UserIdsGenerator.getInstance().generateId();
      balance = 0;
      listTransactions = new TransactionsLinkedList();
    }

    public User(String name_, double balance_) {
        identifier = UserIdsGenerator.getInstance().generateId();
        name = name_;
        balance = balance_ < 0 ? 0 : balance_;
        listTransactions = new TransactionsLinkedList();
    }
    public int getId() {
        return identifier;
     }

     public String getName() {
        return name;
     }

     public void setName(String newName) {
        name = newName;
     }

     public double getBalance() {
        return balance;
     }

     public void setBalance(double val) {
        balance = val < 0 ? 0 : val;
     }

   public TransactionsLinkedList getTransactionsList() {
      return listTransactions;
   }

   public void setTransactionsList(TransactionsLinkedList transactionsList) {
      listTransactions = transactionsList;
   }

   public void removeTransation(String id) {
      listTransactions.remove(id);
   }

     @Override
     public String toString() {
       return identifier + " " +  name + "  " + balance;
     }
}