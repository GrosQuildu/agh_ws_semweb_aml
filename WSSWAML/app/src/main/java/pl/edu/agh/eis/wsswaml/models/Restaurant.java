package pl.edu.agh.eis.wsswaml.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


public class Restaurant implements Parcelable {
    public String id;
    public String name;
    public String url;
    public String address;
    public String cuisines;
    public String timings;
    public String photoUrl;
    public String photos;
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
