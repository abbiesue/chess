package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryAuthDAO implements AuthDAO {
    private List<AuthData> Auths = new ArrayList<>();

    @Override
    public void createAuth(AuthData authdata) {
        Auths.add(authdata);
    }

    @Override
    public AuthData getAuth(String authToken) {
        if (Auths.isEmpty()) {
            return null;
        }
        for (int i=0; i<Auths.size(); i++) {
            if (Objects.equals(Auths.get(i).authToken(), authToken)) {
                return Auths.get(i);
            }
        }
        return null;
    }

    public AuthData getAuthByUsername(String username) {
        if (Auths.isEmpty()) {
            return null;
        } else {
            for (int i=0; i<Auths.size(); i++) {
                if (Auths.get(i).username() == username) {
                    return Auths.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        if (Auths.isEmpty() || authToken == null) {
            return;
        } else {
            for (int i=0; i<Auths.size(); i++) {
                if (Auths.get(i).authToken() == authToken) {
                    Auths.remove(i);
                }
            }
        }
    }
}
