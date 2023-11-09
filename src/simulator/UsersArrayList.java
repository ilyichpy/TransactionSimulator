package simulator;

public class UsersArrayList implements UsersList {
    public static int capacity = 10;
    public static int currentPlace = 0;
    private User[] userArray = new User[10];

    public void addUser(User add) {
        if (currentPlace == capacity - 1) {
            capacity += 10;
            User tmpArray[] = new User[capacity];
            System.arraycopy(userArray, 0, tmpArray, 0, userArray.length);
            userArray = tmpArray;
        }
        userArray[currentPlace] = add;
        ++currentPlace;
    }
    @Override
    public User findId(int id) {
        boolean flag = false;
        User result = null;
        for (int i = 0; i < currentPlace; ++i) {
            if (userArray[i].getId() == id) {
                flag = true;
                result = userArray[i];
                break;
            }
        }
        if (!flag) {
            throw new UserNotFoundException("no user with this id");
        }
        return result;
    }

    @Override
    public User byIndex(int index) {
        if (index > capacity - 1) {
            throw new UserNotFoundException("err");
        }
        return userArray[index];
    }

    @Override
    public int countUsers() {
        return currentPlace;
    }
}