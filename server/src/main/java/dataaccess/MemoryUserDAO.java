package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDAO implements UserDAO{
    private List<UserData> Users = new ArrayList<>();

    @Override
    public void createUser() {

    }

    @Override
    public void getUser() {

    }

    @Override
    public void deleteUser() {

    }
}
