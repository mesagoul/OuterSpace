    package mesa.com.outerspacemanager.outerspacemanager.adapter;

    import android.content.Context;
    import android.os.Handler;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.SeekBar;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;

    import java.util.ArrayList;
    import java.util.Random;

    import mesa.com.outerspacemanager.outerspacemanager.R;
    import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

    /**
     * Created by Lucas on 15/03/2017.
     */

    public class AdapterViewFleet extends ArrayAdapter<Ship> {
        private final Context context;
        private final ArrayList<Ship> values;
        private Ship aShip;



        public AdapterViewFleet(final Context context, ArrayList<Ship> values) {
            super(context, R.layout.adapter_list_fleet, values);
            this.context = context;
            this.values = values;

        }

        public int getCount() {
            return values.size();
        }

        public Ship getItem(int position) {
            return values.get(position);
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {

            final LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.adapter_list_fleet, parent, false);
            if (getCount() == 0) {
                return rowView;
            }


            ImageView ship_image = (ImageView) rowView.findViewById(R.id.ship_image);
            TextView ship_name = (TextView) rowView.findViewById(R.id.ship_name);
            final TextView ship_amount = (TextView) rowView.findViewById(R.id.ship_amount);
            SeekBar seekBar = (SeekBar) rowView.findViewById(R.id.ship_amount_seekbar);


            aShip = values.get(position);
            aShip.getUrlImage();
            ship_amount.setText(String.valueOf(aShip.getAmount()));
            if(aShip.getAmount() == 0){
                seekBar.setVisibility(View.GONE);
            }
            ship_name.setText(aShip.getName());
            seekBar.setMax(aShip.getAmount());
            seekBar.setProgress(aShip.getAmount());


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        ship_amount.setText(String.valueOf(seekBar.getProgress()));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


            if (ship_image != null) {

                Glide
                        .with(getContext())
                        .load(aShip.getUrlImage())
                        .centerCrop()
                        .crossFade()
                        .into(ship_image);
            }
            return rowView;
        }
    }


