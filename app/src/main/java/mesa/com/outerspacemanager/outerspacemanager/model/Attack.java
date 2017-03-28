package mesa.com.outerspacemanager.outerspacemanager.model;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Lucas on 20/03/2017.
 */

public class Attack {

    private UUID id;
    private Ships ships;
    private String username;
    private long attack_time;
    private long begin_attack_time;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Ships getShips() {
        return ships;
    }

    public void setShips(Ships ships) {
        this.ships = ships;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAttack_time() {
        return attack_time;
    }

    public void setAttack_time(long attack_time) {
        this.attack_time = attack_time;
    }
    public long getBegin_attack_time() {
        return begin_attack_time;
    }
    public void setBegin_attack_time(long attack_time) {
        this.begin_attack_time = attack_time;
    }

    public int getProgress(){
        return Integer.parseInt(String.valueOf(((System.currentTimeMillis()-begin_attack_time) * 100)/(attack_time-begin_attack_time)));
    }
}
