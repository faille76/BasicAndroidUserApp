package com.csulb.userapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csulb.userapp.Adapter.RecyclerItemClickListener;
import com.csulb.userapp.Adapter.RouteListAdapter;
import com.csulb.userapp.R;
import com.csulb.userapp.Repository.RouteRepository;

public class RouteListFragment extends Fragment {
    OnRouteSelectedListener mCallback;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public interface OnRouteSelectedListener {
        void onRouteSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.route_list_view, container, false);
        recyclerView = view.findViewById(R.id.route_list_recycler_view);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RouteListAdapter(getContext(), RouteRepository.getInstance().getRoutes());
        recyclerView.setAdapter(mAdapter);
        mCallback = (OnRouteSelectedListener)getActivity();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mCallback.onRouteSelected(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        return view;
    }
}