package com.keonasoft.splitcharge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kushal on 4/7/15.
 */
public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    protected ArrayList<PriceItem> data;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public TextView priceView;
        public ViewHolder(View v) {
            super(v);
            this.textView = (TextView) v.findViewById(R.id.list_item_text);
            this.priceView = (TextView) v.findViewById(R.id.list_item_price);
        }
    }

    public MyAdapter(ArrayList<PriceItem> myDataset) {
        data = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(data.get(i).getName());
        viewHolder.priceView.setText("$" + String.format( "%.2f", data.get(i).getPrice()));
    }

    public void refresh(ArrayList<PriceItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
