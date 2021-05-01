package com.example.project3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.Food_Fragment;
import com.example.project3.R;
import com.example.project3.model.Food;

import java.util.Vector;

public class FoodResultAdapter extends RecyclerView.Adapter<FoodResultAdapter.FoodResultHolder> {

    private final Vector<Food> mFoodList;
    private LayoutInflater mInflater;
    private FragmentManager manager;

    public FoodResultAdapter(Context context, Vector<Food> foodList, FragmentManager manager) {
        mInflater = LayoutInflater.from(context);
        this.mFoodList = foodList;
        this.manager = manager;
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

    class FoodResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView foodResultView;
        final FoodResultAdapter mAdapter;

        public FoodResultHolder(View itemView, FoodResultAdapter adapter) {
            super(itemView);
            foodResultView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Food element = mFoodList.get(mPosition);

            Log.e(FoodResultHolder.class.getSimpleName(), "Clicked on " + element.getFoodName());
            Toast.makeText(mInflater.getContext(), "Clicked on cell", Toast.LENGTH_LONG).show();

            Food_Fragment food_fragment = new Food_Fragment();
            manager.beginTransaction().replace(R.id.fragment_container, food_fragment).commit();

        }
    }
}
