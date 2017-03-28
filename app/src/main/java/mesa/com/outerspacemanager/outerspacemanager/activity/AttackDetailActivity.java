package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.currentAttacks.FragmentCurrentAttacksDetail;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;

/**
 * Created by Lucas on 20/03/2017.
 */

public class AttackDetailActivity extends AppCompatActivity{
    private Gson gson;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_detail);
        gson = new Gson();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Attack anAttack = gson.fromJson(getIntent().getStringExtra("attack"), Attack.class);

        FragmentCurrentAttacksDetail fragment_general_detail = (FragmentCurrentAttacksDetail)getSupportFragmentManager().findFragmentById(R.id.fragment_attack_detail);

        if(anAttack != null){
            fragment_general_detail.setAttack(anAttack);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
