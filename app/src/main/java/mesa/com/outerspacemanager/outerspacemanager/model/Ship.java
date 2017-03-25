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
        this.urlImages.add("https://s-media-cache-ak0.pinimg.com/originals/e0/3e/70/e03e7026394c97aab0a52a5a4283d24b.jpg");
        this.urlImages.add("http://www.vive-internet-gratuit.com/images/dessins/Vaisseau-spatial_49.jpg");
        this.urlImages.add("http://poopss.p.o.pic.centerblog.net/o/e04b1581.jpg");
        this.urlImages.add("http://www.stargate-fusion.com/images/news/divers/x301-schema-grand.jpg");
        this.urlImages.add("https://www.quizz.biz/uploads/quizz/140918/2_D9Z1T.jpg");
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
