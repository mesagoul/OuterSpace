package mesa.com.outerspacemanager.outerspacemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class SignUpActivity extends Activity {

    private Button addBtn;
    private EditText username;
    private EditText password;
    private User user;
    private Retrofit retrofit;
    private Gson gson;

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
        addBtn = (Button) findViewById(R.id.btn_add);
        gson = new Gson();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(username.getText().toString(),password.getText().toString());
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://outer-space-manager.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Service service = retrofit.create(Service.class);
                Call<User> request = service.createUserAccount(user);

                request.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        SharedPreferences settings = getSharedPreferences("token", 0);
                        SharedPreferences.Editor editor = settings.edit();

                        editor.putString("token", response.body().getToken());
                        editor.commit();

                        Intent IntentMainActivity = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(IntentMainActivity);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), String.format("Erreur"), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}
