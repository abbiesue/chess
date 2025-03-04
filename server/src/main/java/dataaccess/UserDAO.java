package dataaccess;

import model.UserData;

public interface UserDAO {
    void createUser(UserData userData);
    UserData getUser(String username);
    void deleteUser(String username);
    void clear();
}
