package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{
    public List<UserData> users = new ArrayList<>();;

    public MemoryUserDAO() {
        users = new ArrayList<>();
    }

    @Override
    public void createUser(UserData userData) {
        //implement for register service method
        users.add(userData);
    }

    @Override
    public UserData getUser(String username) {
        if (users.isEmpty()){
            return null;
        } else {
            for (int i = 0; i< users.size(); i++) {
                if (Objects.equals(users.get(i).username(), username)) {
                    return users.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void deleteUser(String username) {
        if (!users.isEmpty()){
            for (int i = 0; i< users.size(); i++) {
                if (Objects.equals(users.get(i).username(), username)) {
                    users.remove(i);
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i = users.size()-1; i >= 0; i--){
            users.remove(i);
        }
        users = new ArrayList<>();
    }
}
