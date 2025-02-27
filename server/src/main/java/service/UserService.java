package service;

public class UserService {
    public RegisterResult register(RegisterRequest registerRequest){
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
