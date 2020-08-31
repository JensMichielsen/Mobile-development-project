package com.example.mycyclenode.ui.notifications;

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
import com.example.mycyclenode.ui.dashboard.DashboardFragment;

public class NotificationsFragment extends Fragment {
    private static final String TAG = NotificationsFragment.class.getSimpleName();

    private View mView;
    private Button mButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_notifications, container, false);

        mButton = mView.findViewById(R.id.send_button);
        mButton.setOnClickListener(v -> {
            EditText fontSizeText = (EditText) mView.findViewById(R.id.edit_font_size);

        });

        return mView;
    }
}