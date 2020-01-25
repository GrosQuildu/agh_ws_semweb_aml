package pl.edu.agh.eis.wsswaml.views;

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
import pl.edu.agh.eis.wsswaml.models.Cuisine;

public class CuisinesAdapter extends RecyclerView.Adapter<CuisinesAdapter.CuisinesViewHolder> {
    private List<Cuisine> cuisineList;
    private OnItemClickListener itemClickListener;

    CuisinesAdapter(List<Cuisine> cuisineList, OnItemClickListener itemClickListener) {
        this.cuisineList = cuisineList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public CuisinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View CuisinesViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_card, parent, false);
        return new CuisinesViewHolder(CuisinesViewHolder);
    }

    @Override
    public void onBindViewHolder(CuisinesViewHolder holder, final int position) {
        Cuisine cuisine = cuisineList.get(position);
        Picasso.get().load(cuisine.getImage()).into(holder.imageProductImage);
        holder.txtProductName.setText(cuisine.getName());
        holder.bind(cuisine, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return cuisineList.size();
    }

    class CuisinesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProductImage;
        TextView txtProductName;

        CuisinesViewHolder(View view) {
            super(view);
            imageProductImage = view.findViewById(R.id.idProductImage);
            txtProductName = view.findViewById(R.id.idProductName);
        }

        void bind(Cuisine cuisine, OnItemClickListener itemClickListener) {
            itemView.setOnClickListener(view -> itemClickListener.onItemClicked(view, cuisine));
        }
    }
}

interface OnItemClickListener {
    void onItemClicked(View view, Cuisine cuisine);
}