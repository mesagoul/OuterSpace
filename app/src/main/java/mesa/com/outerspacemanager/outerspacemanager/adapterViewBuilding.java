package mesa.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Lucas on 07/03/2017.
 */

public class adapterViewBuilding extends ArrayAdapter<Building> {

        private final Context context;
        private final ArrayList<Building> values;
        private Building aBuilding;
        public adapterViewBuilding(Context context,  ArrayList<Building> values) {
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

            TextView building_name = (TextView) rowView.findViewById(R.id.building_name);
            building_name.setText(aBuilding.getName());

            return rowView;
        }
}
