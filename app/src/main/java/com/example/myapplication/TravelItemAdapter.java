package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
public class TravelItemAdapter extends RecyclerView.Adapter<TravelItemAdapter.ViewHolder> implements Filterable {

    private ArrayList<TravelItem> mTravelItemData = new ArrayList<>();
    private ArrayList<TravelItem> mTravelItemDataAll = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;

    TravelItemAdapter(Context context, ArrayList<TravelItem> itemsData) {
        this.mTravelItemData = itemsData;
        this.mTravelItemDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list, parent,false));
    }

    @Override
    public int getItemCount() {
        return mTravelItemData.size();
    }

    @Override
    public void onBindViewHolder(TravelItemAdapter.ViewHolder holder, int position) {
        TravelItem currentItem=mTravelItemData.get(position);
        holder.bindTo(currentItem);

    }

    @Override
    public Filter getFilter() {
        return travelFilter;
    }
    private Filter travelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TravelItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mTravelItemDataAll.size();
                results.values = mTravelItemDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(TravelItem item : mTravelItemDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mTravelItemData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;

        public ViewHolder( View itemView) {
            super(itemView);
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

        }

        public void bindTo(TravelItem currentItem) {
            mTitleText.setText(currentItem.getName());
            mInfoText.setText(currentItem.getInfo());

            mRatingBar.setRating(currentItem.getRated());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

        }
    }
}

