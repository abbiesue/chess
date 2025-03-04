package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void createAuth(AuthData authdata);
    AuthData getAuth(String authtoken);
    void deleteAuth(String authToken);
    void clear();
}
