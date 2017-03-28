package mesa.com.outerspacemanager.outerspacemanager.fragments.currentAttacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;
import mesa.com.outerspacemanager.outerspacemanager.adapter.AdapterViewAttacksDetail;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;

/**
 * Created by Lucas on 25/03/2017.
 */

public class FragmentCurrentAttacksDetail extends Fragment {
    private RecyclerView rvDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attack_detail,container);
        rvDetail = (RecyclerView) v.findViewById(R.id.recycler_attack_detail);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDetail.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setAttack(Attack anAttack){
        rvDetail.setAdapter(new AdapterViewAttacksDetail(getContext(),anAttack.getShips()));

    }
}
