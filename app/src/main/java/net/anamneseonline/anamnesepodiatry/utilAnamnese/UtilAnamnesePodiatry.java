package net.anamneseonline.anamnesepodiatry.utilAnamnese;

import android.content.Context;
import android.widget.Toast;

import net.anamneseonline.anamnesepodiatry.view.MainActivity;

import java.text.DecimalFormat;

public class UtilAnamnesePodiatry {


    public static String formatDecimal(Double valor)
    {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return df.format(valor);
    }

    public static void showMensagem(Context context, String mensagem){

        if(MainActivity.notifyChk == 0) {
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
        }

    }

}