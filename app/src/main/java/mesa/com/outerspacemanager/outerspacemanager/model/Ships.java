package mesa.com.outerspacemanager.outerspacemanager.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 07/03/2017.
 */

public class Ships {
    private ArrayList<Ship> ships;
    private int size;
    private Double currentUserMinerals;
    private Double currentUserGas;


    public ArrayList<Ship> getShips(){
        return this.ships;
    }
    public int getCountOfShips(){
        return this.size;
    }


    public Double getCurrentUserMinerals(){
        return this.currentUserMinerals;
    }
    public Double getCurrentUserGas(){
        return this.currentUserGas;
    }
}

