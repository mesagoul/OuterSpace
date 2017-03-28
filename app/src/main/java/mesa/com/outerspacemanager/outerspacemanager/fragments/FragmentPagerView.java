package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.interfaces.OnAttackListner;
import mesa.com.outerspacemanager.outerspacemanager.interfaces.OnRapportListener;
import mesa.com.outerspacemanager.outerspacemanager.Permissions.InternalStorage;
import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.AttackDetailActivity;
import mesa.com.outerspacemanager.outerspacemanager.activity.ReportDetailActivity;
import mesa.com.outerspacemanager.outerspacemanager.animations.ZoomOutPageTransformer;
import mesa.com.outerspacemanager.outerspacemanager.fragments.currentAttacks.FragmentCurrentAttacksDetail;
import mesa.com.outerspacemanager.outerspacemanager.fragments.currentAttacks.FragmentCurrentAttacksList;
import mesa.com.outerspacemanager.outerspacemanager.fragments.reports.FragmentReportsDetail;
import mesa.com.outerspacemanager.outerspacemanager.fragments.reports.FragmentReportsList;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;

/**
 * Created by Lucas on 27/03/2017.
 */

public class FragmentPagerView extends Fragment implements OnAttackListner, OnRapportListener {



    private ArrayList<Fragment> listFragments;
    // VIEW PAGER
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Gson gson;
    private TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_pager_view,container, false);
        mPager = (ViewPager) v.findViewById(R.id.main_pager);
        tabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);
        listFragments = new ArrayList<Fragment>();
        gson = new Gson();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragmentsForPagerView();
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), listFragments);
        mPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mPager);
    }

    public void initFragmentsForPagerView(){
        FragmentCurrentAttacksList fragmentCurrentAttacksList = new FragmentCurrentAttacksList();
        fragmentCurrentAttacksList.setListener(this);
        FragmentReportsList fragmentReportsList = new FragmentReportsList();
        fragmentReportsList.setListener(this);

        listFragments.add(fragmentCurrentAttacksList);
        listFragments.add(fragmentReportsList);
    }

    // When an attack is ended, refresh Rapport List
    @Override
    public void onAttackTimeEnded() {
        mPager.setAdapter(new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(), listFragments));
    }

    @Override
    public void onAttackClicked(Attack atk) {
        FragmentCurrentAttacksList fragment_list = (FragmentCurrentAttacksList)  getFragmentManager().findFragmentById(R.id.fragment_generals);
        FragmentCurrentAttacksDetail fragment_detail = (FragmentCurrentAttacksDetail) getFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        if (fragment_detail == null || !fragment_detail.isInLayout()) {
            Intent i = new Intent(getActivity(), AttackDetailActivity.class);
            i.putExtra("attack", gson.toJson(atk));
            startActivity(i);
        } else {
            fragment_detail.setAttack(atk);
        }
    }

    @Override
    public void onReportClicked(Report report) {
        FragmentReportsList fragment_list = (FragmentReportsList) getFragmentManager().findFragmentById(R.id.fragment_generals);
        FragmentReportsDetail fragment_detail = (FragmentReportsDetail) getFragmentManager().findFragmentById(R.id.fragment_generals_detail);
        if (fragment_detail == null || !fragment_detail.isInLayout()) {
            Intent i = new Intent(getActivity(), ReportDetailActivity.class);
            i.putExtra("report", gson.toJson(report));
            startActivity(i);
        } else {
            fragment_detail.setReport(report);
        }

    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
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

