package pl.edu.agh.eis.wsswaml.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Restaurant implements Parcelable {
    // tab 1
    public String id;
    public String name;
    public String url;
    public String address;
    public String cuisines;
    public String timings;
    public String photoUrl;
    public String photos;

    // tab 2
    public int averageCost;
    public int priceRange;

    // tab 3
    public double rating;
    public String ratingText;
    public List<String> reviews;

    // tab 4
    public List<String> menu;

    // for localization and sparql
    public Location location;

    public Restaurant() {

    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        name = in.readString();
        url = in.readString();
        address = in.readString();
        cuisines = in.readString();
        timings = in.readString();
        photoUrl = in.readString();
        photos = in.readString();
        averageCost = in.readInt();
        priceRange = in.readInt();
        rating = in.readDouble();
        ratingText = in.readString();
        reviews = in.createStringArrayList();
        menu = in.createStringArrayList();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(address);
        dest.writeString(cuisines);
        dest.writeString(timings);
        dest.writeString(photoUrl);
        dest.writeString(photos);
        dest.writeInt(averageCost);
        dest.writeInt(priceRange);
        dest.writeDouble(rating);
        dest.writeString(ratingText);
        dest.writeStringList(reviews);
        dest.writeStringList(menu);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
