package mesa.com.outerspacemanager.outerspacemanager.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import mesa.com.outerspacemanager.outerspacemanager.Permissions.InternalStorage;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentPagerView;
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

public class MainActivity extends FragmentActivity {

    // CONSTANTS
    private static final int RESULT_CODE_GENERAL = 0;
    private static final int RESULT_CODE_ACTIVITY_AFTER_ATTACK = 1;
    private static final int RESULT_CODE_ACTIVITY_RELOAD_USER = 2;

    // VARIABLES
    private Double mineralGenerated;
    private InternalStorage permission_storage;
    private Double gasGenerated;
    private CustomDrawer drawer;
    private Handler handler;
    private Runnable runnable;
    boolean isHandlerRun;
    // NETWORK
    private Retrofit retrofit;
    private Service service;
    private String token;


    private Toolbar toolbar;
    private FrameLayout fragment_frameLayout;
    private boolean showFragment = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarName("Outer Space Manager");

        fragment_frameLayout = (FrameLayout) findViewById(R.id.fragment_frameLayout);


        // Instantiate Drawer
        drawer = new CustomDrawer(this, toolbar);
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

        permission_storage = new InternalStorage(this);
        permission_storage.askForPermission();
    }

    public void initAfterRequiredDataBase(){
                loadNewFragment(new FragmentPagerView(),false);
    }

    @Override
    public void onBackPressed() {
        if(drawer.getResult().isDrawerOpen()){
            drawer.getResult().closeDrawer();
        }else{
            setToolbarName("Outer Space");
            loadNewFragment(new FragmentPagerView(), true);
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (showFragment) {
            initAfterRequiredDataBase();
        }
    }

    // load new fragment to show in activity
    public void loadNewFragment(Fragment newFragment , boolean isCloseFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frameLayout, newFragment);
        if(isCloseFragment){
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        }else{
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    // Update ressources of user (every seconds by Runnable)
    public void updateRessources(){
        this.gasGenerated += (0.5);
        this.mineralGenerated += (0.8);
        drawer.updateGasUser(String.valueOf(this.gasGenerated.intValue()));
        drawer.updateMineralUser(String.valueOf(this.mineralGenerated.intValue()));
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
    public void setToolbarName(String name){
        this.toolbar.setTitle(name);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Instantiate a ViewPager and a PagerAdapter.
                    showFragment = true;

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
}
