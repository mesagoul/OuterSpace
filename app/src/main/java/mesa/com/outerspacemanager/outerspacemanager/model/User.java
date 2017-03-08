package mesa.com.outerspacemanager.outerspacemanager.model;

import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class User {
    private String username;
    private String password;
    private String token;
    private Float gas;
    private Integer gasModifier;
    private Float minerals;
    private Integer mineralsModifier;
    private Integer points;


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
    public Float getGas(){
        return this.gas;
    }
    public Integer getGasModifier(){
        return this.gasModifier;
    }
    public Float getMinerals(){
        return this.minerals;
    }
    public Integer getMineralsModifier(){
        return this.mineralsModifier;
    }
    public Integer getPoints(){
        return this.points;
    }
}
