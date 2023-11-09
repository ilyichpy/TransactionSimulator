package simulator;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService res;
    private final Scanner sc;
    public Menu() {
       res = new TransactionsService();
       sc = new Scanner(System.in);
    }
    public void run(boolean specialMod) {
        while (true) {
            if (specialMod) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. DEV – remove a transfer by ID");
                System.out.println("6. DEV – check transfer validity");
                System.out.println("7. Finish execution");
                System.out.println(" ");
                boolean result = specialFunc(true);
                if (result) {
                    break;
                } 
            } else {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                System.out.println("5. Finish execution");
                System.out.println(" ");
                boolean result = specialFunc(false);
                if (result) {
                    break;
                }
            }
        }
    }
    private boolean specialFunc(boolean mod) {
        String answer = sc.nextLine();
        int input = Integer.parseInt(answer);
        if (mod) {
            if (input < 1 || input > 7) {
                throw new MenuException("Wrong input");
            }
            switch(input) {
                case 1:
                    addUser();
                    break;
                case 2:
                    checkBalance();
                    break;
                case 3:
                    performTransaction();
                    break;
                case 4:
                    viewAllTransactions();
                    break;
                case 5:
                    removeId();
                    break;
                case 6:
                    checkUniqueTransactions();
                    break;
                case 7:
                    return true;
            }
        } else {
             if (input < 1 || input > 5) {
                throw new MenuException("Wrong input");
            }
            switch(input) {
                case 1:
                    addUser();
                    break;
                case 2:
                    checkBalance();
                    break;
                case 3:
                    performTransaction();
                    break;
                case 4:
                    viewAllTransactions();
                    break;
                case 5:
                    return true;
            }
        }
        return false;
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String infoInput = sc.nextLine().trim();
        try {
            String[] array = infoInput.split("\\s+");
            if (array.length != 2) {
                throw new MenuException("invalid input");
            }
            String userName = array[0];
            double userBalance = Double.parseDouble(array[1]);
            User user = new User(userName, userBalance);
            res.addUser(user);
            System.out.println("User with id = " + user.getId() + " is added");
        } catch (Exception err) {
            System.err.println(err);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void checkBalance() {
        System.out.println("Enter a user ID");
        String inputId = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(inputId);
            double balance = res.getUserBalance(id);
            System.out.println(res.getUserById(id).getName() + " - " + balance);
        } catch(Exception err) {
            System.err.println(err);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void performTransaction() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String inputS = sc.nextLine().trim();
        try {
            String[] array = inputS.split("\\s+");
            if (array.length != 3) {
                throw new MenuException("Incorrect input");
            }
            int firstId = Integer.parseInt(array[0]);
            int secondId = Integer.parseInt(array[1]);
            double payment = Double.parseDouble(array[2]);
            res.performTransaction(firstId, secondId, payment);
            System.out.println("The transfer is completed");
        } catch(Exception err) {
            System.err.println(err);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    public void viewAllTransactions() {
        System.out.println("Enter a user ID");
        String input = sc.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            Transaction[] transactions = res.geTransactions(id);
            if (transactions == null) {
                throw new RuntimeException("No id was input");
            }
            for (Transaction item : transactions) {
                String category = (item.getCategory() == transferCategory.DEBIT) ?
                        "From " :
                        "To ";
                User user = (item.getCategory() == transferCategory.DEBIT) ?
                        item.getSender() :
                        item.getRecipient();
                System.out.println(category + user.getName() + "(id = " + user.getId() + ") " +
                        item.getPayment() + " with id = " + item.getId());
            }
        } catch (Exception err) {
            System.out.println(err);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private void removeId() {
        System.out.println("Enter a user ID and a transfer ID");
        String input = sc.nextLine().trim();
        try {
            String[] array = input.split("\\s+");
            if (array.length != 2) {
                throw new MenuException("invalid data");
            }
            int userId = Integer.parseInt(array[0]);
            String transactionId = array[1];

            Transaction transaction = getTransaction(res.geTransactions(userId), transactionId);
            if (transaction == null) {
                throw new RuntimeException("No such transaction");
            }
            res.removeTransaction(userId, transactionId);
            User user = (transaction.getCategory() == transferCategory.DEBIT) ?
                    transaction.getSender() :
                    transaction.getRecipient();
            String category = (transaction.getCategory() == transferCategory.DEBIT) ?
                    "From " :
                    "To ";
            System.out.println("Transfer " + category + " " + user.getName() +
                    "(id = " + user.getId() + ") " + transaction.getPayment() + " removed");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("---------------------------------------------------------");
        }
    }

    private Transaction getTransaction(Transaction[] transactions, String id) {
        if (transactions == null) {
            throw new MenuException("Transaction with id = " + id + " not found");
        }
        for (Transaction item : transactions) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    private User getUser(Transaction transaction) {
        UsersArrayList listUsers = res.getList();

        for (int i = 0; i < listUsers.countUsers(); ++i) {
            Transaction[] listTrans = listUsers.byIndex(i).getTransactionsList().toArray();
            for (int j = 0; listTrans != null && j < listTrans.length; ++j) {
                if (listTrans[j].getId().equals(transaction.getId())) {
                    return listUsers.byIndex(i);
                }
            }
        }
        return null;
    }

    private void checkUniqueTransactions() {
        System.out.println("Check results:");
        Transaction[] transactions = res.findUniqTransactions();
        if (transactions != null) {
            for (Transaction item : transactions) {
                User userHolder = getUser(item);
                User userSender = (item.getCategory() == transferCategory.DEBIT) ?
                        item.getSender() :
                        item.getRecipient();
                String transactionId = item.getId();
                double amount = item.getPayment();
                System.out.println(userHolder.getName() + "(id = " + userHolder.getId() +
                        ") has an unacknowledged transfer id = " + transactionId + " from " +
                        userSender.getName() + "(id = " + userSender.getId() +
                        ") for " + amount);
            }
            System.out.println("---------------------------------------------------------");
            return;
        }
        System.out.println("There are no unpaired transactions");
        System.out.println("---------------------------------------------------------");
    }
}