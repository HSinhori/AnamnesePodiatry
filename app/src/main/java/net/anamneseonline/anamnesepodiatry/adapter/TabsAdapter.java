package net.anamneseonline.anamnesepodiatry.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.anamneseonline.anamnesepodiatry.fragments.Annotations;
import net.anamneseonline.anamnesepodiatry.fragments.ClinicalData;
import net.anamneseonline.anamnesepodiatry.fragments.CustomerProfile;
import net.anamneseonline.anamnesepodiatry.fragments.Feet;
import net.anamneseonline.anamnesepodiatry.fragments.Hands;
import net.anamneseonline.anamnesepodiatry.fragments.ImagesFragment;
import net.anamneseonline.anamnesepodiatry.fragments.NailShape;
import net.anamneseonline.anamnesepodiatry.fragments.Perfusion;

public class TabsAdapter extends FragmentStatePagerAdapter {

    Context c;
    private String[] tabNames;

    Fragment select = null;

    public TabsAdapter(FragmentManager fm, Context context) {
        super(fm);

        c = context;

        tabNames = new String[]{"", "", "", "", "", "", "", ""};

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                select = new CustomerProfile();
                break;
            case 1:
                select = new ClinicalData();
                break;
            case 2:
                select = new NailShape();
                break;
            case 3:
                select = new Hands();
                break;
            case 4:
                select = new Feet();
                break;
            case 5:
                select = new Perfusion();
                break;
            case 6:
                select = new Annotations();
                break;
            case 7:
                select = new ImagesFragment();
                break;
        }
        return select;
    }

    @Override
    public int getCount() {

        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabNames[position];

    }
}
