package mesa.com.outerspacemanager.outerspacemanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class BuildingActivity extends Activity {
    private ListView list_buildings;
    private ArrayList<String> maList;
    private Gson gson;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        gson = new Gson();
        list_buildings = (ListView) findViewById(R.id.list_buildings);

        SharedPreferences settings = getSharedPreferences("token", 0);
        String token = settings.getString("token",new String());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<Buildings> request = service.getBuildings(token);

        request.enqueue(new Callback<Buildings>() {
                            @Override
                            public void onResponse(Call<Buildings> call, Response<Buildings> response) {
                                if(response.isSuccessful()){
                                    list_buildings.setAdapter(new adapterViewBuilding(getApplicationContext(), response.body().getBuildings()));
                                }else{
                                    Toast.makeText(getApplicationContext(), String.format("Erreur lors de la récupération des batiments"), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Buildings> call, Throwable t) {

                            }
                        });
    }
}
