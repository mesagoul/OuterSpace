package mesa.com.outerspacemanager.outerspacemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.buildings.Building;

/**
 * Created by Lucas on 07/03/2017.
 */

public class AdapterViewGalaxie  extends ArrayAdapter<User> {

        private final Context context;
        private final ArrayList<User> values;
        private User aUser;
        public AdapterViewGalaxie(Context context, ArrayList<User> values) {
            super(context, R.layout.adapter_list_galaxie, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.adapter_list_galaxie, parent, false);

            aUser = values.get(position);

            TextView galaxie_point = (TextView) rowView.findViewById(R.id.galaxie_point_value);
            TextView galaxie_name = (TextView) rowView.findViewById(R.id.galaxie_name_value);



            return rowView;
        }
}
