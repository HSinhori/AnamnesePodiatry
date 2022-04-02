package net.anamneseonline.anamnesepodiatry.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.anamneseonline.anamnesepodiatry.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BackUpActivity extends AppCompatActivity {

    private static String DB_NAME = "anamnese_podiatry.sqlite";
    private Context context;
    private TextView txt_saved_backup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up);

        context = getApplicationContext();
        txt_saved_backup = findViewById(R.id.txt_saved_backup);

        Button btn_create_backup = findViewById(R.id.btn_create_backup);
        btn_create_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_backup();
            }
        });

        Button btn_restore_backup = findViewById(R.id.btn_restore_backup);
        btn_restore_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restore_backup();
            }
        });
        
    }

    private void restore_backup() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/sqlite");
        String[] mimeTypes = {"application/octet-stream"};

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, 1);

    }

    private void create_backup() {

        try {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

                Calendar calendar = Calendar.getInstance();
                String time = new SimpleDateFormat("MM-dd-yyyy/HH:mm:ss").format(calendar.getTime());

                String backup_folder = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS) + "/Anamnesis/backups/";

                File backup_folder_path = new File(backup_folder, time);

                if(!backup_folder_path.exists()){
                    if(!backup_folder_path.mkdirs()){
                        Toast.makeText(context, "nao criou", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "criou", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "pasta exist", Toast.LENGTH_SHORT).show();
                }

                InputStream inputStream = new FileInputStream(
                        new File(Environment.getDataDirectory()
                                + "/data/" + getPackageName() + "/databases/" + DB_NAME));

                OutputStream outputStream = new FileOutputStream(new File(
                        backup_folder_path + "/" + DB_NAME));

                byte[] buffer = new byte[1024];
                int comprimento;

                while((comprimento = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, comprimento);
                }
                inputStream.close();
                outputStream.close();

                txt_saved_backup.setText(backup_folder_path.toString());

            }else{

                Calendar calendar = Calendar.getInstance();
                String time = new SimpleDateFormat("MM-dd-yyyy/HH:mm:ss").format(calendar.getTime());

                String backup_folder = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS) + "/Anamnesis/backups/";

                File backup_folder_path = new File(backup_folder, time);

                if(!backup_folder_path.exists()){
                    if(!backup_folder_path.mkdirs()){
                        Toast.makeText(context, "nao criou", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "criou", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "pasta exist", Toast.LENGTH_SHORT).show();
                }

                InputStream inputStream = new FileInputStream(
                        new File(Environment.getDataDirectory()
                                + "/data/" + getPackageName() + "/databases/" + DB_NAME));

                OutputStream outputStream = new FileOutputStream(new File(
                        backup_folder_path + "/" + DB_NAME));

                byte[] buffer = new byte[1024];
                int comprimento;

                while((comprimento = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, comprimento);
                }
                inputStream.close();
                outputStream.close();

                txt_saved_backup.setText(backup_folder_path.toString());

            }


        } catch (IOException e) {
            Toast.makeText(context, "IO "+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("TAG", e.getMessage());
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode,resultCode,resultData);



        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == 1) {

            try {

                ProgressDialog progressDialog = new ProgressDialog(BackUpActivity.this);
                progressDialog.setTitle("Restaurando");
                progressDialog.show();

                Uri uri = resultData.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);

                OutputStream outputStream = new FileOutputStream(
                        new File(Environment.getDataDirectory()
                                + "/data/" + getPackageName() + "/databases/" + DB_NAME));
                byte[] buffer = new byte[1024];

                int comprimento;

                while((comprimento = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, comprimento);
                }

                inputStream.close();
                outputStream.close();

                progressDialog.dismiss();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestCode == 17){

            Uri uri = null;
            if(resultData != null){
                uri = resultData.getData();

                /*FileOutputStream fileOutputStream = new FileOutputStream(

                );*/

            }

        }

    }
}