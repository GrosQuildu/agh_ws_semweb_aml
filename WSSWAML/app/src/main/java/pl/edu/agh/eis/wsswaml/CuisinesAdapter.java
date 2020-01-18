package pl.edu.agh.eis.wsswaml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pl.edu.agh.eis.wsswaml.models.Cuisine;

public class CuisinesAdapter extends RecyclerView.Adapter<CuisinesAdapter.CuisinesViewHolder> {
    private List<Cuisine> cuisineList;
    private Context context;

    public CuisinesAdapter(List<Cuisine> cuisineList, Context context) {
        this.cuisineList = cuisineList;
        this.context = context;
    }

    @Override
    @NonNull
    public CuisinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View CuisinesViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_card, parent, false);
        return new CuisinesViewHolder(CuisinesViewHolder);
    }

    @Override
    public void onBindViewHolder(CuisinesViewHolder holder, final int position) {
        Picasso.get().load(cuisineList.get(position).getImage()).into(holder.imageProductImage);
        holder.txtProductName.setText(cuisineList.get(position).getName());

        holder.imageProductImage.setOnClickListener(v -> {
            String productName = cuisineList.get(position).getName();
            Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
        });
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
    }
}
