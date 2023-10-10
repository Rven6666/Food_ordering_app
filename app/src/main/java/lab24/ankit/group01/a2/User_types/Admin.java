package lab24.ankit.group01.a2.User_types;

import org.json.simple.JSONObject;

public class Admin extends User {

    public Admin(JSONObject admin) {
        super(admin);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

}