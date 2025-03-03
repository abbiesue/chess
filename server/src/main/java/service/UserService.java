package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import records.*;
import server.ResponseException;

import java.util.UUID;

public class UserService {
    public static MemoryUserDAO userDAO;
    public static MemoryAuthDAO authDAO;

    public UserService() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
    }

    public RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        //throw exceptions
        if (registerRequest.username() == null || registerRequest.email() == null || registerRequest.password() == null) {
            throw new ResponseException(400, "Error: bad request");
        }
        if (userDAO.getUser(registerRequest.username()) != null) {
            throw new ResponseException(403, "Error: already taken");
        }
        //create a new user
        UserData newUser = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        userDAO.createUser(newUser);
        //create auth data
        AuthData authData = new AuthData(generateToken(), registerRequest.username());
        authDAO.createAuth(authData);
        //return successful result
        return new RegisterResult(authData.username(), authData.authToken());
    }

    public LoginResult login(LoginRequest loginRequest){
        //implement
        return null;
    }

    public void logout(LogoutRequest logoutRequest) {
        //implement
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
};

