package mesa.com.outerspacemanager.outerspacemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.db.ShipDataSource;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;

/**
 * Created by Lucas on 22/03/2017.
 */


public class AdapterViewReportsDetail extends RecyclerView.Adapter<AdapterViewReportsDetail.ReportDetailViewHolder> {
    private final Context context;
    private final List<Ship> ships;

    public AdapterViewReportsDetail(Context context, Ships ships) {
        this.ships = ships.getShips();
        this.context = context;
    }

    @Override
    public AdapterViewReportsDetail.ReportDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.attacks_detail_adapter, parent, false);
        AdapterViewReportsDetail.ReportDetailViewHolder viewHolder = new AdapterViewReportsDetail.ReportDetailViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterViewReportsDetail.ReportDetailViewHolder holder, int position) {
        Ship aShip = ships.get(position);
        ShipDataSource db = new ShipDataSource(context);
        db.open();
        Ship dbShip = db.getShipByID(aShip.getShipId());
        db.close();
        holder.tvName.setText(dbShip.getName());
        holder.tvAmount.setText(String.valueOf(aShip.getAmount()));
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

    public class ReportDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAmount;

        public ReportDetailViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}

