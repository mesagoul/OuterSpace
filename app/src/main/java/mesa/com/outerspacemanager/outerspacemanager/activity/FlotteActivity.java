package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewChantier;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.utils.LoaderProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 14/03/2017.
 */

public class FlotteActivity extends Activity {

    private LoaderProgressBar progress;
    private ListView list_ships;
    private String token;
    private Retrofit retrofit;
    private Service service;
    private ArrayList<Ship> myShips;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_item_listview);
        progress = new LoaderProgressBar(this);
        progress.show();

        list_ships = (ListView) findViewById(R.id.list_items);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_items_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListShips();

            }
        });
        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);


        list_ships.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // currentBuilding = myBuildings.get(position);
                // onBuildingClick();
            }
        });

        refreshListShips();
    }
    public void refreshListShips(){

        Call<Ships> request = service.getMyShips(token);

        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                refreshLayout.setRefreshing(false);
                progress.dismiss();
                if (response.isSuccessful()) {
                    myShips = response.body().getShips();
                    list_ships.setAdapter(new AdapterViewChantier(getApplicationContext(), myShips));
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
}
