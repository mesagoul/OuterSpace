package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Attack;

/**
 * Created by Lucas on 20/03/2017.
 */

    public class FragmentGeneralsDetail extends Fragment {
        private TextView username;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_generals_detail,container);
            username = (TextView) v.findViewById(R.id.attack_detail_username);
            return v;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        public void setAttack(Attack anAttack){
            this.username.setText(anAttack.getUsername());
        }
    }
