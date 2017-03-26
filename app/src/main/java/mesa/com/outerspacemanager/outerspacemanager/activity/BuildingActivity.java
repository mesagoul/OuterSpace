package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewBuilding;
import mesa.com.outerspacemanager.outerspacemanager.model.Building;
import mesa.com.outerspacemanager.outerspacemanager.model.Buildings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class BuildingActivity extends AppCompatActivity{
    // UI ELEMENTS
    private ListView list_buildings;
    private ProgressBar progressBar;

    // VARIABLES
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Building> myBuildings;
    private Building currentBuilding;

    // NETWORK
    private Retrofit retrofit;
    private Service service;
    private String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_listview);


        // INIT UI ELEMENTS
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        list_buildings = (ListView) findViewById(R.id.list_items);

        // INIT BACK ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // REFRESH ON SWIPE DOWN
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_items_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListBuildings();
            }
        });

        // INIT NETWORK
        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // EVENTS
        list_buildings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentBuilding = myBuildings.get(position);
                onBuildingClick();
            }
        });
        service = retrofit.create(Service.class);

        // INIT ACTIVITY
        progressBar.setVisibility(View.VISIBLE);
        refreshListBuildings();
    }
    // ON BACK ARROW PRESSED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // REFRESH LIST OF BUILDINGS
    public void refreshListBuildings(){
        Call<Buildings> request = service.getBuildings(token);

        request.enqueue(new Callback<Buildings>() {
            @Override
            public void onResponse(Call<Buildings> call, Response<Buildings> response) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    myBuildings = response.body().getBuildings();
                    list_buildings.setAdapter(new AdapterViewBuilding(getApplicationContext(), myBuildings));
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des batiments"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Buildings> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des batiments"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // When an item on the listView is clicked
    public void onBuildingClick(){
        AlertDialog.Builder alerteDialog = new AlertDialog.Builder(this);
        alerteDialog.setMessage("Améliorer "+currentBuilding.getName() + " ?");
        alerteDialog.setCancelable(true);

        alerteDialog.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Call<Building> upgradeRequest = service.upgradeBuilding(token, currentBuilding.getBuildingId());
                        upgradeRequest.enqueue(new Callback<Building>() {
                            @Override
                            public void onResponse(Call<Building> call, Response<Building> response) {

                                if(response.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), String.format("En construction"), Toast.LENGTH_SHORT).show();
                                    setResult(2);
                                    finish();
                                }else{
                                    //responses = gson.fromJson(response.errorBody().toString(), Responses.class);
                                    Toast.makeText(getApplicationContext(), String.format("Votre batiment est déja en construction"), Toast.LENGTH_SHORT).show();
                                }
                                //response.errorBody().string()

                            }

                            @Override
                            public void onFailure(Call<Building> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), String.format("Erreur lors de l'upgrade de votre batiment"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        refreshListBuildings();
                    }
                });

        alerteDialog.setNegativeButton(
                "Non",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog upgradeDialog = alerteDialog.create();
        upgradeDialog.show();
    }
}
