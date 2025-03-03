package service;

import dataaccess.MemoryUserDAO;
import server.ResponseException;

public class UserService {
    public static RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        MemoryUserDAO dao = new MemoryUserDAO();
        if (registerRequest.username == null || registerRequest.email == null || registerRequest.password == null) {
            throw new ResponseException(400, "Error: bad request");
        }
        if (dao.getUser(registerRequest.username) != null) {
            throw new ResponseException(403, "Error: already taken");
        }
        //implement
        return null;
    }
    public LoginResult login(LoginRequest loginRequest){
        //implement
        return null;
    }
    public void logout(LogoutRequest logoutRequest) {
        //implement
    }

    //records:
    public record RegisterRequest(String username, String password, String email) {}
    public record RegisterResult(String username, String authToken) {}
    public record LoginRequest(String username, String password) {}
    public record LoginResult(String username, String authToken) {}
    public record LogoutRequest(String authToken) {}
};
