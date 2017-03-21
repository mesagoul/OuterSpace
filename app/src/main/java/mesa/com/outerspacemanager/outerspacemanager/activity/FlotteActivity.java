package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewFleet;
import mesa.com.outerspacemanager.outerspacemanager.db.AttackSataSource;
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

/**
 * Created by Lucas on 15/03/2017.
 */

public class FlotteActivity extends Activity {
    private Attack attack;
    private  AttackSataSource db;
    private Button buttonAttaque;
    private ListView listView;
    private String token;
    private Retrofit retrofit;
    private Service service;
    private ArrayList<Ship> myShips;

    private Ships shipsAttaque;
    private String usernameAttaque;

    private int USERNAME = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);

        listView = (ListView) findViewById(R.id.list_fleet);
        buttonAttaque = (Button) findViewById(R.id.button_attaque);

        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);


        buttonAttaque.setText("Attaquer ! ");
        buttonAttaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getShipsAttaque();
                Intent toUsersActivity = new Intent(getApplicationContext(),GalaxieActivity.class);
                toUsersActivity.putExtra("attaque",true);
                startActivityForResult(toUsersActivity, USERNAME);
            }
        });

        refreshListShips();
    }

    public void getShipsAttaque(){
        shipsAttaque = new Ships();
        for (int i = 0; i < listView.getCount(); i++) {
            int a = listView.getCount();
            //get SHIP OBJECT
            Ship ship = (Ship) listView.getItemAtPosition(i);

            // GET amount of ship
            View view = listView.getChildAt(i);
            TextView amount = (TextView) view.findViewById(R.id.ship_amount);

            // Create Ship
            Ship newShip = new Ship(ship.getShipId(),Integer.parseInt(amount.getText().toString()));

            shipsAttaque.addShip(newShip);
        }

    }
    public void refreshListShips(){

        Call<Ships> request = service.getMyShips(token);

        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                if (response.isSuccessful()) {
                    myShips = response.body().getShips();
                    listView.setAdapter(new AdapterViewFleet(getApplicationContext(), myShips));
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ships> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void attaqueUser(){
        db = new AttackSataSource(getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), String.format("Votre flotte à été envoyée au combat"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttackResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USERNAME) {
            if (resultCode == RESULT_OK) {
                usernameAttaque = data.getStringExtra("username");
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                }else{
                    attaqueUser();
                }
                finish();

            }
        }
    }
}
