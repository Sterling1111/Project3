package com.example.project3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project3.adapter.FoodResultAdapter;
import com.example.project3.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * The Fragment object used to define the behavior of the search page of the application
 */
public class Search_Fragment extends Fragment {

    /**
     * a TextView that the user can enter a search query into
     */
    private EditText mSearchEditText;

    /**
     * Stores the name of the class for printing to the Log
     */
    private final String TAG = Search_Fragment.class.getSimpleName();

    /**
     * A RecyclerView.Adapter that populates the RecyclerView with Food objects from foodList
     */
    private FoodResultAdapter mAdapter;

    /**
     * A vector holding the search results that have been read into Food objects
     */
    private final Vector<Food> foodList = new Vector<>();

    /**
     * initializes fields when the fragment is launched from the bottom navigation toolbar, and
     * defines a OnKeyListener for mSearchEditText
     * @param inflater used to initialize the View object that is returned
     * @param container is the View that contains the Search_Fragment
     * @param savedInstanceState a nullable object used to pass arguments between Fragments
     *                           and activities
     * @return returns a view containing the Search_Fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_, container, false);
        mSearchEditText = v.findViewById(R.id.search_box);

        //listens for a enter key press in mSearchEditText and hides the keyboard and calls doSearch
        mSearchEditText.setOnKeyListener((v1, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager imm = (InputMethodManager)getActivity()
                        .getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                doSearch(mSearchEditText.getText().toString());
                return true;
            }
            return false;
        });

        RecyclerView mRecyclerView = v.findViewById(R.id.results_list);
        mAdapter = new FoodResultAdapter(getActivity(), foodList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    /**
     * makes a call to the USDA's FoodData Center and retrieves a JSON Object
     * @param sq the search term passed to FoodData
     */
    private void doSearch(String sq) {
        Log.e(TAG, "Query passed to function: " + sq);
        String apiKey = "bGsZ45bJXihI7OhZmH5E3SpnPN0WUXHx24hhAU5l";
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=" + apiKey +
                "&query=" + sq +"&dataType=Branded&requireAllWords=true";

        //clear the vector every time a new search is made
        foodList.clear();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //initializes an asynchronous request that call populateList on completion
        JsonObjectRequest searchQuery = new JsonObjectRequest(Request.Method.GET,
                url, null, this::populateList,
                error -> Log.e(TAG, "JsonObjectRequest error" + error.getMessage()));

        queue.add(searchQuery);
    }

    /**
     * parses the JSON Object and initializes Food objects stored in foodList that will use the
     * FoodResultAdapter to display contents in the RecyclerView
     * @param jsonObj JSON object that will be parsed for Food objects
     */
    private void populateList(JSONObject jsonObj) {
        if (jsonObj != null) {
            Log.e(TAG, "Response from url: " + jsonObj.toString());
            try {
                JSONArray foods = jsonObj.getJSONArray("foods");

                for (int i = 0; i < foods.length(); i++) {

                    JSONObject f = foods.getJSONObject(i);
                    String foodName = f.getString("description");
                    Food newFood = new Food();
                    newFood.setFoodName(foodName);

                    JSONArray nutrients = f.getJSONArray("foodNutrients");

                        for (int j = 0; j < nutrients.length(); j++) {
                            JSONObject n = nutrients.getJSONObject(j);
                            String nutrientName = n.getString("nutrientName");
                            Float value = (float) n.getDouble("value");

                            switch (nutrientName) {
                                case "Protein":
                                    newFood.setProtein(value);
                                    break;

                                case "Total lipid (fat)":
                                    newFood.setTotalFat(value);
                                    break;

                                case "Carbohydrate, by difference":
                                    newFood.setTotalCarb(value);
                                    break;

                                case "Energy":
                                    newFood.setCaloriesPerServing(value);
                                    break;
                            }
                        }
                    foodList.add(newFood);
                }
                mAdapter.notifyDataSetChanged();

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}