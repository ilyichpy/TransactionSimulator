package simulator;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Transaction transaction;
        Node (Node next_, Node prev_, Transaction transaction_) {
            next = next_;
            prev = prev_;
            transaction = transaction_;
        }
        Node next;
        Node prev;
        
    }
    public static int currentTPlace = 0;
    @Override
    public void addTransaction(Transaction add) {
        final Node lst = last;
        final Node newNode = new Node(null, lst, add);
        last = newNode;

        if (lst == null) {
            first = newNode;
        } else {
            lst.next = newNode;
        }
        ++size;
        
    }
    @Override
    public void remove(String id) {
        boolean flag = false;
        if (id == null) {
            throw new TransactionNotFoundException("bad input");
        }
        for (Node node = first; node != null; node = node.next) {
            if (id.equals(node.transaction.getId())) {
                flag = true;
                final Node next = node.next;
                final Node prev = node.prev;
                if (prev == null) {
                    first = next;
                } else {
                    prev.next = next;
                    node.prev = null;
                }

                if (next == null) {
                    last = prev;
                } else {
                    next.prev = prev;
                    node.next = null;
                }
                node.transaction = null;
                --size;
            }
        }
        if (!flag) {
            throw new TransactionNotFoundException("No id find in list");
        }
    }
    @Override
    public Transaction[] toArray() {
        if (size == 0) {
            return null;
        }
        Transaction[] transactions = new Transaction[this.size];
        int i = 0;
        for (Node node = first; node != null; node = node.next) {
            transactions[i++] = node.transaction;
        }
        return transactions;
    }

    @Override
    public String toString() {
        String listString = new String();
        for (Node node = first; node != null; node = node.next) {
            listString = listString + node.transaction.toString() + "\n";
        }
        return listString;
    }

    public int getSize() {
        return size;
    }
}