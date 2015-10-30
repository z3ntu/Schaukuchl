package io.github.z3ntu.schaukuchl;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luca on 20.07.15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Food> mDataset;

    public RecyclerViewAdapter() {
        mDataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mName.setText(mDataset.get(i).name);
        viewHolder.mPrice.setText(mDataset.get(i).price);
        if (mDataset.get(i).aus) {
            viewHolder.mName.setTextColor(Color.RED);
            viewHolder.mPrice.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(Food food) {
        mDataset.add(food);
        notifyItemInserted(mDataset.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mPrice;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name_text);
            mPrice = (TextView) v.findViewById(R.id.price_text);
        }
    }


}
