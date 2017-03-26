package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.OnGeneralClickedListener;
import mesa.com.outerspacemanager.outerspacemanager.Permissions.InternalStorage;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.animations.ZoomOutPageTransformer;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentCurrentAttacksDetail;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentCurrentAttacksList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentRapportDetail;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentRapportList;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;
import mesa.com.outerspacemanager.outerspacemanager.network.Service;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.utils.CustomDrawer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lucas on 06/03/2017.
 */

public class MainActivity extends FragmentActivity implements OnGeneralClickedListener {

    // CONSTANTS
    private static final int RESULT_CODE_GENERAL = 0;
    private static final int RESULT_CODE_ACTIVITY_AFTER_ATTACK = 1;
    private static final int RESULT_CODE_ACTIVITY_RELOAD_USER = 2;

    // VARIABLES
    private Double mineralGenerated;
    private Double gasGenerated;
    private CustomDrawer drawer;
    private ArrayList<Fragment> listFragments;
    private Handler handler;
    private Runnable runnable;
    boolean isHandlerRun;
    private InternalStorage permission_storage;
    // NETWORK
    private Retrofit retrofit;
    private Service service;
    private String token;
    private Gson gson;

    // VIEW PAGER
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new FragmentCurrentAttacksList());
        listFragments.add(new FragmentRapportList());

        gson = new Gson();

        permission_storage = new InternalStorage(this);
        permission_storage.askForPermission();


        // Instantiate Drawer
        drawer = new CustomDrawer(this);
        drawer.load();


        // Instantiate Handler & Runnable
        isHandlerRun = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateRessources();
                handler.postDelayed(this,1000);
            }
        };


        // GET TOKEN
        SharedPreferences settings = getSharedPreferences("token", 0);
        token = settings.getString("token", new String());


        // CALL API
        retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
        refreshUser();

    }

    public void initVariablesRequiredDataBase(){
        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), listFragments);
        mPager.setAdapter(mPagerAdapter);


        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);
    }

    public void refreshPagerView(){
        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager(), listFragments));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Instantiate a ViewPager and a PagerAdapter.
                   this.initVariablesRequiredDataBase();


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    permission_storage.askForPermission();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() != 0){
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CODE_GENERAL){
            // if fleet response after attack an user
            refreshUser();
            if(resultCode == RESULT_CODE_ACTIVITY_AFTER_ATTACK){
                // reload PagerView
                refreshPagerView();
            }
        }
    }

    public void updateRessources(){
        this.gasGenerated += (0.5);
        this.mineralGenerated += (0.8);
        drawer.updateGasUser(String.valueOf(this.gasGenerated.intValue()));
        drawer.updateMineralUser(String.valueOf(this.mineralGenerated.intValue()));

    }
    @Override
    public void onAttackClicked(Attack atk) {
        FragmentCurrentAttacksList fragment_list = (FragmentCurrentAttacksList) getSupportFragmentManager().findFragmentById(R.id.fragment_generals);
        FragmentCurrentAttacksDetail fragment_detail = (FragmentCurrentAttacksDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        if (fragment_detail == null || !fragment_detail.isInLayout()) {
            Intent i = new Intent(getApplicationContext(), AttackDetailActivity.class);
            i.putExtra("attack", gson.toJson(atk));
            startActivity(i);
        } else {
            fragment_detail.setAttack(atk);
        }
    }

    @Override
    public void onReportClicked(Report report) {
        FragmentRapportList fragment_list = (FragmentRapportList) getSupportFragmentManager().findFragmentById(R.id.fragment_generals);
        FragmentRapportDetail fragment_detail = (FragmentRapportDetail) getSupportFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        if (fragment_detail == null || !fragment_detail.isInLayout()) {
            Intent i = new Intent(getApplicationContext(), ReportDetailActivity.class);
            i.putExtra("report", gson.toJson(report));
            startActivity(i);
        } else {
            fragment_detail.setReport(report);
        }

    }

    public void refreshUser(){
        Call<User> request = service.getUser(token);

        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                drawer.setNameUser(response.body().getUsername());
                drawer.updateGasUser(response.body().getGas().toString());
                drawer.setScoreUser(String.valueOf(response.body().getPoints()));

                gasGenerated = response.body().getGas().doubleValue();
                mineralGenerated = response.body().getMinerals().doubleValue();

                if(!isHandlerRun){
                    handler.post(runnable);
                    isHandlerRun = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        private ArrayList<Fragment> listFragments;
        private String tabTitles[] = new String[] { "Attaques en cours", "Rapports" };

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragments) {
            super(fm);
            this.listFragments = listFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
