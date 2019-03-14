package com.csulb.userapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csulb.userapp.Adapter.StopListAdapter;
import com.csulb.userapp.Entity.Route;
import com.csulb.userapp.R;
import com.csulb.userapp.Repository.RouteRepository;

public class StopListFragment extends Fragment {
    public static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    TextView routeItem;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        View view = inflater.inflate(R.layout.stop_list_view, container, false);

        recyclerView = view.findViewById(R.id.stop_list_recycler_view);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateRouteView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateRouteView(mCurrentPosition);
        }
    }

    public void updateRouteView(int position) {
        Log.i("Update route", "Got position " + position);
        Route route = RouteRepository.getInstance().getRoutes().getListRoute().get(position);
        if (route != null) {

            mAdapter = new StopListAdapter(getContext(), route);
            recyclerView.setAdapter(mAdapter);
            mCurrentPosition = position;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}