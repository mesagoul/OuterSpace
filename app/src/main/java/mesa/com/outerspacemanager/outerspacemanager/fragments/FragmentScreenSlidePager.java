package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mesa.com.outerspacemanager.outerspacemanager.R;

/**
 * Created by Lucas on 25/03/2017.
 */

public class FragmentScreenSlidePager extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.pager_view_rapport, container, false);

        return rootView;
    }
}
