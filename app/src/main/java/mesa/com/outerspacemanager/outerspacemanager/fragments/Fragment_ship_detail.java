package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Amount;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 14/03/2017.
 */

public class Fragment_ship_detail extends Fragment {
    private TextView name,life,capacity,attaque,shield,speed,gasCost,mineralCost,spatioportLevelNeeded,timeToBuild,seekbar_text;
    private SeekBar seekbar;
    private Button button;
    private Ship ship;
    private String token;
    private Retrofit retrofit;
    private Service service;
    private SharedPreferences settings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_detail,container);
        name = (TextView)v.findViewById(R.id.ship_name);
        life = (TextView)v.findViewById(R.id.ship_life);
        capacity = (TextView)v.findViewById(R.id.ship_capacity);
        attaque = (TextView)v.findViewById(R.id.ship_attaque);
        shield = (TextView)v.findViewById(R.id.ship_shield);
        speed = (TextView)v.findViewById(R.id.ship_speed);
        gasCost = (TextView)v.findViewById(R.id.ship_gas);
        mineralCost = (TextView)v.findViewById(R.id.ship_mineral);
        spatioportLevelNeeded = (TextView)v.findViewById(R.id.ship_spatioportLevelNeeded);
        timeToBuild = (TextView)v.findViewById(R.id.ship_timeToBuild);
        seekbar = (SeekBar)v.findViewById(R.id.ship_seekbar);
        seekbar_text = (TextView)v.findViewById(R.id.ship_seekbar_text);
        button = (Button) v.findViewById(R.id.ship_buy);

        settings = getActivity().getSharedPreferences("token", 0);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = settings.getString("token", new String());
        service = retrofit.create(Service.class);



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbar_text.setText(String.valueOf(seekbar.getProgress()));
                updateValues(seekbar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyShip();
            }
        });
    }

    public void updateValues(int multiplier){
        gasCost.setText(String.valueOf(ship.getGasCost()*multiplier));
        mineralCost.setText(String.valueOf(ship.getMineralCost()*multiplier));
        timeToBuild.setText(String.valueOf(ship.getTimeToBuild()*multiplier));
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        this.name.setText(ship.getName());
        this.life.setText(String.valueOf(ship.getLife()));
        this.attaque.setText(String.valueOf(ship.getMaxAttack()));
        this.capacity.setText(String.valueOf(ship.getCapacity()));
        this.gasCost.setText(String.valueOf(ship.getGasCost()));
        this.mineralCost.setText(String.valueOf(ship.getMineralCost()));
        this.shield.setText(String.valueOf(ship.getShield()));
        this.speed.setText(String.valueOf(ship.getSpeed()));
        this.spatioportLevelNeeded.setText(String.valueOf(ship.getSpatioportLevelNeeded()));
        this.timeToBuild.setText(String.valueOf(ship.getTimeToBuild()));
    }
    public void updateSeekBarMax(Double minerals, Double gas){
        Double maxGas = gas/ship.getGasCost();
        Double maxMineral = minerals/ship.getMineralCost();
        seekbar.setProgress(0);
        if(maxGas > maxMineral){
            seekbar.setMax(maxMineral.intValue());
        }else{
            seekbar.setMax(maxGas.intValue());
        }
    }

    public void buyShip(){
        Amount myAmount = new Amount();
        myAmount.setAmount(seekbar.getProgress());
        Call<Ships> request = service.buyShip(token,ship.getShipId(),myAmount);
        request.enqueue(new Callback<Ships>() {
            @Override
            public void onResponse(Call<Ships> call, Response<Ships> response) {
                Toast.makeText(getContext(), String.format("Vos vaisseaux sont en construction"), Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }

            @Override
            public void onFailure(Call<Ships> call, Throwable t) {
                Toast.makeText(getContext(), String.format("Erreur"), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
