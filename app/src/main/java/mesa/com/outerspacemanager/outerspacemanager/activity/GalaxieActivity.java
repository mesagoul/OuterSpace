package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewGalaxie;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.utils.LoaderProgressBar;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.model.Users;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 07/03/2017.
 */

public class GalaxieActivity extends Activity {
    private ListView topListe;
    private Retrofit retrofit;
    private String token;
    private ArrayList<User> listUser;
    private LoaderProgressBar progress;
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxie);

        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_galaxie_layout);
        topListe = (ListView) findViewById(R.id.list_galaxie);
        progress = new LoaderProgressBar(this);
        progress.show();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<Users> request = service.getUsers(token,0,10);

        request.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, retrofit2.Response<Users> response) {
                refreshLayout.setRefreshing(false);
                progress.dismiss();
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
}
