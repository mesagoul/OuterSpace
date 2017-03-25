package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.Fragment_ship_detail;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

/**
 * Created by Lucas on 14/03/2017.
 */

public class ShipDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);
        Ship aShip = (Ship) getIntent().getExtras().getSerializable("ship");
        Double minerals =  (Double) getIntent().getExtras().getSerializable("currentUserMinreals");
        Double gas =  (Double) getIntent().getExtras().getSerializable("currentUserGas");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Fragment_ship_detail fragment_ship_detail = (Fragment_ship_detail)getSupportFragmentManager().findFragmentById(R.id.fragment_ship_detail);
        fragment_ship_detail.setShip(aShip);
        fragment_ship_detail.updateSeekBarMax(minerals,gas);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
