package BusinessLogicLayer;

import DataAccessLayer.DALLogin;
import Entity.EntityLogin;

public class BLLLogin {
    private DALLogin dalLogin = new DALLogin();

    public String validateLogin(String username, String password) {
        EntityLogin entity = dalLogin.login(username, password);
        if (entity != null) {
            return entity.getRole();
        }
        return null;
    }
}
