package com.example.mycyclenode.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycyclenode.R;
import com.example.mycyclenode.constants.LifecycleConstants;
import com.example.mycyclenode.gateways.FirebaseUtil;
import com.example.mycyclenode.models.CycleNode;
import com.example.mycyclenode.ui.detail.DetailFragment;
import com.example.mycyclenode.ui.logout.LogoutActivity;
import com.example.mycyclenode.ui.masterdetail.MasterDetailFragment;
import com.example.mycyclenode.util.Logger;
import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements GreenAdapter.ListItemClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "cycleNodeArrayCallback";

    private ArrayList<CycleNode> cycleNodeArrayList;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    private int mFrameOrientation;

    private GreenAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Existing code
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Make menu for this Fragment visible
        setHasOptionsMenu(true);

        // Wire up adapter
        mRecyclerView = (RecyclerView) root.findViewById(R.id.list_view_cycle_nodes);
        mAdapter = new GreenAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
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

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUtil.resumeConnection();

        Logger.infoLog(TAG, LifecycleConstants.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        FirebaseUtil.pauseConnection();

        Logger.infoLog(TAG, LifecycleConstants.ON_PAUSE);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        CycleNode selectedCycleNode = cycleNodeArrayList.get(clickedItemIndex);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DetailFragment detailFragment = new DetailFragment();
            androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cycleNode", selectedCycleNode);
            detailFragment.setArguments(bundle);
            ft.replace(R.id.nav_host_fragment, detailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.logout) {
            Intent intent = new Intent(this.getContext(), LogoutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        Logger.infoLog(TAG, "config changed");
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            mFrameOrientation = Configuration.ORIENTATION_PORTRAIT;

        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mFrameOrientation = Configuration.ORIENTATION_LANDSCAPE;

            MasterDetailFragment masterDetailFragment = new MasterDetailFragment();
            androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, masterDetailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
        else
            Logger.infoLog(TAG, "other: " + orientation);
    }
}