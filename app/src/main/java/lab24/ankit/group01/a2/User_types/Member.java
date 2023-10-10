package lab24.ankit.group01.a2.User_types;

import org.json.simple.JSONObject;

public class Member extends User {

    public Member(JSONObject member) {
        super(member);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

}