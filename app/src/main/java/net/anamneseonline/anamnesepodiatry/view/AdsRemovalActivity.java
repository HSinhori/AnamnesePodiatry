package net.anamneseonline.anamnesepodiatry.view;

import static com.android.billingclient.api.BillingClient.SkuType.SUBS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.ArrayList;
import java.util.List;

public class AdsRemovalActivity extends AppCompatActivity {

    BillingClient billingClient;
    PurchasesUpdatedListener purchasesUpdatedListener;

    private static final String PREF_FILE= "MyPref";
    private static final String SUBSCRIBE_KEY= "subscribe";
    private static final String ITEM_SKU_SUBSCRIBE_MONTHLY = "anamnese_off_remove_ads";

    private Button btn_remove_ads;
    private TextView item_status;

    private Boolean savedSubscribe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_removal);

        btn_remove_ads = findViewById(R.id.btn_remove_ads);
        item_status = findViewById(R.id.item_status);

        btn_remove_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startBillingConnection();

            }
        });

        checkAdsRemoval();

        startAdsBilling();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAdsRemoval();
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
                    UtilAnamnesePodiatry.showMensagem(AdsRemovalActivity.this, getString(R.string.cancelled_purchase));
                } else {
                    // Handle any other error codes.
                    UtilAnamnesePodiatry.showMensagem(AdsRemovalActivity.this, "Error: " + billingResult.getDebugMessage());
                }

            }
        };

        billingClient = BillingClient.newBuilder(AdsRemovalActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(AdsRemovalActivity.this,"Service Disconnected",Toast.LENGTH_SHORT).show();
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

    private void checkAdsRemoval() {

        if(getSubscribe()){
            btn_remove_ads.setVisibility(View.GONE);
            item_status.setText(getString(R.string.thx_subs));
        }else{
            btn_remove_ads.setVisibility(View.VISIBLE);
            item_status.setText(getString(R.string.remove_ads_message));
        }

    }

    private void startBillingConnection(){

        List<String> skuList = new ArrayList<>();
        skuList.add(ITEM_SKU_SUBSCRIBE_MONTHLY);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(
                            @NonNull BillingResult billingResult,
                            @Nullable List<SkuDetails> list) {

                        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                .setSkuDetails(list.get(0))
                                .build();
                        billingClient.launchBillingFlow(AdsRemovalActivity.this, billingFlowParams);

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
            //if item is purchased
            if(ITEM_SKU_SUBSCRIBE_MONTHLY.equals(purchase.getSkus()) &&
                    (purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE ||
                            purchase.getPurchaseState() == Purchase.PurchaseState.PENDING))
            {
                saveSubscribe(false);
                btn_remove_ads.setVisibility(View.VISIBLE);
                item_status.setText(getString(R.string.remove_ads_message));
                Toast.makeText(this, "Purchase Status Unknown", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveSubscribe(Boolean save){

        SharedPreferences saveSubscribe = this.getSharedPreferences(PREF_FILE, 0);

        SharedPreferences.Editor subscribe = saveSubscribe.edit();

        subscribe.putBoolean(SUBSCRIBE_KEY, save);

        subscribe.apply();

    }

    private Boolean getSubscribe(){

        SharedPreferences getSubscribeStatus = this.getSharedPreferences(PREF_FILE, 0);

        savedSubscribe = getSubscribeStatus.getBoolean(SUBSCRIBE_KEY, false);

        return savedSubscribe;

    }
}