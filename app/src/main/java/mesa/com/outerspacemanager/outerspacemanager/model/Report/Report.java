package mesa.com.outerspacemanager.outerspacemanager.model.Report;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

/**
 * Created by Lucas on 21/03/2017.
 */

public class Report {
    private ArrayList<Ship> attackerFleet;
    private FleetAfterBattle attackerFleetAfterBattle;
    private long date;
    private long dateInv;
    private ArrayList<Ship> defenderFleet;
    private FleetAfterBattle defenderFleetAfterBattle;
    private String from;
    private Double gasWon;
    private Double mineralsWon;
    private String to;
    private String type;

    public ArrayList<Ship> getAttackerFleet() {
        return attackerFleet;
    }

    public void setAttackerFleet(ArrayList<Ship> attackerFleet) {
        this.attackerFleet = attackerFleet;
    }

    public FleetAfterBattle getAttackerFleetAfterBattle() {
        return attackerFleetAfterBattle;
    }

    public void setAttackerFleetAfterBattle(FleetAfterBattle attackerFleetAfterBattle) {
        this.attackerFleetAfterBattle = attackerFleetAfterBattle;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDateInv() {
        return dateInv;
    }

    public void setDateInv(long dateInv) {
        this.dateInv = dateInv;
    }

    public ArrayList<Ship> getDefenderFleet() {
        return defenderFleet;
    }

    public void setDefenderFleet(ArrayList<Ship> defenderFleet) {
        this.defenderFleet = defenderFleet;
    }

    public FleetAfterBattle getDefenderFleetAfterBattle() {
        return defenderFleetAfterBattle;
    }

    public void setDefenderFleetAfterBattle(FleetAfterBattle defenderFleetAfterBattle) {
        this.defenderFleetAfterBattle = defenderFleetAfterBattle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Double getGasWon() {
        return gasWon;
    }

    public void setGasWon(Double gasWon) {
        this.gasWon = gasWon;
    }

    public Double getMineralsWon() {
        return mineralsWon;
    }

    public void setMineralsWon(Double mineralsWon) {
        this.mineralsWon = mineralsWon;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
