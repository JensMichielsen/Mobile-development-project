package com.example.mycyclenode.ui.masterdetail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycyclenode.R;
import com.example.mycyclenode.gateways.FirebaseUtil;
import com.example.mycyclenode.models.CycleNode;
import com.example.mycyclenode.ui.detail.DetailFragment;
import com.example.mycyclenode.ui.home.GreenAdapter;
import com.example.mycyclenode.ui.home.HomeFragment;
import com.example.mycyclenode.util.Logger;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MasterDetailFragment extends Fragment implements GreenAdapter.ListItemClickListener {
    private static final String TAG = MasterDetailFragment.class.getSimpleName();

    private ArrayList<CycleNode> cycleNodeArrayList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private GreenAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_detail, container, false);

        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list_view_cycle_nodes_2);

        mAdapter = new GreenAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Firebase
        FirebaseUtil.openFirebaseReference("cyclenodes");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        cycleNodeArrayList = new ArrayList<>();

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CycleNode cycleNode = snapshot.getValue(CycleNode.class);
                cycleNodeArrayList.add(cycleNode);
                cycleNode.Id = snapshot.getKey();
                mAdapter.setCycleNodes(cycleNodeArrayList);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        return mView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        CycleNode selectedCycleNode = cycleNodeArrayList.get(clickedItemIndex);
        updateView(selectedCycleNode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Logger.infoLog(TAG, "Navigating to HomeFragment");

            HomeFragment homeFragment = new HomeFragment();
            androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, homeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    public void updateView(CycleNode cycleNode) {
        Logger.infoLog(TAG, "updateView");

        TextView nodeText = (TextView) mView.findViewById(R.id.text_node_number);
        TextView cityText = (TextView) mView.findViewById(R.id.text_city);
        TextView provinceText = (TextView) mView.findViewById(R.id.text_province);
        TextView coordinatesText = (TextView) mView.findViewById(R.id.text_coordinates);

        nodeText.setText(Integer.toString(cycleNode.CycleNode));
        cityText.setText(cycleNode.City);
        provinceText.setText(cycleNode.Province);
        coordinatesText.setText(cycleNode.Coordinates);
    }
}
