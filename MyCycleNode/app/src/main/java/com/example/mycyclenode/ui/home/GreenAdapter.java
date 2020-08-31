package com.example.mycyclenode.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.mycyclenode.R;

import com.example.mycyclenode.models.CycleNode;

import java.util.ArrayList;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickLister;

    private int mNumberOfItems = 0;
    private ArrayList<CycleNode> mCycleNodeArrayList;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public GreenAdapter(ListItemClickListener listener) {
        mOnClickLister = listener;
    }

    public void setCycleNodes(ArrayList<CycleNode> cycleNodes) {
        mCycleNodeArrayList = cycleNodes;
        mNumberOfItems = mCycleNodeArrayList.size();
        notifyItemInserted(mCycleNodeArrayList.size()-1);
    }

    public void filterCycleNodes(String filter) {
        ArrayList<CycleNode> secondList = new ArrayList<>();
        for( CycleNode a : mCycleNodeArrayList) {
            if (a.City.equals(filter)) secondList.add(a);
        }
        mCycleNodeArrayList = secondList;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.node_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }


    // For caching the items of the RecyclerView
    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public NumberViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.item_node_number);
            imageView = (ImageView) itemView.findViewById(R.id.item_node_image);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            CycleNode cycleNode = mCycleNodeArrayList.get(listIndex);
            textView.setText(String.valueOf(cycleNode.CycleNode + " - " + cycleNode.City ));
            switch (cycleNode.Province.toLowerCase()) {
                case "limburg":
                    imageView.setImageResource(R.mipmap.flag_belgian_limburg_forground);
                    return;
                case "antwerpen":
                    imageView.setImageResource(R.mipmap.flag_antwerp_foreground);
                    return;
                case "luik":
                    imageView.setImageResource(R.mipmap.flag_liege_foreground);
                    return;
                case "vlaams-brabant":
                    imageView.setImageResource(R.mipmap.flag_flemish_brabant_foreground);
                    return;
            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickLister.onListItemClick(clickedPosition);
        }
    }
}
