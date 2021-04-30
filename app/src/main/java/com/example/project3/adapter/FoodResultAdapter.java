package com.example.project3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.R;
import com.example.project3.model.Food;

import java.util.Vector;

public class FoodResultAdapter extends RecyclerView.Adapter<FoodResultAdapter.FoodResultHolder> {

    private final Vector<Food> mFoodList;
    private LayoutInflater mInflater;

    public FoodResultAdapter(Context context, Vector<Food> foodList) {
        mInflater = LayoutInflater.from(context);
        this.mFoodList = foodList;
    }

    @NonNull
    @Override
    public FoodResultAdapter.FoodResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.search_result_item, parent, false);

        return new FoodResultHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodResultAdapter.FoodResultHolder holder, int position) {
        String mCurrent = mFoodList.get(position).getFoodName();
        holder.foodResultView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    class FoodResultHolder extends RecyclerView.ViewHolder {
        public final TextView foodResultView;
        final FoodResultAdapter mAdapter;

        public FoodResultHolder(View itemView, FoodResultAdapter adapter) {
            super(itemView);
            foodResultView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }
}
