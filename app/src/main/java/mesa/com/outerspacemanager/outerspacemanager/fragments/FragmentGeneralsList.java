package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ToggleButton;

import mesa.com.outerspacemanager.outerspacemanager.OnAttackClickedListener;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.ChantierActivity;
import mesa.com.outerspacemanager.outerspacemanager.activity.GeneralActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewAttacks;
import mesa.com.outerspacemanager.outerspacemanager.db.AttackSataSource;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 20/03/2017.
 */

public class FragmentGeneralsList extends Fragment {
    private ToggleButton btn_toggle;
    private RecyclerView listGenerals;
    private Retrofit retrofit;
    private Service service;
    public static final String PREFS_NAME = "OuterSpaceManager";
    private SharedPreferences settings;
    private AttackSataSource db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generals_list,container);
        listGenerals = (RecyclerView) v.findViewById(R.id.generals);
        btn_toggle = (ToggleButton) v.findViewById(R.id.btn_toggle);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listGenerals.setLayoutManager(new LinearLayoutManager(getContext()));

        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);

        btn_toggle.setText("Rapports");
        btn_toggle.setTextOn("Attaques en cours");
        btn_toggle.setTextOff("Rapports");
        btn_toggle.setChecked(false);
        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_toggle.isChecked()){
                    getAttacks();
                }else{
                    getRapports();
                }
            }
        });
        getRapports();
    }

    public void getRapports(){
        Log.d("zzzz","Rapport");
        /*Call<Users> request = service.getUsers(settings.getString("users", new String()));
        request.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                rvUsers.setAdapter(new GalaxyArrayAdapter(getApplicationContext(), response.body().getUsers()));
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Context context = getApplicationContext();
                CharSequence text = "Error";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
           }*/
    }

    public void getAttacks(){
        Log.d("zzzz","Attaque");
        db = new AttackSataSource(getContext());
        db.open();
        AdapterViewAttacks attackAdapter = new AdapterViewAttacks(getContext(), db.getAllAttacks());
        attackAdapter.setListner((GeneralActivity)getActivity());
        listGenerals.setAdapter(attackAdapter);
    }


}
