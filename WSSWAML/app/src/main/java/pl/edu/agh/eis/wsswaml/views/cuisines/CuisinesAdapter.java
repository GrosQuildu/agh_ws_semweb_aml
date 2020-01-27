package pl.edu.agh.eis.wsswaml.views.cuisines;

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
    private static final String TAG = "CuisinesAdapter";
    private List<Cuisine> cuisineList;
    private OnItemClickListener itemClickListener;

    CuisinesAdapter(List<Cuisine> cuisineList, OnItemClickListener itemClickListener) {
        this.cuisineList = cuisineList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public CuisinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cuisinesViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cuisine, parent, false);
        return new CuisinesViewHolder(cuisinesViewHolder);
    }

    @Override
    public void onBindViewHolder(CuisinesViewHolder holder, final int position) {
        Cuisine cuisine = cuisineList.get(position);
        if (!cuisine.getImage().isEmpty()) {
            Picasso.get()
                    .load(cuisine.getImage())
                    .placeholder(R.drawable.unknown)
                    .error(R.drawable.error)
                    .into(holder.imageProductImage);
        } else {
            holder.imageProductImage.setImageResource(R.drawable.unknown);
        }
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