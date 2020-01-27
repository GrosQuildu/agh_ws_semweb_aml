package pl.edu.agh.eis.wsswaml.views.restaurants.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class PlaceholderFragmentCosts extends PlaceholderFragmentBase {
    public static PlaceholderFragmentBase newInstance(int index, Restaurant restaurant) {
        PlaceholderFragmentCosts fragment = new PlaceholderFragmentCosts();
        return PlaceholderFragmentBase.newInstance(fragment, index, restaurant);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_description_costs, container, false);

        Restaurant restaurant = pageViewModel.getRestaurant();
        TextView textView = root.findViewById(R.id.restaurantAverageCost);
        textView.setText(Integer.toString(restaurant.averageCost));

        return root;
    }
}
