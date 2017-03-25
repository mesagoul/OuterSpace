package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewGalaxie;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.model.Users;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 07/03/2017.
 */

public class GalaxieActivity extends AppCompatActivity {

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
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxie);

        // INIT BACK ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // INIT NETWORK
        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);

        // INIT VARIABLES
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_galaxie_layout);
        topListe = (ListView) findViewById(R.id.list_galaxie);
        intent = getIntent();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        if(isAttaque()) {
            topListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView username = (TextView) view.findViewById(R.id.galaxie_name_value);
                    intent.putExtra("username",username.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
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
                    topListe.setAdapter(new AdapterViewGalaxie(getApplicationContext(), listUser));
                }else{
                    Toast.makeText(getApplicationContext(), String.format("Erreur"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération de la Galaxie"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // CHECK if activityis called by fleet Actity or not
    public boolean isAttaque(){
        return intent.getBooleanExtra("attaque",false);
    }

    // On back arrow pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
