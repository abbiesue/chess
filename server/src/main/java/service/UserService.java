package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import records.*;
import server.ResponseException;

import java.util.Objects;
import java.util.UUID;

public class UserService {
    public MemoryUserDAO userDAO;
    public MemoryAuthDAO authDAO;

    public UserService(MemoryUserDAO userDAO, MemoryAuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
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

    public LoginResult login(LoginRequest loginRequest) throws ResponseException {
        //store userData
        UserData userData = userDAO.getUser(loginRequest.username());
        //throw exceptions
        if (userData == null || !Objects.equals(userData.password(), loginRequest.password())) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        AuthData authData = new AuthData(generateToken(), loginRequest.username());
        authDAO.createAuth(authData);
        return new LoginResult(authData.username(), authData.authToken());
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws ResponseException {
        //store AuthData
        AuthData authData = authDAO.getAuth(logoutRequest.authToken());
        //throw exceptions
        if (authData == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        authDAO.deleteAuth(authData.authToken());
        return new LogoutResult();
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}

