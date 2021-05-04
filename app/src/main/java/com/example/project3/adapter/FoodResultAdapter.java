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
         * instance of the adapter class associated with each ViewHolder object
         */
        final FoodResultAdapter mAdapter;

        /**
         * constructs the FoodResultHolder and sets an OnClickListener
         * @param itemView the View object for the Holder
         * @param adapter the FoodResultAdapter saved in each Holder
         */
        public FoodResultHolder(View itemView, FoodResultAdapter adapter) {
            super(itemView);
            foodResultView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this::onClick);
        }

        /**
         * gets the Food object associated with the cell that was clicked and starts a NewFoodActivity
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
     * data structure that holds all Food objects initialized in Search_Fragment
     */
    private final Vector<Food> mFoodList;

    /**
     * saves the LayoutInflater from the context fo the Activity that calls FoodResultAdapter
     */
    private LayoutInflater mInflater;

    /**
     * saves the Context of the activity that calls FoodResultAdapter
     */
    private Context mContext;

    /**
     * FoodResultAdapter constructor
     * @param context initializes mContext and mInflater
     * @param foodList initializes mFoodList
     */
    public FoodResultAdapter(Context context, Vector<Food> foodList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mFoodList = foodList;

    }

    /**
     * creates a FoodResultHolder for each cell when the view is created
     * @param parent used as the root to initialize a View
     * @param viewType is not used
     * @return returns a FoodResultHolder for each ViewHolder
     */
    @NonNull
    @Override
    public FoodResultAdapter.FoodResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.search_result_item, parent, false);

        return new FoodResultHolder(mItemView, this);
    }

    /**
     * sets the text for each foodResultView with its position in the RecyclerView and mFoodList
     * @param holder a FoodResultHolder at a certain position
     * @param position the index number of the FoodResultHolder
     */
    @Override
    public void onBindViewHolder(@NonNull FoodResultAdapter.FoodResultHolder holder, int position) {
        String mCurrent = mFoodList.get(position).getFoodName();
        holder.foodResultView.setText(mCurrent);

    }

    /**
     * gets the size of mFoodList so the RecyclerView knows how many cells it needs
     * @return the size of mFoodList
     */
    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

}
