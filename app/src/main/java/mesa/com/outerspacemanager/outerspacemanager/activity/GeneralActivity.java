package mesa.com.outerspacemanager.outerspacemanager.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.OnAttackClickedListener;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewAttacks;
import mesa.com.outerspacemanager.outerspacemanager.db.AttackSataSource;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentGeneralsDetail;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentGeneralsList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentShipList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.Fragment_ship_detail;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 20/03/2017.
 */

public class GeneralActivity extends AppCompatActivity implements OnAttackClickedListener {
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        gson = new Gson();
    }

    @Override
    public void onAttackClicked(Attack atk) {
        FragmentGeneralsList fragment_list = (FragmentGeneralsList) getSupportFragmentManager().findFragmentById(R.id.fragment_generals);
        FragmentGeneralsDetail fragment_detail = (FragmentGeneralsDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        if (fragment_detail == null || !fragment_detail.isInLayout()) {
            Intent i = new Intent(getApplicationContext(), AttackDetailActivity.class);
            i.putExtra("attack", gson.toJson(atk));
            startActivity(i);
        } else {
            fragment_detail.setAttack(atk);
        }
    }
}
