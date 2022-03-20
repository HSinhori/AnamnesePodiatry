package net.anamneseonline.anamnesepodiatry.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.adapter.TabsAdapter;

public class ClientDetails extends Fragment {

    View view;

    private AdView adView;

    public static int idSaved;

    private int[] icons = new int[]{R.drawable.profile, R.drawable.cdata, R.drawable.nshape,
                                        R.drawable.hand_icon, R.drawable.feet, R.drawable.perfusion,
                                        R.drawable.annotation, R.drawable.ic_menu_camera};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_client_details, container, false);

        getActivity().setTitle(R.string.client_details);

        readIdSharedPref();

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager(), getContext());
        viewPager.setAdapter(tabsAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0; i < tabLayout.getTabCount(); i++){
            if(tabLayout.getTabAt(i) != null){
                tabLayout.getTabAt(i).setIcon(icons[i]);
            }
        }

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        adView = (AdView) view.findViewById(R.id.adViewClientDetails);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return view;
    }

    private void readIdSharedPref(){

        SharedPreferences savedIdPref = getActivity().getSharedPreferences("savedId", 0);
        idSaved = savedIdPref.getInt("savedId", 0);

    }
}
