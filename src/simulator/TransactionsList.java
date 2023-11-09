package simulator;

public interface TransactionsList {
    public void addTransaction(Transaction add);
    public void remove(String id);
    public Transaction[] toArray();
}