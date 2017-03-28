package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.interfaces.ChantierListener;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.activity.ShipDetailActivity;
import mesa.com.outerspacemanager.outerspacemanager.fragments.ships.FragmentShipList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.ships.Fragment_ship_detail;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;


/**
 * Created by Lucas on 13/03/2017.
 */

public class FragmentChantier extends Fragment implements ChantierListener {
    private FragmentShipList fragment_itemList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_chantier,container, false);
        fragment_itemList = (FragmentShipList) getChildFragmentManager().findFragmentById(R.id.fragment_ship_list);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragment_itemList.setListener(this);
        ((MainActivity)getActivity()).setToolbarName("Chantier naval");
        super.onViewCreated(view, savedInstanceState);
    }

    // When an user click on a ship
    @Override
    public void onShipClicked(Ship ship, Double gas, Double mineral) {
        Fragment_ship_detail fragment_ship_detail = (Fragment_ship_detail) getChildFragmentManager().findFragmentById(R.id.fragment_ship_detail);
        if (fragment_ship_detail == null || !fragment_ship_detail.isInLayout()) {
            Intent i = new Intent(getActivity().getApplicationContext(), ShipDetailActivity.class);
            i.putExtra("ship", ship);
            i.putExtra("currentUserMinreals", mineral);
            i.putExtra("currentUserGas", gas);
            startActivityForResult(i,0);
        } else {
            fragment_ship_detail.setShip(ship);
            fragment_ship_detail.updateSeekBarMax(mineral,gas);
        }
    }

    @Override
    public void shipsFetched(ArrayList<Ship> myShips, Double minerals, Double gas) {
        Fragment_ship_detail fragment_ship_detail = (Fragment_ship_detail) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_ship_detail);
        if (fragment_ship_detail != null && fragment_ship_detail.isInLayout()) {
            fragment_ship_detail.setShip(myShips.get(0));
            fragment_ship_detail.updateSeekBarMax(minerals,gas);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode  == 1){
            ((MainActivity)getActivity()).refreshUser();
            ((MainActivity)getActivity()).loadNewFragment(new FragmentPagerView());
        }
    }
}
