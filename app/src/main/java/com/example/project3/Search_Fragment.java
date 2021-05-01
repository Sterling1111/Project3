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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project3.adapter.FoodResultAdapter;
import com.example.project3.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class Search_Fragment extends Fragment {
    private EditText mSearchEditText;
    private final String TAG = Search_Fragment.class.getSimpleName();
    private String searchQuery;
    private RecyclerView mRecyclerView;
    private FoodResultAdapter mAdapter;
    private final Vector<Food> foodlist = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_, container, false);
        mSearchEditText = v.findViewById(R.id.search_box);
        mSearchEditText.setOnKeyListener((v1, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                searchQuery = mSearchEditText.getText().toString();
                doSearch(searchQuery);
                return true;
            }
            return false;
        });

        mRecyclerView = v.findViewById(R.id.results_list);
        mAdapter = new FoodResultAdapter(getActivity(), foodlist, getParentFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    private void doSearch(String sq) {
        Log.e(TAG, "Query passed to function: " + sq);
        String apiKey = "bGsZ45bJXihI7OhZmH5E3SpnPN0WUXHx24hhAU5l";
        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key="+ apiKey +"&query="+ sq +"&dataType=Branded&requireAllWords=true";

        foodlist.clear();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest searchQuery = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                populateList(json);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "JsonObjectRequest error");
            }
        });

        queue.add(searchQuery);
    }

    private void populateList(JSONObject jsonObj) {
        if (jsonObj != null) {
            Log.e(TAG, "Response from url: " + jsonObj.toString());
            try {

                JSONArray foods = jsonObj.getJSONArray("foods");

                for (int i = 0; i < foods.length(); i++) {

                    JSONObject f = foods.getJSONObject(i);
                    String foodName = f.getString("description");
                    //String foodCategory = f.getString("foodCategory");
                    Food newFood = new Food();
                    newFood.setFoodName(foodName);

                    JSONArray nutrients = f.getJSONArray("foodNutrients");

                        for (int j = 0; j < nutrients.length(); j++) {
                            JSONObject n = nutrients.getJSONObject(j);
                            String nutrientName = n.getString("nutrientName");
                            //String unitName = n.getString("unitName");
                            //String derivationDescription = n.getString("derivationDescription");
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

                    foodlist.add(newFood);

                }

                mAdapter.notifyDataSetChanged();

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}