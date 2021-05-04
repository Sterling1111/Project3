package com.example.project3.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.NewFoodActivity;
import com.example.project3.R;
import com.example.project3.model.Food;

import java.util.Vector;

/**
 * adapter class for populating RecyclerView in Search_Fragment with data from FoodData database
 */
public class FoodResultAdapter extends RecyclerView.Adapter<FoodResultAdapter.FoodResultHolder> {

    /**
     * ViewHolder inner class that initializes each row of the RecyclerView
     */
    class FoodResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * displays the name of a Food Object
         */
        public final TextView foodResultView;

        /**
         *
         */
        final FoodResultAdapter mAdapter;

        /**
         *
         * @param itemView
         * @param adapter
         */
        public FoodResultHolder(View itemView, FoodResultAdapter adapter) {
            super(itemView);
            foodResultView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this::onClick);
        }

        /**
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Food element = mFoodList.get(mPosition);

            Log.e(FoodResultHolder.class.getSimpleName(), "Clicked on " + element.getFoodName());

            Intent intent = new Intent(mContext, NewFoodActivity.class);
            mContext.startActivity(intent.putExtra("food", element));
        }
    }

    /**
     *
     */
    private final Vector<Food> mFoodList;

    /**
     *
     */
    private LayoutInflater mInflater;

    /**
     *
     */
    private Context mContext;

    /**
     *
     * @param context
     * @param foodList
     */
    public FoodResultAdapter(Context context, Vector<Food> foodList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mFoodList = foodList;

    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public FoodResultAdapter.FoodResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.search_result_item, parent, false);

        return new FoodResultHolder(mItemView, this);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull FoodResultAdapter.FoodResultHolder holder, int position) {
        String mCurrent = mFoodList.get(position).getFoodName();
        holder.foodResultView.setText(mCurrent);

    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

}
