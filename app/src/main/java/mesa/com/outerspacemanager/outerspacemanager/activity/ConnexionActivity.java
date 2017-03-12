package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.utils.LoaderProgressBar;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 07/03/2017.
 */

public class ConnexionActivity extends Activity {
    private Button connexionBtn;
    private EditText username;
    private EditText password;
    private User user;
    private Retrofit retrofit;
    private LoaderProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_connexion);

        progress = new LoaderProgressBar(this);
        username = (EditText) findViewById(R.id.connexion_username);
        password = (EditText) findViewById(R.id.connexion_mdp);
        connexionBtn = (Button) findViewById(R.id.btn_se_connecter);

        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                user = new User(username.getText().toString(), password.getText().toString());
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://outer-space-manager.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Service service = retrofit.create(Service.class);
                Call<User> request = service.connectUserAccount(user);

                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progress.dismiss();
                        if (response.isSuccessful()) {
                            SharedPreferences settings = getSharedPreferences("token", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("token", response.body().getToken());
                            editor.commit();

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
        });
    }
}
