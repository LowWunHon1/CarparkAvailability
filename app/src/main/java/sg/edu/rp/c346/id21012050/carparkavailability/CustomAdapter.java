package sg.edu.rp.c346.id21012050.carparkavailability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Carpark> carparkList;

    public CustomAdapter(Context context, int resource, ArrayList<Carpark> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        carparkList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTotalLots = rowView.findViewById(R.id.textViewTotalLots);
        TextView tvLotType = rowView.findViewById(R.id.textViewLotType);
        TextView tvLotsAvailable = rowView.findViewById(R.id.textViewLotsAvailable);
        TextView tvCarparkNumber = rowView.findViewById(R.id.textViewCarparkNumber);
        TextView tvUpdateDatetime = rowView.findViewById(R.id.textViewUpdateDatetime);

        Carpark currentItem = carparkList.get(position);
        tvTotalLots.setText(currentItem.getTotal_lots());
        tvLotType.setText(currentItem.getLot_type());
        tvLotsAvailable.setText(currentItem.getLots_available());
        tvCarparkNumber.setText(currentItem.getCarpark_number());
        tvUpdateDatetime.setText(currentItem.getUpdate_datetime());

        return rowView;
    }

}
