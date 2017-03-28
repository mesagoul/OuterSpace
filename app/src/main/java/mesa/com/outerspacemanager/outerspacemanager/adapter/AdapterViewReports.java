package mesa.com.outerspacemanager.outerspacemanager.adapter;


        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;

        import mesa.com.outerspacemanager.outerspacemanager.interfaces.OnRapportListener;
        import mesa.com.outerspacemanager.outerspacemanager.R;
        import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;

/**
 * Created by Lucas on 21/03/2017.
 */



public class AdapterViewReports extends RecyclerView.Adapter<AdapterViewReports.ReportsViewHolder> {
        private final Context context;
        private final ArrayList<Report> reports;
        private OnRapportListener listener;

    public AdapterViewReports(Context context, ArrayList<Report> reports) {
            this.reports = reports;
            this.context = context;
        }

        @Override
        public AdapterViewReports.ReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.reports_adapter, parent, false);
            AdapterViewReports.ReportsViewHolder viewHolder = new AdapterViewReports.ReportsViewHolder(rowView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final AdapterViewReports.ReportsViewHolder holder, int position) {
            final Report aReport = reports.get(position);

            if(aReport.getType().equals("attacker")){
                if(aReport.getAttackerFleetAfterBattle().getSurvivingShips() == 0){
                    // lose
                    holder.lose.setVisibility(View.VISIBLE);
                    holder.win.setVisibility(View.GONE);
                    holder.defended_lose.setVisibility(View.GONE);
                    holder.defended_won.setVisibility(View.GONE);

                    holder.overlay.setBackgroundResource(R.color.colorRed);


                }else{
                    // win
                    holder.win.setVisibility(View.VISIBLE);
                    holder.lose.setVisibility(View.GONE);
                    holder.defended_lose.setVisibility(View.GONE);
                    holder.defended_won.setVisibility(View.GONE);

                    holder.overlay.setBackgroundResource(R.color.colorGreen);

                    holder.gas_won.setText(String.valueOf(aReport.getGasWon().intValue()));
                    holder.mineral_won.setText(String.valueOf(aReport.getMineralsWon().intValue()));
                }
            }else{
                if(aReport.getDefenderFleetAfterBattle().getSurvivingShips() == 0){
                    // defended lose
                    holder.defended_lose.setVisibility(View.VISIBLE);
                    holder.lose.setVisibility(View.GONE);
                    holder.win.setVisibility(View.GONE);
                    holder.defended_won.setVisibility(View.GONE);

                    holder.defended_gas_lose.setText(String.valueOf(aReport.getGasWon().intValue()));
                    holder.defended_mineral_lose.setText(String.valueOf(aReport.getMineralsWon().intValue()));

                    holder.overlay.setBackgroundResource(R.color.colorRed);
                }else{
                    // defended won
                    holder.defended_won.setVisibility(View.VISIBLE);
                    holder.lose.setVisibility(View.GONE);
                    holder.win.setVisibility(View.GONE);
                    holder.defended_lose.setVisibility(View.GONE);

                    holder.overlay.setBackgroundResource(R.color.colorBlue);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onReportClicked(aReport);
                }
            });

            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            DateFormat formatter_day = new SimpleDateFormat("d/M");
            Date date = new Date(aReport.getDate());
            holder.date.setText(formatter.format(date));
            holder.date_day.setText(formatter_day.format(date));
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }

    public void setListener(OnRapportListener listener) {
        this.listener = listener;
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView mineral_won;
        private TextView gas_won;
        private TextView defended_mineral_lose;
        private TextView defended_gas_lose;
        private TextView overlay;
        private TextView date_day;
        private LinearLayout win;
        private LinearLayout lose;
        private LinearLayout defended_won;
        private LinearLayout defended_lose;

        public ReportsViewHolder(View itemView) {
            super(itemView);
            overlay = (TextView) itemView.findViewById(R.id.rapport_overlay);
            date_day = (TextView) itemView.findViewById(R.id.date_day);
            date = (TextView) itemView.findViewById(R.id.rapport_date);
            gas_won = (TextView) itemView.findViewById(R.id.rapport_gasWon);
            mineral_won = (TextView) itemView.findViewById(R.id.rapport_MieralWon);
            defended_gas_lose = (TextView) itemView.findViewById(R.id.rapport_gasLose);
            defended_mineral_lose = (TextView) itemView.findViewById(R.id.rapport_MieralLose);
            win = (LinearLayout) itemView.findViewById(R.id.rapport_win_layout);
            lose = (LinearLayout) itemView.findViewById(R.id.rapport_lost_layout);
            defended_won = (LinearLayout) itemView.findViewById(R.id.rapport_defended_won_layout);
            defended_lose = (LinearLayout) itemView.findViewById(R.id.rapport_defended_lose_layout);
        }
    }
}
