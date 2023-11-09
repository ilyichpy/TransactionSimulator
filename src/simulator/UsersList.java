package simulator;

public interface UsersList {
    public void addUser(User add);
    public User findId(int id);
    public User byIndex(int index);
    public int countUsers();

}