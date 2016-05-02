package com.accenture.restaurantyelp.restaurantyelp;

import com.yelp.clientlib.entities.Business;

import java.util.Comparator;

/**
 * Created by Dennis on 5/1/2016.
 */
public class BusinessComparator implements Comparator<Business> {
    public int compare(Business lhs, Business rhs) {
        return lhs.name().compareTo(rhs.name());
    }
}
