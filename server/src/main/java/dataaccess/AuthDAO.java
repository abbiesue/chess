package dataaccess;

import model.AuthData;
import server.ResponseException;

public interface AuthDAO {
    void createAuth(AuthData authdata) throws ResponseException;
    AuthData getAuth(String authtoken) throws ResponseException;
    void deleteAuth(String authToken) throws ResponseException;
    void clear() throws ResponseException, DataAccessException;
}
