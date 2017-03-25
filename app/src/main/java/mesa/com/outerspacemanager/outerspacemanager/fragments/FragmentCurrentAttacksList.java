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
import android.widget.ToggleButton;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewAttacks;
import mesa.com.outerspacemanager.outerspacemanager.db.AttackDataSource;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 25/03/2017.
 */

public class FragmentCurrentAttacksList extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView rvCurrentAttacks;
    private AttackDataSource db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_generals_list,container, false);
        rvCurrentAttacks = (RecyclerView) v.findViewById(R.id.generals);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCurrentAttacks.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar.setVisibility(View.GONE);
        getAttacks();

    }

    public void getAttacks(){
        db = new AttackDataSource(getContext());
        db.open();
        AdapterViewAttacks attackAdapter = new AdapterViewAttacks(getContext(), db.getAllAttacks());
        db.close();
        attackAdapter.setListner((MainActivity)getActivity());
        rvCurrentAttacks.setAdapter(attackAdapter);
    }
}
