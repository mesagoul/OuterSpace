package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewSearche;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentPagerView;
import mesa.com.outerspacemanager.outerspacemanager.model.Searche;
import mesa.com.outerspacemanager.outerspacemanager.model.Searches;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 08/03/2017.
 */

public class FragmentSearche extends Fragment {

    // UI ELEMENTS
    private ListView list_searches;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    // NETWORK
    private String token;
    private Retrofit retrofit;
    private Service service;

    // VARIABLES
    private ArrayList<Searche> mySearches;
    private Searche currentSearche;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.activity_item_listview,container,false);
        // INIT UI ELEMENTS
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_items_layout);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        list_searches = (ListView) v.findViewById(R.id.list_items);

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
        ((MainActivity)getActivity()).setToolbarName("Recherches");
        list_searches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSearche = mySearches.get(position);
                onSearcheClick();
            }
        });

        // EVENTS
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshListSearches();
            }
        });

        // INIT ACTIVITY
        progressBar.setVisibility(View.VISIBLE);
        refreshListSearches();
    }

    // Refresh the listView of Searches
    public void refreshListSearches(){
        Call<Searches> request = service.getSearches(token);

        request.enqueue(new Callback<Searches>() {
            @Override
            public void onResponse(Call<Searches> call, Response<Searches> response) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mySearches = response.body().getSearches();
                    list_searches.setAdapter(new AdapterViewSearche(getActivity().getApplicationContext(), mySearches));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des recherches"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Searches> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des batiments"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // When an item on the listView is clicked
    public void onSearcheClick(){
        AlertDialog.Builder alerteDialog = new AlertDialog.Builder(getContext());
        alerteDialog.setMessage("Améliorer "+currentSearche.getName() + " ?");
        alerteDialog.setCancelable(true);

        alerteDialog.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Call<Searche> upgradeRequest = service.upgradeSearche(token, currentSearche.getSearcheId());
                        upgradeRequest.enqueue(new Callback<Searche>() {
                            @Override
                            public void onResponse(Call<Searche> call, Response<Searche> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(getActivity().getApplicationContext(), String.format("En construction"), Toast.LENGTH_SHORT).show();
                                    ((MainActivity)getActivity()).refreshUser();
                                }else{
                                    Toast.makeText(getActivity().getApplicationContext(), String.format("Votre recherche est déja en construction"), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Searche> call, Throwable t) {
                                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de l'amélioration de votre recherche"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        refreshListSearches();
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
