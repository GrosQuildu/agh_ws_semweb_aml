package pl.edu.agh.eis.wsswaml.models;

import android.location.Location;

import java.io.Serializable;

public class Restaurant implements Serializable {
    public String id;
    public String name;
    public String url;
    public String address;
    public String cuisines;
    public String timings;
    public String photoUrl;
    public String photos;
    public Location location;
}
