package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;

public class MemoryAuthDAO implements AuthDAO {
    private List<AuthData> Auths = new ArrayList<>();

    @Override
    public void createAuth(AuthData authdata) {
        Auths.add(authdata);
    }

    @Override
    public void getAuth() {

    }

    @Override
    public void deleteAuth() {

    }
}
