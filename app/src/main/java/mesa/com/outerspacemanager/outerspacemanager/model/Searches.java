package mesa.com.outerspacemanager.outerspacemanager.model;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/03/2017.
 */

public class Searches {
    private Integer size;
    private ArrayList<Searche> searches;


    public ArrayList<Searche> getSearches(){
        return this.searches;
    }

    public Integer getSize(){
        return  this.size;
    }
}
