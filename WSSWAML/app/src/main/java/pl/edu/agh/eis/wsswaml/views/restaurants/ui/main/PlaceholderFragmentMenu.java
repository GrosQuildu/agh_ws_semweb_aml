package pl.edu.agh.eis.wsswaml.views.restaurants.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;

public class PlaceholderFragmentMenu extends PlaceholderFragmentBase implements OnItemClickListener {
    private RestaurantMenuAdapter restaurantMenuAdapter;

    public static PlaceholderFragmentBase newInstance(int index, Restaurant restaurant) {
        PlaceholderFragmentMenu fragment = new PlaceholderFragmentMenu();
        return PlaceholderFragmentBase.newInstance(fragment, index, restaurant);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_restaurant_description_menu, container, false);

        Restaurant restaurant = pageViewModel.getRestaurant();
        if (restaurant.menu == null) {
            restaurant.menu = new ArrayList<>();
            restaurant.menu.add("Example menu entry");
            restaurant.menu.add("Another menu entry");
        }

        RecyclerView recyclerView = root.findViewById(R.id.restaurantsMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        restaurantMenuAdapter = new RestaurantMenuAdapter(restaurant.menu, this);
        recyclerView.setAdapter(restaurantMenuAdapter);

        return root;
    }

    @Override
    public void onItemClicked(View view, String menuItem) {
        // pass
    }
}

class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {
    private static final String TAG = "RestaurantMenuAdapter";
    private List<String> menuDishes;
    private OnItemClickListener itemClickListener;

    RestaurantMenuAdapter(List<String> menuDishes, OnItemClickListener itemClickListener) {
        this.menuDishes = menuDishes;
        this.itemClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public RestaurantMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View restaurantMenuViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_restaurant_menu, parent, false);
        return new RestaurantMenuViewHolder(restaurantMenuViewHolder);
    }

    @Override
    public void onBindViewHolder(RestaurantMenuViewHolder holder, final int position) {
        String menuItem = menuDishes.get(position);
        holder.txtProductName.setText(menuItem);
        holder.bind(menuItem, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return this.menuDishes.size();
    }

    class RestaurantMenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;

        RestaurantMenuViewHolder(View view) {
            super(view);
            txtProductName = view.findViewById(R.id.restaurantMenuCard);
        }

        void bind(String menuItem, OnItemClickListener itemClickListener) {
            itemView.setOnClickListener(view -> itemClickListener.onItemClicked(view, menuItem));
        }
    }
}

interface OnItemClickListener {
    void onItemClicked(View view, String menuItem);
}
