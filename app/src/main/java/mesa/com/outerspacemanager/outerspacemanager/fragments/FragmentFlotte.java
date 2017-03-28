package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.GalaxieActivity;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewFleet;
import mesa.com.outerspacemanager.outerspacemanager.db.AttackDataSource;
import mesa.com.outerspacemanager.outerspacemanager.db.ShipDataSource;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentPagerView;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.AttackResponse;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lucas on 15/03/2017.
 */

public class FragmentFlotte extends Fragment {

    // UI ELEMENTS
    private Button buttonAttaque;
    private ListView listView;
    private ProgressBar progressBar;

    // NETWORK
    private String token;
    private Retrofit retrofit;
    private Service service;

    // VARIABLES
    private Attack attack;
    private Ships shipsAttaque;
    private String usernameAttaque;
    private ArrayList<Ship> myShips;

    // DATABASE
    private AttackDataSource db;

    private int USERNAME = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_fleet,container,false);
        // INIT VARIABLES
        listView = (ListView) v.findViewById(R.id.list_fleet);
        buttonAttaque = (Button) v.findViewById(R.id.button_attaque);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);


        // INIT NETWORK
        SharedPreferences settings = getActivity().getSharedPreferences("token", 0);
        token = settings.getString("token", new String());
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setToolbarName("Flotte");
        // EVENTS
        buttonAttaque.setText("Attaquer ! ");
        buttonAttaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListShipToAttack();
                Intent toUsersActivity = new Intent(getActivity().getApplicationContext(),GalaxieActivity.class);
                toUsersActivity.putExtra("attaque",true);
                startActivityForResult(toUsersActivity, USERNAME);
            }
        });

        // INIT ACTIVITY
        progressBar.setVisibility(View.VISIBLE);
        refreshListShips();

    }

    // GET the list of ships to attack
    public void getListShipToAttack(){
        shipsAttaque = new Ships();
        ShipDataSource dbShip = new ShipDataSource(getContext());
        dbShip.open();
        for (int i = 0; i < listView.getCount(); i++) {
            //GET item as a Ship
            Ship ship = (Ship) listView.getItemAtPosition(i);
            dbShip.createShip(ship);

            // GET amount of ship
            AdapterViewFleet adapter = (AdapterViewFleet) listView.getAdapter();
            HashMap<Integer,Integer> map = adapter.getHashMap();

            // Create new Ship
            Ship newShip = new Ship(ship.getShipId(),Integer.parseInt(String.valueOf(map.get(ship.getShipId()))));

            // if ship haven't amount, not get it
            if(newShip.getAmount() > 0){
                shipsAttaque.addShip(newShip);
            }
        }
        dbShip.close();
    }

    // REFRESH list of ships
    public void refreshListShips(){
        Call<Ships> request = service.getMyShips(token);

        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    myShips = response.body().getShips();
                    listView.setAdapter(new AdapterViewFleet(getActivity().getApplicationContext(), myShips));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ships> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ATTACK an USER (REQUEST TO SERVER)
    public void attaqueUser(){
        db = new AttackDataSource(getActivity().getApplicationContext());
        db.open();
        attack = new Attack();
        attack.setUsername(usernameAttaque);
        attack.setShips(shipsAttaque);
        Call<AttackResponse> request = service.attaqueUser(token, usernameAttaque, shipsAttaque);

        request.enqueue(new Callback<AttackResponse>() {
            @Override
            public void onResponse(Call<AttackResponse> call, Response<AttackResponse> response) {
                if (response.isSuccessful()) {
                    attack.setAttack_time(response.body().getAttackTime());
                    db.createAttack(attack);
                    db.close();
                    Toast.makeText(getActivity().getApplicationContext(), String.format("Votre flotte à été envoyée au combat"), Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).loadNewFragment(new FragmentPagerView());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttackResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USERNAME) {
            if (resultCode == RESULT_OK) {
                usernameAttaque = data.getStringExtra("username");
                attaqueUser();
            }
        }
    }
}
