package mesa.com.outerspacemanager.outerspacemanager.interfaces;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

/**
 * Created by Lucas on 27/03/2017.
 */

public interface ChantierListener {
    void onShipClicked(Ship ship, Double gas, Double mineral);
    void shipsFetched(ArrayList<Ship> myShips, Double minerals, Double gas);
}
