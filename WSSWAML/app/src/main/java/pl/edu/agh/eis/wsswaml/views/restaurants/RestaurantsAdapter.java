package pl.edu.agh.eis.wsswaml.views.restaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;


public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {
    private List<Restaurant> restaurantList;
    private OnItemClickListener itemClickListener;

    RestaurantsAdapter(List<Restaurant> restaurantList, OnItemClickListener itemClickListener) {
        this.restaurantList = restaurantList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public RestaurantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View restaurantsViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_restaurant, parent, false);
        return new RestaurantsViewHolder(restaurantsViewHolder);
    }

    @Override
    public void onBindViewHolder(RestaurantsViewHolder holder, final int position) {
        Restaurant restaurant = restaurantList.get(position);
        if (!restaurant.photoUrl.isEmpty()) {
            Picasso.get()
                    .load(restaurant.photoUrl)
                    .placeholder(R.drawable.unknown)
                    .error(R.drawable.error)
                    .into(holder.imageProductImage);
        } else {
            holder.imageProductImage.setImageResource(R.drawable.unknown);
        }
        holder.txtProductName.setText(restaurant.name);
        holder.bind(restaurant, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class RestaurantsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProductImage;
        TextView txtProductName;

        RestaurantsViewHolder(View view) {
            super(view);
            imageProductImage = view.findViewById(R.id.idRestaurantImage);
            txtProductName = view.findViewById(R.id.idRestaurantName);
        }

        void bind(Restaurant restaurant, OnItemClickListener itemClickListener) {
            itemView.setOnClickListener(view -> itemClickListener.onItemClicked(view, restaurant));
        }
    }
}

interface OnItemClickListener {
    void onItemClicked(View view, Restaurant restaurant);
}