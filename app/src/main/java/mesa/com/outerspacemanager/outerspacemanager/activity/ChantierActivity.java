package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentShipList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.Fragment_ship_detail;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;


/**
 * Created by Lucas on 13/03/2017.
 */

public class ChantierActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chantier);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentShipList fragment_itemList = (FragmentShipList) getSupportFragmentManager().findFragmentById(R.id.fragment_ship_list);
        Fragment_ship_detail fragment_ship_detail = (Fragment_ship_detail) getSupportFragmentManager().findFragmentById(R.id.fragment_ship_detail);
        if (fragment_ship_detail == null || !fragment_ship_detail.isInLayout()) {
            Intent i = new Intent(getApplicationContext(), ShipDetailActivity.class);
            i.putExtra("ship", fragment_itemList.getShips().get(position));
            i.putExtra("currentUserMinreals", fragment_itemList.getCurrentUserMinerals());
            i.putExtra("currentUserGas", fragment_itemList.getCurrentUserGas());
            startActivityForResult(i,0);
        } else {
            fragment_ship_detail.setShip(fragment_itemList.getShips().get(position));
            fragment_ship_detail.updateSeekBarMax(fragment_itemList.getCurrentUserMinerals(), fragment_itemList.getCurrentUserGas());
        }
    }

    public void shipsFetched(ArrayList<Ship> myShips, Double minerals, Double gas) {
        Fragment_ship_detail fragment_ship_detail = (Fragment_ship_detail) getSupportFragmentManager().findFragmentById(R.id.fragment_ship_detail);
        if (fragment_ship_detail != null && fragment_ship_detail.isInLayout()) {
            fragment_ship_detail.setShip(myShips.get(0));
            fragment_ship_detail.updateSeekBarMax(minerals,gas);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != 0){
            setResult(2);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
