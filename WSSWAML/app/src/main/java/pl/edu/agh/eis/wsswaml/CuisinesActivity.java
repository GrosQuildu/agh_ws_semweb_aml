package pl.edu.agh.eis.wsswaml;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.eis.wsswaml.models.Cuisine;

public class CuisinesActivity extends AppCompatActivity {
    private RecyclerView cuisinesRecyclerView;
    private CuisinesAdapter mAdapter;
    private List<Cuisine> mCuisinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView cuisinesRecyclerView = findViewById(R.id.cuisines_recycler_view);
        //cuisinesRecyclerView.setHasFixedSize(true);
        cuisinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCuisinesList = new ArrayList<>();
        mCuisinesList.add(new Cuisine("Mango", "https://upload.wikimedia.org/wikipedia/commons/8/8b/Food_in_Israel.jpg"));
        mCuisinesList.add(new Cuisine("Pineapple", "https://upload.wikimedia.org/wikipedia/commons/9/99/Ph%E1%BB%9F_b%C3%B2%2C_C%E1%BA%A7u_Gi%E1%BA%A5y%2C_H%C3%A0_N%E1%BB%99i.jpg"));

        mAdapter = new CuisinesAdapter(mCuisinesList,this);
        cuisinesRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
