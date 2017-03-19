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
        private  ArrayList<String> urlImages;
        private Random r = new Random();



        public AdapterViewFleet(final Context context, ArrayList<Ship> values) {
            super(context, R.layout.adapter_list_fleet, values);
            this.context = context;
            this.values = values;
            urlImages = new ArrayList<String>();
            urlImages.add("https://s-media-cache-ak0.pinimg.com/originals/e0/3e/70/e03e7026394c97aab0a52a5a4283d24b.jpg");
            urlImages.add("http://www.vive-internet-gratuit.com/images/dessins/Vaisseau-spatial_49.jpg");
            urlImages.add("http://poopss.p.o.pic.centerblog.net/o/e04b1581.jpg");
            urlImages.add("http://www.stargate-fusion.com/images/news/divers/x301-schema-grand.jpg");
            urlImages.add("https://www.quizz.biz/uploads/quizz/140918/2_D9Z1T.jpg");

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
            ship_amount.setText(String.valueOf(aShip.getAmount()));
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
                        .load(urlImages.get(aShip.getShipId()))
                        .centerCrop()
                        .crossFade()
                        .into(ship_image);
            }
            return rowView;
        }
    }


