package mesa.com.outerspacemanager.outerspacemanager.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 07/03/2017.
 */

public class Ships {
    private ArrayList<Ship> ships;
    private int size;
    public ArrayList<Ship> getShips(){
        return this.ships;
    }
    public int getCountOfShips(){
        return this.size;
    }
}

