package net.anamneseonline.anamnesepodiatry.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        controller = new AnamnesePodiatryController(getBaseContext());

        checkPermissions();

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

                    UtilAnamnesePodiatry.showMensagem(this, getResources().getString(R.string.some_permissions));
                    apresentarTelaSplash();

                }

                break;

            }

        }

    }

    /*private void getUsers() {

        List<AnamnesePodiatry> UserList = controller.Users();

        for (AnamnesePodiatry anamnesePodiatry : UserList) {

            if(DataSource.userSC == 1){

                useridSC = 1;

            }else{

                useridSC = 0;

            }

        }
    }*/
}