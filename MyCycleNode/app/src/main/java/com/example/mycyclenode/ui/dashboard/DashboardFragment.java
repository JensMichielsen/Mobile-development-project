package com.example.mycyclenode.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mycyclenode.R;
import com.example.mycyclenode.gateways.FirebaseUtil;
import com.example.mycyclenode.models.CycleNode;
import com.example.mycyclenode.ui.detail.DetailFragment;
import com.example.mycyclenode.util.Logger;

import java.util.UUID;

public class DashboardFragment extends Fragment {
    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardViewModel dashboardViewModel;
    private View mView;
    private Button mButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mButton = mView.findViewById(R.id.send_button);
        mButton.setOnClickListener(v -> {
            EditText nodeText = (EditText) mView.findViewById(R.id.edit_node_number);
            EditText cityText = (EditText) mView.findViewById(R.id.edit_city);
            EditText provinceText = (EditText) mView.findViewById(R.id.edit_province);
            EditText coordinatesText = (EditText) mView.findViewById(R.id.edit_coordinates);
            TextView infoText = (TextView) mView.findViewById(R.id.text_info);

            String nodeNumber = "";
            String city= "";
            String province = "";
            String coordinates = "";
            try {
                nodeNumber = nodeText.getText().toString();
                city = cityText.getText().toString();
                province = provinceText.getText().toString();
                coordinates = coordinatesText.getText().toString();
            }
            catch (Exception e) {
                infoText.setText("Please fill in all the required info.");
            }

            if (isFormValid(nodeNumber, city, province)) {
                CycleNode cycleNode = new CycleNode(UUID.randomUUID().toString(), Integer.parseInt(nodeNumber), city, province, coordinates);

                FirebaseUtil.SaveCycleNode(cycleNode);
                infoText.setText("Info saved!");
            }
            else {
                infoText.setText("Please fill in all the required info.");
            }
        });
        return mView;
    }

    private boolean isFormValid(String nodeNumber, String city, String province) {
        boolean isValid = true;

        try {
            Integer.parseInt(nodeNumber);
        } catch (NumberFormatException e) {
            isValid = false;
        }
        if (city.length() < 1) isValid = false;
        if (province.length() < 5) isValid = false;
        return isValid;
    }
}