package dataaccess;

import model.UserData;

public interface UserDAO {
    void createUser();
    public UserData getUser(String username);
    void deleteUser();
}
