package pl.edu.agh.eis.wsswaml.views.restaurants.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import pl.edu.agh.eis.wsswaml.R;
import pl.edu.agh.eis.wsswaml.models.Restaurant;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Restaurant restaurant;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.restaurant_tab_text_1,
            R.string.restaurant_tab_text_2, R.string.restaurant_tab_text_3 /*,R.string.restaurant_tab_text_4 */ };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Restaurant restaurant) {
        super(fm);
        this.mContext = context;
        this.restaurant = restaurant;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1: return PlaceholderFragmentCosts.newInstance(position, restaurant);
            case 2: return PlaceholderFragmentReviews.newInstance(position, restaurant);
//            case 3: return PlaceholderFragmentMenu.newInstance(position, restaurant);
            default: return PlaceholderFragmentGeneral.newInstance(position, restaurant);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}