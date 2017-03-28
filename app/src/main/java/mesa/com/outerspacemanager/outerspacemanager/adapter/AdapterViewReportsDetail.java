package mesa.com.outerspacemanager.outerspacemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.db.ShipDataSource;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.FleetAfterBattle;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;

/**
 * Created by Lucas on 22/03/2017.
 */


public class AdapterViewReportsDetail extends RecyclerView.Adapter<AdapterViewReportsDetail.ReportDetailViewHolder> {
    private final Context context;
    private final ArrayList<Ship> ships;
    private final FleetAfterBattle fleetAfterBattle;

    public AdapterViewReportsDetail(Context context, FleetAfterBattle fleet) {
        this.context = context;
        this.fleetAfterBattle = fleet;
        this.ships = fleetAfterBattle.getFleet();
    }

    @Override
    public AdapterViewReportsDetail.ReportDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_report_attack_detail, parent, false);
        AdapterViewReportsDetail.ReportDetailViewHolder viewHolder = new AdapterViewReportsDetail.ReportDetailViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterViewReportsDetail.ReportDetailViewHolder holder, int position) {
        if(fleetAfterBattle.getCapacity() != 0){
            Ship aShip = this.ships.get(position);
            if(aShip.getAmount() != 0){
                holder.tvName.setText(aShip.getName());
                holder.tvAmount.setText(String.valueOf(aShip.getAmount()));
            }
        }else{
            holder.tvName.setText("Pas de flotte");
            holder.tvAmount.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        if(ships == null){
            return 0;
        }else{
            return ships.size();
        }
    }

    public class ReportDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAmount;

        public ReportDetailViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.ship_name);
            tvAmount = (TextView) itemView.findViewById(R.id.ship_amount);
        }
    }
}

