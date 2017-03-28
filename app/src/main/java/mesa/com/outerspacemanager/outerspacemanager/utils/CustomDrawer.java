package mesa.com.outerspacemanager.outerspacemanager.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentBuilding;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentFlotte;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentSearche;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentChantier;
import mesa.com.outerspacemanager.outerspacemanager.fragments.FragmentGalaxy;

/**
 * Created by Lucas on 22/03/2017.
 */

public class CustomDrawer {

    private Drawer result;

    private PrimaryDrawerItem buildings_item;
    private PrimaryDrawerItem fleet_item;
    private PrimaryDrawerItem resherches_item;
    private PrimaryDrawerItem chantier_item;
    private PrimaryDrawerItem galaxy_item;
    private PrimaryDrawerItem logout_item;

    private PrimaryDrawerItem gas_item;
    private PrimaryDrawerItem mineral_item ;

    private Activity activity;

    private ProfileDrawerItem profile;
    private AccountHeader account;

    private Toolbar toolbar;


    public CustomDrawer(Activity activity, Toolbar toolbar){
        this.activity = activity;
        this.toolbar = toolbar;
        this.buildings_item = new PrimaryDrawerItem().withIdentifier(1).withName("Bâtiments").withIcon(R.drawable.ic_rocket);
        this.fleet_item = new PrimaryDrawerItem().withIdentifier(2).withName("Flotte").withIcon(R.drawable.ic_fleet);
        this.resherches_item = new PrimaryDrawerItem().withIdentifier(3).withName("Recherches").withIcon(R.drawable.ic_resherches);
        this.chantier_item = new PrimaryDrawerItem().withIdentifier(4).withName("Chantier spatial").withIcon(R.drawable.ic_chantier);
        this.galaxy_item = new PrimaryDrawerItem().withIdentifier(5).withName("Galaxie").withIcon(R.drawable.ic_galaxy);
        this.logout_item = new PrimaryDrawerItem().withIdentifier(6).withName("Déconnexion").withIcon(R.drawable.ic_logout);
        this.gas_item = new PrimaryDrawerItem().withIdentifier(7).withName("Gas : ");
        this.mineral_item = new PrimaryDrawerItem().withIdentifier(8).withName("Mineral : ");
        this.profile = new ProfileDrawerItem();

    }

    public Fragment getFragmentFromIdentifier(int id){
        switch (id){
            case 1 :
                return new FragmentBuilding();
            case 2 :
                 return new FragmentFlotte();
            case 3 :
                 return new FragmentSearche();
            case 4 :
                return new FragmentChantier();
            case 5 :
                  return new FragmentGalaxy();
        }
         return new FragmentGalaxy();

    }
    public void load(){

        profile.withIsExpanded(false);
        profile.withIcon(R.drawable.ic_person);

        gas_item.withSelectable(false);
        gas_item.withSelectedBackgroundAnimated(false);
        gas_item.withIconTintingEnabled(false);
        mineral_item.withSelectable(false);
        mineral_item.withSelectedBackgroundAnimated(false);


        account =  new AccountHeaderBuilder()
                .withActivity(this.activity)
                .withHeaderBackground(R.drawable.galaxy_background)
                .addProfiles(
                        profile
                ).build();

        result = new DrawerBuilder()
                .withActivity(this.activity)
                .withToolbar(this.toolbar)
                .withAccountHeader(this.account)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        gas_item,
                        mineral_item,
                        new DividerDrawerItem(),
                        buildings_item,
                        fleet_item,
                        resherches_item,
                        chantier_item,
                        galaxy_item,
                        logout_item
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(Integer.parseInt(String.valueOf(drawerItem.getIdentifier())) != 6){
                            Fragment newFragment = getFragmentFromIdentifier(Integer.parseInt(String.valueOf(drawerItem.getIdentifier())));
                            ((MainActivity)activity).loadNewFragment(newFragment, false);

                        }else{
                            SharedPreferences settings = activity.getSharedPreferences("token", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", "");
                            editor.commit();
                            activity.finish();
                        }

                        return false;
                    }
                })
                .withSelectedItem(-1)
                .build();
    }

    public void setNameUser(String nameUser){
        this.profile.withName(nameUser);
        this.account.updateProfile(profile);
    }

    public void setScoreUser(String score){
        this.profile.withEmail("Score : " + score);
        this.account.updateProfile(profile);
    }

    public void updateGasUser(String gas){
        result.updateBadge(7,new StringHolder(gas));
    }

    public void updateMineralUser(String mineral) {
        result.updateBadge(8,new StringHolder(mineral));
    }

    public Drawer getResult(){
        return this.result;
    }

}
