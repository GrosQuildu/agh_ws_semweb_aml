package pl.edu.agh.eis.wsswaml.views.restaurants.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class PlaceholderFragmentGeneral extends PlaceholderFragmentBase {
    public static PlaceholderFragmentBase newInstance(int index, Restaurant restaurant) {
        PlaceholderFragmentGeneral fragment = new PlaceholderFragmentGeneral();
        return PlaceholderFragmentBase.newInstance(fragment, index, restaurant);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurant_description_general, container, false);

        Restaurant restaurant = pageViewModel.getRestaurant();

        TextView restaurantName = root.findViewById(R.id.restaurantName);
        restaurantName.setText(restaurant.name);

        ImageView restaurantImage = root.findViewById(R.id.restaurantImage);
        if (!restaurant.photoUrl.isEmpty()) {
            Picasso.get()
                    .load(restaurant.photoUrl)
                    .placeholder(R.drawable.unknown)
                    .error(R.drawable.error)
                    .into(restaurantImage);
        } else {
            restaurantImage.setImageResource(R.drawable.unknown);
        }

        if (restaurant.url != null && !restaurant.url.isEmpty() && restaurant.url.startsWith("http"))
        restaurantImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(restaurant.url));
            startActivity(intent);
        });

        TextView restaurantAddress = root.findViewById(R.id.restaurantAddress);
        restaurantAddress.setText(restaurant.address);

        TextView restaurantCuisines = root.findViewById(R.id.restaurantCuisines);
        restaurantCuisines.setText(restaurant.cuisines);

        return root;
    }
}
