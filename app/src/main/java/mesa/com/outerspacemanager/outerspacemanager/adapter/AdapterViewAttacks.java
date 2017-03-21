    package mesa.com.outerspacemanager.outerspacemanager.adapter;

    import android.content.Context;
    import android.os.Handler;
    import android.support.v7.widget.RecyclerView;
    import android.text.format.DateUtils;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;

    import mesa.com.outerspacemanager.outerspacemanager.R;
    import mesa.com.outerspacemanager.outerspacemanager.db.AttackSataSource;
    import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
    import mesa.com.outerspacemanager.outerspacemanager.OnAttackClickedListener;

    /**
     * Created by Lucas on 20/03/2017.
     */


    public class AdapterViewAttacks extends RecyclerView.Adapter<AdapterViewAttacks.AttaksViewHolder>{
    private final Context context;
    private final List<Attack> listattacks;



        private OnAttackClickedListener lister;

    public AdapterViewAttacks(Context context, List<Attack> attacks) {
            this.listattacks = attacks;
            this.context = context;
            }



        @Override
        public AdapterViewAttacks.AttaksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.adapter_list_attacks, parent, false);
                AdapterViewAttacks.AttaksViewHolder viewHolder = new AdapterViewAttacks.AttaksViewHolder(rowView);
                viewHolder.handler = new Handler();




                return viewHolder;
                }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return 0;
            }else{
                return 1;
            }
        }

        @Override
    public void onBindViewHolder(final AdapterViewAttacks.AttaksViewHolder holder, int position) {
            final Attack anAttack = listattacks.get(position);

            if(System.currentTimeMillis() > anAttack.getAttack_time()){
                holder.date.setText("Attaque terminée");
                holder.userAttacked.setText(anAttack.getUsername());
                AttackSataSource db = new AttackSataSource(context);
                db.open();
                db.deleteAttack(anAttack);
                db.close();
            } else {

                if (holder.runnable != null){
                    holder.handler.removeCallbacks(holder.runnable);
                }

                holder.runnable = new Runnable() {
                    @Override
                    public void run() {
                        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Date date = new Date(anAttack.getAttack_time() - System.currentTimeMillis());
                        String dateFormatted = formatter.format(date);
                        holder.date.setText(dateFormatted);

                        holder.handler.postDelayed(this, 1000);

                    }
                };

                holder.handler.post(holder.runnable);

                holder.userAttacked.setText(anAttack.getUsername());
                holder.progress.setProgress(anAttack.getProgress());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lister.onAttackClicked(anAttack);
                    }
                });
            }
        }

        public void setListner(OnAttackClickedListener lister) {
            this.lister = lister;
        }



    @Override
    public int getItemCount() {
            return listattacks.size();
            }

    public class AttaksViewHolder extends RecyclerView.ViewHolder {
        private TextView userAttacked;
        private TextView date;
        private ProgressBar progress;
        private Handler handler;
        private Runnable runnable;

        public AttaksViewHolder(View itemView)
        {
            super(itemView);
            userAttacked = (TextView) itemView.findViewById(R.id.attack_username);
            date = (TextView) itemView.findViewById(R.id.attack_date);
            progress = (ProgressBar) itemView.findViewById(R.id.attack_progress);
        }
    }
    }
