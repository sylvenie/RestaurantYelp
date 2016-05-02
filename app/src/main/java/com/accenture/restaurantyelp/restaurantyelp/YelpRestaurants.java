package com.accenture.restaurantyelp.restaurantyelp;

import android.support.annotation.Nullable;
import android.util.Log;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dennis on 4/30/2016.
 */
public class YelpRestaurants {
    static private ArrayList<Business> businesses = null;

    @Nullable
    static public Business getRetrievedRestaurant(int position) {
        if ((businesses != null) && (businesses.size() > position)) {
            return businesses.get(position);
        }
        return null;
    }

    static public String getRestaurantDisplayAddress(Business business) {
        ArrayList<String> addressArray = business.location().displayAddress();
        String addressString = "";

        if (addressArray.size() != 0) {
            addressString = addressArray.get(0);
            if (addressArray.size() > 1) {
                addressString += ", " + addressArray.get(1);
            }
        }

        return addressString;
    }

    static void getRestaurants(String searchCategory, final YelpCallback cb) {
        YelpAPIFactory apiFactory = new YelpAPIFactory("JX9ssbKx7bc5OGZJJ9MOjg",
                "DGKdemp6FWOXUxslhPVLh5XKy0U", "8-BotMSptPjBA0dgXYsT8-XdkJAAMvPl",
                "CeS7Vx05b6CH6xvQ1jTXvAOTrsY");
        YelpAPI yelpAPI = apiFactory.createAPI();

        if (cb == null) {
            return;
        }

        Map<String, String> params = new HashMap<>();

        // locale params
        params.put("lang", "en");

        // general params
        params.put("term", "restaurants");
        params.put("limit", "10");

        if ((searchCategory != null) && (searchCategory.length() != 0)) {
            // Yelp service seems to prefer lower case categories
            params.put("category_filter", searchCategory.toLowerCase());
        }

        Call<SearchResponse> call = yelpAPI.search("Toronto, Canada", params);

        Callback<SearchResponse> callback = new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                if (searchResponse != null) {
                    int totalNumberOfResult = searchResponse.total();  // 3

                    businesses = searchResponse.businesses();
                    Collections.sort(businesses, new BusinessComparator());
                    cb.updateRestaurants(businesses);
                } else {
                    // send empty list
                    cb.updateRestaurants(new ArrayList<Business>());
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // HTTP error happened
                Log.d("RestaurantListActivity", "Failed to execute search: " + t.toString());
                // send empty list
                cb.updateRestaurants(new ArrayList<Business>());
            }
        };

        call.enqueue(callback);
    }
}
