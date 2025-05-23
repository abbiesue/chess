package dataaccess;

import model.UserData;
import exceptions.ResponseException;

public interface UserDAO {
    void createUser(UserData userData) throws ResponseException;
    UserData getUser(String username) throws ResponseException;
    void clear() throws ResponseException, DataAccessException;
}
