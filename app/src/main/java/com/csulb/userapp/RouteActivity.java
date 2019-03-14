package com.csulb.userapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.csulb.userapp.Fragment.StopListFragment;
import com.csulb.userapp.Fragment.RouteListFragment;
import com.csulb.userapp.Repository.RouteRepository;


public class RouteActivity extends FragmentActivity
        implements RouteListFragment.OnRouteSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_frag);

        RouteRepository.getInstance();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            RouteListFragment firstFragment = new RouteListFragment();
            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onRouteSelected(int position) {
        StopListFragment routeFrag = (StopListFragment)
                getSupportFragmentManager().findFragmentById(R.id.route_item_fragment);

        if (routeFrag != null) {
            routeFrag.updateRouteView(position);
        } else {
            StopListFragment newFragment = new StopListFragment();
            Bundle args = new Bundle();
            args.putInt(StopListFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
