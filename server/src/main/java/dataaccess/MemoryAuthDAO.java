package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryAuthDAO implements AuthDAO {
    public List<AuthData> Auths = new ArrayList<>();

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

    @Override
    public void clear() {
        for (int i = Auths.size()-1; i >= 0; i--){
            Auths.remove(i);
        }
        Auths = new ArrayList<>();
    }
}
