package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentGeneralsDetail;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;

/**
 * Created by Lucas on 20/03/2017.
 */

public class AttackDetailActivity extends AppCompatActivity {
    private Gson gson;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_detail);
        gson = new Gson();


        Attack anAttack = gson.fromJson(getIntent().getStringExtra("attack"), Attack.class);

        FragmentGeneralsDetail fragment_general_detail = (FragmentGeneralsDetail)getSupportFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        fragment_general_detail.setAttack(anAttack);
    }
}
