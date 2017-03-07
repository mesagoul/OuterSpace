package mesa.com.outerspacemanager.outerspacemanager;

/**
 * Created by Lucas on 06/03/2017.
 */

public class Building {
    private Integer amountOfEffectByLevel;
    private Integer amountOfEffectLevel0 ;
    private String effect;
    private Integer gasCostByLevel0;
    private Integer gasCostLevel0 ;
    private Integer mineralCostByLevel;
    private Integer mineralCostLevel0 ;
    private String name;
    private Integer timeToBuildByLevel;
    private Integer timeToBuildLevel0;

    public String getName(){
        return this.name;
    }
}
