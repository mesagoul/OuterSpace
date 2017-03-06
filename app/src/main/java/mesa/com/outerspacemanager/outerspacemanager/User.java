package mesa.com.outerspacemanager.outerspacemanager;

/**
 * Created by Lucas on 06/03/2017.
 */

public class User {
    private String username;
    private String password;
    private String token;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }
    public String getToken(){
        return this.token;
    }
}
