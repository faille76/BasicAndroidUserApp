package com.csulb.userapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csulb.userapp.Entity.Route;
import com.csulb.userapp.Entity.Stop;
import com.csulb.userapp.R;

import java.util.Map;


public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.StopViewHolder> {
    private Map<Integer, Stop> mStopList;
    private LayoutInflater mInflater;

    class StopViewHolder extends RecyclerView.ViewHolder {
        public final TextView stopItemView;
        public final TextView stopAddressItemView;
        final StopListAdapter mAdapter;

        public StopViewHolder(View itemView, StopListAdapter adapter){
            super(itemView);
            stopItemView = itemView.findViewById(R.id.stop_name);
            stopAddressItemView = itemView.findViewById(R.id.stop_address);
            this.mAdapter = adapter;
        }
    }

    public StopListAdapter(Context context, Route mRoute) {
        mInflater = LayoutInflater.from(context);
        this.mStopList = mRoute.stopList;
    }

    @Override
    public StopListAdapter.StopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.stop_item, parent, false);
        return new StopViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder stopViewHolder, int i) {
        stopViewHolder.stopItemView.setText(mStopList.get(i).name);
        stopViewHolder.stopAddressItemView.setText(mStopList.get(i).address);
    }

    @Override
    public int getItemCount() {
        return mStopList.size();
    }
}
