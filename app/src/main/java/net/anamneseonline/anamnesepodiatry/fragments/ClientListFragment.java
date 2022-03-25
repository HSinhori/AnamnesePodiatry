package net.anamneseonline.anamnesepodiatry.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.adapter.AnamnesePodiatryAdapter;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.ArrayList;

public class ClientListFragment extends Fragment {

    public static int idSaved;
    ArrayList<AnamnesePodiatry> dataSet;

    ListView listView;

    AnamnesePodiatryController controller;

    AnamnesePodiatryAdapter adapter;

    View view;

    FloatingActionButton fab;

    FragmentManager fragmentManager;

    AlertDialog.Builder builder;
    AlertDialog alert;
    Dialog dialog_img;

    Button btn_delete, btn_access;

    AnamnesePodiatry anamnese_podiatry;

    private int img_rotate_costumer = 0;

    private String nameSaved;

    ProgressDialog progressDialog;

    private InterstitialAd mInterstitialAd, mInterstitialAd_details;

    private Boolean savedSubscribe = false;
    private static final String PREF_FILE= "MyPref";
    private static final String SUBSCRIBE_KEY= "subscribe";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(requireContext());

        dialog_img = new Dialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_client_list, container, false);

        controller = new AnamnesePodiatryController(getContext());

        requireActivity().setTitle(R.string.menu_client_list);

        fragmentManager = getFragmentManager();

        MobileAds.initialize(requireContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdView adView = (AdView) view.findViewById(R.id.adViewClientList);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        InterstitialAd.load(requireContext(), "ca-app-pub-5981899111891359/1436280145", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        mInterstitialAd = interstitialAd;
                    }
                });

        InterstitialAd.load(requireContext(), "ca-app-pub-5981899111891359/4412735180", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mInterstitialAd_details = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd_details) {
                        super.onAdLoaded(interstitialAd_details);
                        mInterstitialAd_details = interstitialAd_details;
                    }
                });

        fab = view.findViewById(R.id.fabNewClient);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mInterstitialAd != null) {

                    if(!getSubscribe()) {
                        mInterstitialAd.show(requireActivity());
                    }

                }

                fragmentManager.beginTransaction().replace(R.id.content_fragment, new NewClientFragment()).commit();

            }
        });

        listView = view.findViewById(R.id.listview);

        dataSet = controller.list();

        adapter = new AnamnesePodiatryAdapter(dataSet, getContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    anamnese_podiatry = dataSet.get(position);

                    idSaved = anamnese_podiatry.getId();

                    nameSaved = anamnese_podiatry.getName();

                    img_rotate_costumer = anamnese_podiatry.getImagerotatecostumer();

                    dialog_img.setContentView(R.layout.client_images_list);

                    ImageView image = dialog_img.findViewById(R.id.imageView_list_patient);
                    TextView txt = dialog_img.findViewById(R.id.txt_dialog_list);

                    btn_delete = dialog_img.findViewById(R.id.btn_delete_img_dialog_list);
                    btn_delete.setText(R.string.delete_patient);
                    btn_delete.setBackgroundColor(Color.WHITE);
                    btn_delete.setTextColor(Color.RED);

                    btn_access = dialog_img.findViewById(R.id.btn_access_patient_dialog_list);
                    btn_access.setVisibility(View.VISIBLE);
                    btn_access.setText(R.string.access_patient);
                    btn_access.setBackgroundColor(Color.WHITE);
                    btn_access.setTextColor(getResources().getColor(R.color.colorPrimary));

                    txt.setText(nameSaved);

                    if(anamnese_podiatry.getImgprofilecustomer() != null) {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(anamnese_podiatry.getImgprofilecustomer(), 0,
                                anamnese_podiatry.getImgprofilecustomer().length);
                        image.setImageBitmap(bitmap);
                        image.setRotation(img_rotate_costumer*90);

                    }else{

                        image.setImageDrawable(getResources().getDrawable(R.drawable.profile));

                    }

                    btn_access.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressDialog = new ProgressDialog(getContext());

                            progressDialog.setTitle(getResources().getString(R.string.pacient_details));
                            progressDialog.setMessage(getResources().getString(R.string.loading_info) + nameSaved);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            saveIdSharedPref();

                            dialog_img.dismiss();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            progressDialog.dismiss();

                                        }
                                    }, 500);

                                    if(mInterstitialAd_details != null) {

                                        if(!getSubscribe()) {
                                            mInterstitialAd_details.show(requireActivity());
                                        }
                                    }

                                    fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClientDetails()).commit();

                                }
                            }, 1000);

                        }
                    });

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            builder = new AlertDialog.Builder(requireContext());
                            builder.setTitle(R.string.txt_delete_customer);
                            builder.setMessage(R.string.delete_record);
                            builder.setCancelable(true);
                            builder.setIcon(R.mipmap.ic_launcher);

                            builder.setPositiveButton(R.string.yes, new Dialog.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    try {

                                        if(controller.deleteAllImages(anamnese_podiatry)){

                                            UtilAnamnesePodiatry.showMensagem(getContext(), getResources().getString(R.string.deleted_images));

                                        }
                                        if(controller.deleteClient(anamnese_podiatry)){

                                            UtilAnamnesePodiatry.showMensagem(getContext(), getResources().getString(R.string.deleted_patient));

                                        }

                                    }catch (Exception e){

                                        Log.e("Adapter", "Erro: " + e.getMessage());

                                    }

                                    adapter.notifyDataSetChanged();

                                    atualizarLista(controller.getAllClients());
                                    dialog_img.dismiss();

                                }
                            });

                            builder.setNegativeButton(R.string.cancel, new Dialog.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    dialog.cancel();

                                }
                            });

                            alert = builder.create();
                            alert.show();

                        }
                    });

                    dialog_img.show();

                }catch (Exception e){

                    UtilAnamnesePodiatry.showMensagem(getContext(), getResources().getString(R.string.cant_load) );

                }

            }
        });

        return view;
    }

    public void atualizarLista(ArrayList<AnamnesePodiatry> novosDados){

        this.dataSet.clear();
        this.dataSet.addAll(novosDados);
        adapter.notifyDataSetChanged();

    }

    private void saveIdSharedPref(){

        SharedPreferences savedIdPref = getActivity().getSharedPreferences("savedId", 0);
        SharedPreferences.Editor savedIdPrefer = savedIdPref.edit();
        savedIdPrefer.putInt("savedId", idSaved);
        savedIdPrefer.commit();

    }

    private Boolean getSubscribe(){

        SharedPreferences getSubscribeStatus = getActivity().getSharedPreferences(PREF_FILE, 0);

        savedSubscribe = getSubscribeStatus.getBoolean(SUBSCRIBE_KEY, false);

        return savedSubscribe;

    }
}
