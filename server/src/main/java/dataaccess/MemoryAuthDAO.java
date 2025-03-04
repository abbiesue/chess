package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryAuthDAO implements AuthDAO {
    public List<AuthData> auths = new ArrayList<>();

    @Override
    public void createAuth(AuthData authdata) {
        auths.add(authdata);
    }

    @Override
    public AuthData getAuth(String authToken) {
        if (auths.isEmpty()) {
            return null;
        }
        for (int i = 0; i< auths.size(); i++) {
            if (Objects.equals(auths.get(i).authToken(), authToken)) {
                return auths.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        if (auths.isEmpty() || authToken == null) {
            return;
        } else {
            for (int i = 0; i< auths.size(); i++) {
                if (auths.get(i).authToken() == authToken) {
                    auths.remove(i);
                }
            }
        }
    }

    @Override
    public void clear() {
        for (int i = auths.size()-1; i >= 0; i--){
            auths.remove(i);
        }
        auths = new ArrayList<>();
    }
}
