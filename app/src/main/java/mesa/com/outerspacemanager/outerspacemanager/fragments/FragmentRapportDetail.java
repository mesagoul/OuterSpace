package mesa.com.outerspacemanager.outerspacemanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;

/**
 * Created by Lucas on 20/03/2017.
 */

    public class FragmentRapportDetail extends Fragment {
        private RecyclerView rvAttackDetail;
        private RecyclerView rvDefendDetail;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_report_detail,container, false);
            rvAttackDetail = (RecyclerView) v.findViewById(R.id.recycler_report_attack_detail);
            rvDefendDetail = (RecyclerView) v.findViewById(R.id.recycler_report_defend_detail);
            return v;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            rvAttackDetail.setLayoutManager(new LinearLayoutManager(getContext()));
            rvDefendDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        public void setReport(Report report) {
           // rvDetail.setAdapter(new AdapterViewReportsDetail(getContext(),report));
        }
}
