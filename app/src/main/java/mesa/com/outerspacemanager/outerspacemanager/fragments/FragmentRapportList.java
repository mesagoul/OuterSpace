package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewReports;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Reports;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 20/03/2017.
 */

public class FragmentRapportList extends Fragment {
    // UI ELEMENTS
    private RecyclerView rvRapport;
    private ProgressBar progressBar;

    // NETWORK
    private Retrofit retrofit;
    private Service service;
    private SharedPreferences settings;
    private String token;

    // VARIABLES
    private ArrayList<Report> listReports;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_generals_list,container, false);
        rvRapport = (RecyclerView) v.findViewById(R.id.generals);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        listReports = new ArrayList<Report>();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRapport.setLayoutManager(new LinearLayoutManager(getContext()));

        // NETWORK
        settings = getActivity().getSharedPreferences("token", 0);
        token = settings.getString("token", new String());
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        getRapports();
    }

    public void getRapports(){
        if(listReports.size() == 0){
            loadReports();
        }else{
            progressBar.setVisibility(View.GONE);
            AdapterViewReports adapter = new AdapterViewReports(getContext(), listReports);
            adapter.setListener((MainActivity)getActivity());
            rvRapport.setAdapter(adapter);
        }
    }

    public void loadReports(){
        Call<Reports> request = service.getReports(token,"0","20");
        request.enqueue(new Callback<Reports>() {
            @Override
            public void onResponse(Call<Reports> call, Response<Reports> response) {
                listReports = response.body().getReports();
                getRapports();
            }

            @Override
            public void onFailure(Call<Reports> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), String.format("Erreur lors de la récupération des reports"), Toast.LENGTH_SHORT).show();

            }

        });
    }
}
