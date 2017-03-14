package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Building;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class MainActivity extends Activity {

    private TextView username;
    private TextView score_label;
    private TextView score_value;

    private Button btnVueGeneral;
    private Button btnBatiment;
    private Button btnFlotte;
    private Button btnRecherches;
    private Button btnChantierNaval;
    private Button btnGalaxie;
    private Button btnLogOut;

    private TextView gas;
    private TextView mineral;

    private TextView gas_modifier;
    private TextView mineral_modifier;

    private SwipeRefreshLayout refreshLayout;

    private User currentUser;
    private Retrofit retrofit;
    private Service service;
    private String token;
    private Gson gson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);



        // INIT VARIABLES
        gson = new Gson();

        username = (TextView) findViewById(R.id.menu_username);
        score_label = (TextView) findViewById(R.id.menu_score);
        score_value = (TextView) findViewById(R.id.menu_score_value);

        gas = (TextView) findViewById(R.id.menu_mineral);
        mineral = (TextView) findViewById(R.id.menu_gas);

        gas_modifier = (TextView) findViewById(R.id.menu_mineral_modifier);
        mineral_modifier = (TextView) findViewById(R.id.menu_gas_modifier);


        btnVueGeneral = (Button) findViewById(R.id.btnGeneralVue);
        btnBatiment = (Button) findViewById(R.id.btn_batiments);
        btnFlotte = (Button) findViewById(R.id.btn_flotte);
        btnRecherches = (Button) findViewById(R.id.btn_recherches);
        btnChantierNaval = (Button) findViewById(R.id.btn_chantier_spatial);
        btnGalaxie = (Button) findViewById(R.id.btn_galaxie);
        btnLogOut = (Button) findViewById(R.id.btn_logout);

        // GET TOKEN
        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());


        // CALL API
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_main);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUser();

            }
        });








        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("token", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("token", "");
                editor.commit();
                finish();
            }
        });

        btnRecherches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSearcheActivity = new Intent(getApplicationContext(),SearcheActivity.class);
                startActivity(toSearcheActivity);
            }
        });

        btnBatiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBuildingActivity = new Intent(getApplicationContext(),BuildingActivity.class);
                startActivity(toBuildingActivity);
            }
        });

        btnChantierNaval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChantierActivity = new Intent(getApplicationContext(),ChantierActivity.class);
                startActivity(toChantierActivity);
            }
        });
        btnGalaxie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGalaxieActivity = new Intent(getApplicationContext(),GalaxieActivity.class);
                startActivity(toGalaxieActivity);
            }
        });
        btnGalaxie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGalaxieActivity = new Intent(getApplicationContext(),GalaxieActivity.class);
                startActivity(toGalaxieActivity);
            }
        });
        btnFlotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFlotteActivity = new Intent(getApplicationContext(),FlotteActivity.class);
                startActivity(toFlotteActivity);
            }
        });



        refreshUser();


    }

    public void refreshUser(){
        Call<User> request = service.getUser(token);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                refreshLayout.setRefreshing(false);

                username.setText(response.body().getUsername());
                score_value.setText(response.body().getPoints().toString());

                gas.setText(response.body().getGas().toString() + " gas");
                mineral.setText(response.body().getMinerals().toString() + " mineral");

                gas_modifier.setText("x"+response.body().getGasModifier().toString());
                mineral_modifier.setText("x"+response.body().getMineralsModifier().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
