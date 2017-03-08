package mesa.com.outerspacemanager.outerspacemanager.buildings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;

/**
 * Created by Lucas on 07/03/2017.
 */

public class AdapterViewBuilding extends ArrayAdapter<Building> {

        private final Context context;
        private final ArrayList<Building> values;
        private Building aBuilding;
        public AdapterViewBuilding(Context context, ArrayList<Building> values) {
            super(context, R.layout.adapter_list_building, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.adapter_list_building, parent, false);

            aBuilding = values.get(position);

            TextView building_name = (TextView) rowView.findViewById(R.id.building_name_value);
            TextView building_effect = (TextView) rowView.findViewById(R.id.building_effect_value);
            TextView building_gas_cost = (TextView) rowView.findViewById(R.id.building_gas_cost_value);
            TextView building_mineral_cost = (TextView) rowView.findViewById(R.id.building_mineral_cost_value);
            TextView building_level = (TextView) rowView.findViewById(R.id.building_level);
            TextView building_time = (TextView) rowView.findViewById(R.id.building_time_value);

            String name = aBuilding.getName();
            String effect = aBuilding.getEffect();
            String gasCost = aBuilding.getGazCost().toString();
            String mineralCost = aBuilding.getMineralsCost().toString();
            String level = aBuilding.getLevel().toString();
            String time = aBuilding.getTimeToBuild().toString();

            building_name.setText(name);
            building_effect.setText(effect);
            building_gas_cost.setText(gasCost);
            building_mineral_cost.setText(mineralCost);
            building_level.setText(level);
            building_time.setText(time);

            return rowView;
        }
}
