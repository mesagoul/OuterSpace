package mesa.com.outerspacemanager.outerspacemanager.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;

/**
 * Created by Lucas on 13/03/2017.
 */

public class AdapterViewChantier extends ArrayAdapter<Ship> {

    private final Context context;
    private final ArrayList<Ship> values;
    private Ship aShip;

    public AdapterViewChantier(Context context, ArrayList<Ship> values) {
        super(context, R.layout.adapter_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_list_ship, parent, false);

        ImageView ship_image = (ImageView) rowView.findViewById(R.id.ship_image_url);
        ImageView ship_life_image = (ImageView) rowView.findViewById(R.id.ship_life_image);
        TextView ship_life_value = (TextView) rowView.findViewById(R.id.ship_life_value);
        TextView ship_name = (TextView) rowView.findViewById(R.id.ship_name_value);
        TextView ship_cost_gas = (TextView) rowView.findViewById(R.id.ship_cost_gas_value);
        TextView ship_cost_mineral = (TextView) rowView.findViewById(R.id.ship_cost_mineral_value);


        aShip = values.get(position);

        ship_life_value.setText(String.valueOf(aShip.getLife()));
        ship_cost_gas.setText(String.valueOf(aShip.getGasCost()));
        ship_cost_mineral.setText(String.valueOf(aShip.getMineralCost()));
        ship_name.setText(aShip.getName());


        Glide
                .with(getContext())
                .load("https://cdn.pixabay.com/photo/2012/04/10/17/23/spaceship-26556_960_720.png")
                .centerCrop()
                .crossFade()
                .into(ship_image);

        Glide
                .with(getContext())
                .load("https://s-media-cache-ak0.pinimg.com/originals/ff/8d/e7/ff8de7deab4afdc484bc1b1aa9637491.png")
                .centerCrop()
                .crossFade()
                .into(ship_life_image);

        return rowView;
    }
}
