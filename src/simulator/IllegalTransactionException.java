package simulator;

class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException(String s) {
        super(s);
    }
}