package mesa.com.outerspacemanager.outerspacemanager.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lucas on 08/03/2017.
 */

public class Ship implements Serializable {
    private int capacity;
    private int gasCost;
    private int life;
    private int maxAttack;
    private int minAttack;
    private int mineralCost;
    private String name;
    private int shield;
    private int shipId;
    private int spatioportLevelNeeded;
    private int speed;
    private int timeToBuild;
    private int amount;
    private ArrayList<String> urlImages;

    public Ship(int id, int amount){
        this.shipId = id;
        this.amount = amount;
    }
    public Ship(int id, String name){
        this.shipId = id;
        this.name = name;
    }

    public String getUrlImage(){
        this.urlImages = new ArrayList<String>();
        this.urlImages.add("https://cdn.pixabay.com/photo/2014/09/11/12/45/science-fiction-441708_960_720.jpg");
        this.urlImages.add("https://cdn.pixabay.com/photo/2015/08/28/16/20/eagle-912122_960_720.jpg");
        this.urlImages.add("https://cdn.pixabay.com/photo/2017/02/11/10/33/spaceship-2057420_960_720.jpg");
        this.urlImages.add("https://cdn.pixabay.com/photo/2015/12/11/22/25/star-wars-1088872_960_720.jpg");
        this.urlImages.add("https://cdn.pixabay.com/photo/2015/03/26/18/36/spacex-693229_960_720.jpg");
        return this.urlImages.get(this.shipId);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getGasCost() {
        return gasCost;
    }

    public int getLife() {
        return life;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public String getName() {
        return name;
    }

    public int getShield() {
        return shield;
    }

    public int getShipId() {
        return shipId;
    }

    public int getSpatioportLevelNeeded() {
        return spatioportLevelNeeded;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public int getAmount() {
        return this.amount;
    }


}
