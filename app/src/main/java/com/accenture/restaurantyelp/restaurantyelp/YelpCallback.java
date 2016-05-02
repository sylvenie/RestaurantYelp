package com.accenture.restaurantyelp.restaurantyelp;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;

/**
 * Created by Dennis on 4/30/2016.
 */
public interface YelpCallback {
    void updateRestaurants(ArrayList<Business> businesses);
}
