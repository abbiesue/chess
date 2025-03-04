package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{
    private List<UserData> Users;

    public MemoryUserDAO() {
        Users = new ArrayList<>();
    }

    @Override
    public void createUser(UserData userData) {
        //implement for register service method
        Users.add(userData);
    }

    @Override
    public UserData getUser(String username) {
        if (Users.isEmpty()){
            return null;
        } else {
            for (int i=0; i<Users.size(); i++) {
                if (Objects.equals(Users.get(i).username(), username)) {
                    return Users.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void deleteUser(String username) {

    }
}
