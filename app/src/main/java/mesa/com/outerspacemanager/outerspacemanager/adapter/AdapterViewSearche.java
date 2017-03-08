package mesa.com.outerspacemanager.outerspacemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.Searche;

/**
 * Created by Lucas on 07/03/2017.
 */

public class AdapterViewSearche extends ArrayAdapter<Searche> {

        private final Context context;
        private final ArrayList<Searche> values;
        private Searche aSearche;
        public AdapterViewSearche(Context context, ArrayList<Searche> values) {
            super(context, R.layout.adapter_list_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.adapter_list_item, parent, false);

            aSearche = values.get(position);

            TextView searche_name = (TextView) rowView.findViewById(R.id.building_name_value);
            TextView searche_effect = (TextView) rowView.findViewById(R.id.building_effect_value);
            TextView searche_gas_cost = (TextView) rowView.findViewById(R.id.building_gas_cost_value);
            TextView searche_mineral_cost = (TextView) rowView.findViewById(R.id.building_mineral_cost_value);
            TextView searche_level = (TextView) rowView.findViewById(R.id.building_level);
            TextView searche_time = (TextView) rowView.findViewById(R.id.building_time_value);

            String name = aSearche.getName();
            String effect = aSearche.getEffect();
            String gasCost = aSearche.getGazCost().toString();
            String mineralCost = aSearche.getMineralsCost().toString();
            String level = aSearche.getLevel().toString();
            String time = aSearche.getTimeToBuild().toString();

            searche_name.setText(name);
            searche_effect.setText(effect);
            searche_gas_cost.setText(gasCost);
            searche_mineral_cost.setText(mineralCost);
            searche_level.setText(level);
            searche_time.setText(time);

            return rowView;
        }
}
