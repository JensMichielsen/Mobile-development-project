package com.example.mycyclenode.gateways;

import android.util.Log;

import com.example.mycyclenode.models.CycleNode;
import com.example.mycyclenode.util.Logger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    private static final String TAG = FirebaseUtil.class.getSimpleName();

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<CycleNode> mCycleNodes;

    private FirebaseUtil() {}

    public static void openFirebaseReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mCycleNodes = new ArrayList<>();
        }
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    public static void pauseConnection() {
        mFirebaseDatabase.goOffline();
        Logger.infoLog(TAG, "Pausing Firebase connection");
    }

    public static void resumeConnection() {
        mFirebaseDatabase.goOnline();
        Logger.infoLog(TAG, "Resuming Firebase connection");
    }

    public static void SaveCycleNode(CycleNode cycleNode) {
        mDatabaseReference.push().setValue(cycleNode);
        Log.i(TAG, "SaveCycleNode: " + cycleNode.toString());
    }
}
