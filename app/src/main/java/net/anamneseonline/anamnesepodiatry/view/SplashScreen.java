package net.anamneseonline.anamnesepodiatry.view;

import static com.android.billingclient.api.BillingClient.SkuType.SUBS;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.controller.SDKController;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    AnamnesePodiatryController controller;

    public static final int APP_PERMISSIONS_REQUEST = 4;

    private static final int SPLASH_TIME_OUT = 2500;

    private PurchasesUpdatedListener purchasesUpdatedListener;
    private BillingClient billingClient;

    private static final String PREF_FILE= "MyPref";
    private static final String SUBSCRIBE_KEY= "subscribe";
    private static final String ITEM_SKU_SUBSCRIBE_MONTHLY = "anamnese_off_remove_ads";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        controller = new AnamnesePodiatryController(getBaseContext());

        checkPermissions();

    }

    private void startAdsBilling() {

        purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, List<Purchase> purchases) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && purchases != null) {
                    for (Purchase purchase : purchases) {
                        handlePurchase(purchase);
                    }
                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    UtilAnamnesePodiatry.showMensagem(getApplicationContext(), getString(R.string.cancelled_purchase));
                } else {
                    // Handle any other error codes.
                    UtilAnamnesePodiatry.showMensagem(getApplicationContext(), "Error: " + billingResult.getDebugMessage());
                }

            }
        };

        billingClient = BillingClient.newBuilder(getApplicationContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(getApplicationContext(),"Service Disconnected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){

                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(SUBS);
                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
                    if(queryPurchases!=null && queryPurchases.size()>0){
                        handlePurchases(queryPurchases);
                    }else{
                        saveSubscribe(false);
                    }

                }

            }
        });

    }

    private void handlePurchase(Purchase purchase) {

        AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {

                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                    //if purchase is acknowledged
                    // Grant entitlement to the user. and restart activity
                    saveSubscribe(true);
                }

            }
        };

        if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED){
            if(!purchase.isAcknowledged()){
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    saveSubscribe(true);
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);

    }

    void handlePurchases(List<Purchase>  purchases) {
        for(Purchase purchase:purchases) {
            purchase = null;
            //if item is purchased
            if(ITEM_SKU_SUBSCRIBE_MONTHLY.equals(purchase.getSkus()) && purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE)
            {
                saveSubscribe(false);
            }else{
                saveSubscribe(true);
            }
        }
    }

    private void saveSubscribe(Boolean save){

        SharedPreferences saveSubscribe = getApplicationContext().getSharedPreferences(PREF_FILE, 0);

        SharedPreferences.Editor subscribe = saveSubscribe.edit();

        subscribe.putBoolean(SUBSCRIBE_KEY, save);

        subscribe.apply();

        Toast.makeText(getApplicationContext(), save.toString(), Toast.LENGTH_SHORT).show();

    }

    private void apresentarTelaSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            Intent telaPrincipal = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(telaPrincipal);

            finish();

            }
        }, SPLASH_TIME_OUT);

    }

    private void checkPermissions() {

        startAdsBilling();

        if (Build.VERSION.SDK_INT < 23) {

            if(SDKController.verificarGooglePlayServices(SplashScreen.this)){

                apresentarTelaSplash();

            }else{

                UtilAnamnesePodiatry.showMensagem(this, getResources().getString(R.string.gps_not_conf));

            }

        } else {

            if(SDKController.verificarGooglePlayServices(SplashScreen.this)){

                if(checkAndRequestPermissions()){
                    apresentarTelaSplash();
                }

            }else{

                UtilAnamnesePodiatry.showMensagem(this, getResources().getString(R.string.gps_not_conf));

            }

        }

    }

    private boolean checkAndRequestPermissions() {

        boolean retorno = true;

        List<String> permissoesNecessarias = new ArrayList<>();

        int permissaoLerDB = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissaoEscreverDB = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissaoInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permissaoNetworkState = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int permissaoCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissaoLerDB != PackageManager.PERMISSION_GRANTED) {
            permissoesNecessarias.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissaoEscreverDB != PackageManager.PERMISSION_GRANTED) {
            permissoesNecessarias.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissaoInternet != PackageManager.PERMISSION_GRANTED) {
            permissoesNecessarias.add(Manifest.permission.INTERNET);
        }

        if (permissaoNetworkState != PackageManager.PERMISSION_GRANTED) {
            permissoesNecessarias.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (permissaoCamera != PackageManager.PERMISSION_GRANTED) {
            permissoesNecessarias.add(Manifest.permission.CAMERA);
        }

        if (!permissoesNecessarias.isEmpty()) {

            ActivityCompat.requestPermissions(this, permissoesNecessarias.toArray(new String[permissoesNecessarias.size()]), APP_PERMISSIONS_REQUEST);

            retorno = false;

        }


        return retorno;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case APP_PERMISSIONS_REQUEST: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        apresentarTelaSplash();

                    }

                } else {

                    //UtilAnamnesePodiatry.showMensagem(this, getResources().getString(R.string.some_permissions));
                    apresentarTelaSplash();

                }

                break;

            }

        }

    }
}