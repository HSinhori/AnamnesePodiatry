package net.anamneseonline.anamnesepodiatry.view;

import android.app.Dialog;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.fragments.ClientListFragment;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    private boolean allowExit = false;
    AnamnesePodiatryController controller;
    Context context;
    TextView username_drawer;

    private int savedAdsNews = 0;
    private int savedRateUs = 0;
    public static int notifyChk = 0;

    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getBaseContext();

        controller = new AnamnesePodiatryController(context);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        username_drawer = header.findViewById(R.id.username_drawer);
        backupDrive();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClientListFragment()).commit();

        builder = new AlertDialog.Builder(this);

        readRateUs();
        readAdsNews();
        notifyRead();

        if(savedRateUs == 10 || savedRateUs == 30 || savedRateUs == 50) {
            rateUs();
        }

        if(savedAdsNews == 0 || savedAdsNews == 4) {
            adsNews();
        }

        saveRateUs();
        saveAdsNews();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        }else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();

        }else{

            if (allowExit) {

                super.onBackPressed();

            }else{

                fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClientListFragment()).commit();

                UtilAnamnesePodiatry.showMensagem(context, getString(R.string.app_exit_message));


                allowExit = true;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        allowExit = false;

                    }

                }, 1500);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_notify_off) {
            if (item.isChecked()) {

                item.setChecked(false);
                notifyChk = 0;

            } else {

                item.setChecked(true);
                notifyChk = 1;

            }
            notifySave();
        }else if(item.getItemId() == R.id.remove_ads){

            Intent telaPrincipal = new Intent(MainActivity.this, AdsRemovalActivity.class);
            startActivity(telaPrincipal);

        }/*else if(item.getItemId() == R.id.backup){

            Intent telaPrincipal = new Intent(MainActivity.this, BackUpActivity.class);
            startActivity(telaPrincipal);

        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(notifyChk == 0) {
            menu.findItem(R.id.action_notify_off).setChecked(false);
        }else{
            menu.findItem(R.id.action_notify_off).setChecked(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_client_list) {

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClientListFragment()).commit();

        } else if (id == R.id.nav_share) {

            try {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage= getString(R.string.app_name) + "\n\n" + getString(R.string.recommend) + "\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=net.anamneseonline.anamnesepodiatry";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, getString(R.string.choose)));

            } catch(Exception e) {

                e.toString();

            }

        } else if (id == R.id.nav_logout) {

            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void backupDrive() {

        BackupManager backupManager = new BackupManager(this);
        backupManager.dataChanged();

    }

    private void rateUs(){

        try {

            builder.setTitle(getString(R.string.rate_us) + " " + getString(R.string.app_name));
            builder.setMessage(getString(R.string.liking) + " " + getString(R.string.app_name) + "?");
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.ic_launcher);

            builder.setPositiveButton(R.string.yes, new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.anamneseonline.anamnesepodiatry")));

                    } catch (Exception e) {

                        Log.e("Adapter", "Erro: " + e.getMessage());

                    }

                }
            });

            builder.setNegativeButton(R.string.cancel, new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            alert = builder.create();
            alert.show();

        }catch (Exception e){

            e.toString();
        }
    }

    private void saveRateUs(){

        SharedPreferences saveRateUs = this.getSharedPreferences("savedRateUs", 0);

        SharedPreferences.Editor rateUs = saveRateUs.edit();

        savedRateUs = savedRateUs + 1;

        rateUs.putInt("savedRateUs", savedRateUs);

        rateUs.commit();

    }

    private void readRateUs(){

        SharedPreferences saveRateUs = this.getSharedPreferences("savedRateUs", 0);

        savedRateUs = saveRateUs.getInt("savedRateUs", 0);

    }

    private void notifySave(){

        SharedPreferences saveNotify = this.getSharedPreferences("notifyChk", 0);

        SharedPreferences.Editor notifySvr = saveNotify.edit();

        notifySvr.putInt("notifyChk", notifyChk);

        notifySvr.commit();

    }

    private void notifyRead(){

        SharedPreferences notifyChkr = this.getSharedPreferences("notifyChk", 0);

        notifyChk = notifyChkr.getInt("notifyChk", 0);

    }

    private void saveAdsNews(){

        SharedPreferences saveAdsNews = this.getSharedPreferences("saveAdsNews", 0);

        SharedPreferences.Editor adsNews = saveAdsNews.edit();

        savedAdsNews = savedAdsNews + 1;

        adsNews.putInt("saveAdsNews", savedAdsNews);

        adsNews.commit();

    }

    private void readAdsNews(){

        SharedPreferences adsNews = this.getSharedPreferences("saveAdsNews", 0);

        savedAdsNews = adsNews.getInt("saveAdsNews", 0);

    }

    private void adsNews(){

        try {

            builder.setTitle(getString(R.string.ads_removal));
            builder.setMessage(getString(R.string.see_ads_removal));
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.ic_launcher);

            builder.setPositiveButton(R.string.close, new Dialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            alert = builder.create();
            alert.show();

        }catch (Exception e){

            e.getMessage();
        }
    }

}
