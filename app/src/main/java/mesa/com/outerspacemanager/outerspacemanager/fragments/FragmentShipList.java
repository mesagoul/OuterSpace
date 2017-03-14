package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.ChantierActivity;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
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

public class FragmentShipList extends Fragment {
    private LoaderProgressBar progress;
    private ListView list_ships;
    private String token;
    private Retrofit retrofit;
    private Service service;
    private ArrayList<Ship> myShips;
    private Double currentUserMinerals;
    private Double currentUserGas;
    private SharedPreferences settings;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_list,container);
        progress = new LoaderProgressBar(getContext());
        settings = getActivity().getSharedPreferences("token", 0);
        list_ships = (ListView) v.findViewById(R.id.list_items);
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

        list_ships.setOnItemClickListener((ChantierActivity)getActivity());

        progress.show();
        refreshListShips();
    }

    public void refreshListShips(){

        Call<Ships> request = service.getShips(token);

        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                refreshLayout.setRefreshing(false);
                progress.dismiss();
                if (response.isSuccessful()) {
                    myShips = response.body().getShips();
                    currentUserGas = response.body().getCurrentUserGas();
                    currentUserMinerals = response.body().getCurrentUserMinerals();
                    ((ChantierActivity) getActivity()).shipsFetched(myShips, currentUserMinerals, currentUserGas);
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

}

