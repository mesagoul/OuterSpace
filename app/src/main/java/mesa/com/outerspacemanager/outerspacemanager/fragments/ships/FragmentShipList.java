package mesa.com.outerspacemanager.outerspacemanager.fragments.ships;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.interfaces.ChantierListener;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewChantier;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 14/03/2017.
 */

public class FragmentShipList extends Fragment {

    // UI ELEMENTS
    private ProgressBar progressBar;
    private ListView list_ships;
    private SwipeRefreshLayout refreshLayout;

    // VARIABLES
    private ArrayList<Ship> myShips;
    private Double currentUserMinerals;
    private Double currentUserGas;
    private ChantierListener listener;

    // NETWORK
    private String token;
    private Retrofit retrofit;
    private Service service;
    private SharedPreferences settings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_list,container);
        settings = getActivity().getSharedPreferences("token", 0);
        list_ships = (ListView) v.findViewById(R.id.list_items);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_items_layout);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        token = settings.getString("token", new String());
        service = retrofit.create(Service.class);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {refreshListShips();}
        });

        list_ships.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onShipClicked(myShips.get(position), getCurrentUserGas(),getCurrentUserMinerals());
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        refreshListShips();
    }

    public void refreshListShips(){

        Call<Ships> request = service.getShips(token);

        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    myShips = response.body().getShips();
                    currentUserGas = response.body().getCurrentUserGas();
                    currentUserMinerals = response.body().getCurrentUserMinerals();
                    listener.shipsFetched(myShips, currentUserMinerals, currentUserGas);
                    list_ships.setAdapter(new AdapterViewChantier(getContext(), myShips));
                } else {
                    Toast.makeText(getContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ships> call, Throwable t) {
                Toast.makeText(getContext(), String.format("Erreur lors de la récupération des vaisseaux"), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public ArrayList<Ship> getShips(){
        return this.myShips;
    }
    public Double getCurrentUserMinerals(){
        return this.currentUserMinerals;
    }
    public Double getCurrentUserGas(){
        return this.currentUserGas;
    }


    public void setListener(ChantierListener listener) {
        this.listener = listener;
    }
}

