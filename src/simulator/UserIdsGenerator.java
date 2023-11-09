package simulator;

public class UserIdsGenerator {
    private static UserIdsGenerator generator;
    private static int idCounter = -1;
    public static UserIdsGenerator getInstance() {
        if (generator == null) {
            generator = new UserIdsGenerator();
        }
        return generator;
    }
    public int generateId() {
        return ++idCounter;
    }
}