package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewGalaxie;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.model.Users;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 27/03/2017.
 */

public class FragmentGalaxy extends Fragment {
    // UI ELEMENTS
    private ListView topListe;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    // NETWORK
    private Retrofit retrofit;
    private String token;
    private Service service;

    // VARIABLES
    private ArrayList<User> listUser;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_galaxie,container,false);


        // INIT NETWORK
        SharedPreferences settings = getActivity().getSharedPreferences("token", 0);
        token = settings.getString("token", new String());
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);

        // INIT VARIABLES
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_galaxie_layout);
        topListe = (ListView) v.findViewById(R.id.list_galaxie);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setToolbarName("Galaxie");
        // EVENTS
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        // INIT ACTIVITY
        progressBar.setVisibility(View.VISIBLE);
        refreshList();
    }

    public void refreshList(){
        Call<Users> request = service.getUsers(token,0,20);
        request.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, retrofit2.Response<Users> response) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    listUser = response.body().getUsers();
                    topListe.setAdapter(new AdapterViewGalaxie(getActivity().getApplicationContext(), listUser));
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération de la Galaxie"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
