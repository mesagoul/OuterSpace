package mesa.com.outerspacemanager.outerspacemanager;

/**
 * Created by Lucas on 06/03/2017.
 */

public class User {
    private String userame;
    private String password;

    public User(String username, String password){
        this.userame = username;
        this.password = password;
    }

    public String getUsername(){
        return this.userame;
    }
}
