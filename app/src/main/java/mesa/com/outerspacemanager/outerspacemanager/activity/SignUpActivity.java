package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class SignUpActivity extends Activity {


    // UI ELEMENTS
    private EditText username;
    private EditText password;
    private ProgressBar progressBar;
    private LinearLayout layout_connexion;

    //  NETWORK
    Service service;

    // VARIABLES
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences settings = getSharedPreferences("token", 0);
        String token = settings.getString("token","");
        if(!token.isEmpty()){
            Intent IntentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(IntentMainActivity);
        }


        username = (EditText) findViewById(R.id.input_identifiant);
        password = (EditText) findViewById(R.id.input_mdp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layout_connexion = (LinearLayout) findViewById(R.id.layout_connexion);
        Button btn_inscription = (Button) findViewById(R.id.btn_inscription);
        Button btn_connexion = (Button) findViewById(R.id.btn_connexion);

        progressBar.setVisibility(View.GONE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);



        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = new User(username.getText().toString(),password.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                layout_connexion.setVisibility(View.GONE);
                connexion();
            }
        });

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = new User(username.getText().toString(),password.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                layout_connexion.setVisibility(View.GONE);
                inscription();

            }
        });

    }

    public void connexion(){
        Call<User> request = service.connectUserAccount(currentUser);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressBar.setVisibility(View.GONE);
                layout_connexion.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences("token", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token", response.body().getToken());
                    editor.commit();



                    SharedPreferences settings_device = getSharedPreferences("devicetoken", 0);
                    String deviceToken = settings_device.getString("devicetoken", "");
                    if(!deviceToken.isEmpty()){
                        User user = new User(deviceToken);
                        sendTokenDevice(response.body().getToken(),user);
                    }
                    Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(toMainActivity);
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Erreur lors la connexion"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur lors la connexion"), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void sendTokenDevice(String token, User user) {
        Call<User> request = service.sendDeviceToken(token, user);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), String.format("Erreur serveur"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur serveur"), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void inscription(){
        Call<User> request = service.createUserAccount(currentUser);

        request.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressBar.setVisibility(View.GONE);
                layout_connexion.setVisibility(View.VISIBLE);
                if(response.isSuccessful()){
                    SharedPreferences settings = getSharedPreferences("token", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token", response.body().getToken());
                    editor.commit();

                    Intent IntentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(IntentMainActivity);
                }else{
                    Toast.makeText(getApplicationContext(), String.format("Erreur lors de l'authentification"), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Erreur"), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
