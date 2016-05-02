package com.accenture.restaurantyelp.restaurantyelp;

import android.app.Activity;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.Review;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantListActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Business mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int pos = getArguments().getInt(ARG_ITEM_ID, 3);
            mItem = YelpRestaurants.getRetrievedRestaurant(pos);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            String address = YelpRestaurants.getRestaurantDisplayAddress(mItem);
            String snippet = mItem.snippetText();
            String imageUrl = mItem.imageUrl();
            ImageView imgView = (ImageView) rootView.findViewById(R.id.restaurant_image);

            Log.i("RestaurantYelp", "Review: " + snippet + ";, URL=" + imageUrl);
            ((TextView) rootView.findViewById(R.id.restaurant_address)).setText(address);
            ((TextView) rootView.findViewById(R.id.restaurant_review)).setText(snippet);

            new ImageLoaderTask(imageUrl, imgView).execute();
        }

        return rootView;
    }
}
