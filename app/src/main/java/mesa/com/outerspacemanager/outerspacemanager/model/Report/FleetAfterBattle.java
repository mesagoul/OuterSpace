package mesa.com.outerspacemanager.outerspacemanager.model.Report;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

/**
 * Created by Lucas on 21/03/2017.
 */

public class FleetAfterBattle {
    private int capacity;
    private ArrayList<Ship> fleet;
    private int survivingShips;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        this.fleet = fleet;
    }

    public int getSurvivingShips() {
        return survivingShips;
    }

    public void setSurvivingShips(int survivingShips) {
        this.survivingShips = survivingShips;
    }
}
