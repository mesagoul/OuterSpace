package mesa.com.outerspacemanager.outerspacemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mesa.com.outerspacemanager.outerspacemanager.R;
import mesa.com.outerspacemanager.outerspacemanager.model.User;

/**
 * Created by Lucas on 07/03/2017.
 */

public class AdapterViewGalaxie  extends ArrayAdapter<User> {

        private final Context context;
        private final ArrayList<User> values;
        private User aUser;
        private Integer cpt;
        public AdapterViewGalaxie(Context context, ArrayList<User> values) {
            super(context, R.layout.adapter_list_galaxie, values);
            this.context = context;
            this.values = values;
            this.cpt = 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.adapter_list_galaxie, parent, false);

            aUser = values.get(position);
            this.cpt++;

            TextView galaxie_point = (TextView) rowView.findViewById(R.id.galaxie_point_value);
            TextView galaxie_name = (TextView) rowView.findViewById(R.id.galaxie_name_value);
            TextView galaxie_rank = (TextView) rowView.findViewById(R.id.galaxie_rank_value);

            galaxie_name.setText(aUser.getUsername());
            galaxie_point.setText(aUser.getPoints().toString());
            galaxie_rank.setText(cpt.toString());



            return rowView;
        }
}
