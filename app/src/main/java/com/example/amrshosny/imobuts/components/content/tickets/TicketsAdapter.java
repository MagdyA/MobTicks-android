package com.example.amrshosny.imobuts.components.content.tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.amrshosny.imobuts.R;
import com.example.amrshosny.imobuts.api.json.Ticket;

import java.util.ArrayList;


public class TicketsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Ticket> mDataSource;

    public TicketsAdapter(Context context, ArrayList<Ticket> tickets) {
        mContext = context;
        mDataSource = tickets;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.ticket_listview_row, viewGroup, false);
        TextView code = (TextView) rowView.findViewById(R.id.code);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        code.setText(mDataSource.get(i).getCode());
        date.setText(mDataSource.get(i).getDate());
        price.setText(mDataSource.get(i).getPrice().toString());
        return rowView;
    }
}
