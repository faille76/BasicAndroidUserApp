package com.csulb.userapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csulb.userapp.Entity.Route;
import com.csulb.userapp.Entity.RouteList;
import com.csulb.userapp.R;

import java.util.Map;


public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteViewHolder> {
    private Map<Integer, Route> mRouteList;
    private LayoutInflater mInflater;

    class RouteViewHolder extends RecyclerView.ViewHolder {
        public final TextView routeItemView;
        final RouteListAdapter mAdapter;

        public RouteViewHolder(View itemView, RouteListAdapter adapter){
            super(itemView);
            routeItemView = itemView.findViewById(R.id.route_name);
            this.mAdapter = adapter;
        }
    }

    public RouteListAdapter(Context context, RouteList mRouteList) {
        mInflater = LayoutInflater.from(context);
        this.mRouteList = mRouteList.getListRoute();
    }

    @Override
    public RouteListAdapter.RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.route_item, parent, false);
        return new RouteViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder routeViewHolder, int i) {
        String mCurrent = mRouteList.get(i).name;
        routeViewHolder.routeItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }
}
