package com.example.mycyclenode.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycyclenode.R;
import com.example.mycyclenode.models.CycleNode;
import com.example.mycyclenode.ui.home.HomeFragment;
import com.example.mycyclenode.util.Logger;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "currentCycleNode";

    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState"; // TODO: extract to sep file

    private CycleNode mCycleNode;
    private View mView;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                CycleNode cycleNode = (CycleNode) savedInstanceState.getSerializable(LIFECYCLE_CALLBACKS_TEXT_KEY);
                Logger.infoLog(TAG, "get CycleNode from state: ", cycleNode);
                updateView(cycleNode);
            }
        }
        else {
            Logger.infoLog(TAG, "attempting to serialize");
            CycleNode cycleNode = (CycleNode) getArguments().getSerializable("cycleNode");

            updateView(cycleNode);
        }
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIFECYCLE_CALLBACKS_TEXT_KEY, mCycleNode);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_top_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.back) {
            HomeFragment homeFragment = new HomeFragment();
            androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, homeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.addToBackStack(null);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateView(CycleNode cycleNode) {
        mCycleNode = cycleNode;
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
