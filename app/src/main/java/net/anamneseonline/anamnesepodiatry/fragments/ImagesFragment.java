package net.anamneseonline.anamnesepodiatry.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.adapter.GridAdapter;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class ImagesFragment extends Fragment {

    View view;

    private GridView grid_patient_images;
    private FloatingActionButton fabAddImages, fabSaveImages;
    private ImageView img_pacient_img, turn_images;
    private String dataAtual = "00/00/0000", txt_image, currentPhotoPath;
    public static int id_patient_image;
    private Button btn_delete;
    private Dialog dialog_img;

    AnamnesePodiatryController controller;
    AnamnesePodiatry anamnesePodiatry;
    GridAdapter gridAdapter = null;
    ArrayList<AnamnesePodiatry> list;
    AlertDialog.Builder builder;
    AlertDialog alert_img;

    Context context;

    private byte[] byteArrayImage;

    private static final int GALLERY_REQUEST = 100;
    private static final int CAMERA_REQUEST = 200;
    private static final int CAMERA_REQUEST_CODE = 300;
    private Uri photoURI;

    private int img_rotate = 0;
    private float rotation = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        controller = new AnamnesePodiatryController(context);

        anamnesePodiatry = new AnamnesePodiatry();

        builder = new AlertDialog.Builder(context);

        dialog_img = new Dialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_images, container, false);

        grid_patient_images = view.findViewById(R.id.grid_patient_images);
        fabAddImages = view.findViewById(R.id.fabAddImages);
        fabSaveImages = view.findViewById(R.id.fabSaveImages);
        img_pacient_img = view.findViewById(R.id.img_pacient_img);
        turn_images = view.findViewById(R.id.turn_images);

        list = controller.images();
        gridAdapter = new GridAdapter(context, R.layout.client_images_grid, list);
        grid_patient_images.setAdapter(gridAdapter);

        fabAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_pacient_img.setVisibility(View.VISIBLE);
                fabSaveImages.setVisibility(View.VISIBLE);
                selectImage();

            }
        });

        grid_patient_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    anamnesePodiatry = list.get(position);

                    id_patient_image = anamnesePodiatry.getIdimage();

                    txt_image = anamnesePodiatry.getImagetxt();

                    img_rotate = anamnesePodiatry.getImagerotate();

                    dialog_img.setContentView(R.layout.client_images_dialog);

                    PhotoView image = dialog_img.findViewById(R.id.img_dialog);
                    TextView txt = dialog_img.findViewById(R.id.txt_dialog);
                    btn_delete = dialog_img.findViewById(R.id.btn_delete_img_dialog);
                    btn_delete.setText(R.string.delete_image);
                    btn_delete.setTypeface(btn_delete.getTypeface(), Typeface.BOLD);
                    btn_delete.setBackground(getResources().getDrawable(R.drawable.edittext_custom));
                    txt.setText(txt_image);

                    if(anamnesePodiatry.getPatientimage() != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(anamnesePodiatry.getPatientimage(), 0,
                                anamnesePodiatry.getPatientimage().length);
                        image.setImageBitmap(bitmap);
                        image.setRotation(img_rotate*90);
                    }

                    btn_delete.setOnClickListener(new AdapterView.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            builder.setTitle(R.string.delete_image);
                            builder.setMessage(R.string.delete_img);
                            builder.setCancelable(true);
                            builder.setIcon(R.mipmap.ic_launcher);

                            builder.setPositiveButton(R.string.yes, new Dialog.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    try {

                                        if(controller.deleteImage(anamnesePodiatry)){

                                            UtilAnamnesePodiatry.showMensagem(getContext(), getResources().getString(R.string.deleted_images));

                                        }

                                    }catch (Exception e){

                                        Log.e("Adapter", "Erro: " + e.getMessage());

                                    }

                                    atualizarLista(controller.getAllImages());
                                    dialog_img.dismiss();

                                }
                            });

                            builder.setNegativeButton(R.string.cancel, new Dialog.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    dialog.cancel();

                                }
                            });

                            alert_img = builder.create();
                            alert_img.show();

                        }
                    });

                    dialog_img.show();

                }catch (Exception e){

                    UtilAnamnesePodiatry.showMensagem(getContext(), getResources().getString(R.string.cant_load) );

                }

            }
        });

        turn_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation = img_pacient_img.getRotation();
                rotation = rotation + 90.0f;
                if(rotation > 270.0f){
                    rotation = 0.0f;
                }
                img_pacient_img.setRotation(rotation);
                img_rotate = img_rotate + 1;
                if(img_rotate > 3){
                    img_rotate = 0;
                }

            }
        });

        fabSaveImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(img_pacient_img.getDrawable() != null) {

                    saveImages();

                }else{

                    UtilAnamnesePodiatry.showMensagem(context, getResources().getString(R.string.no_images));

                }

            }
        });

        return view;
    }

    private void selectImage() {
        final CharSequence[] options = {getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_device), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.add_photo);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getResources().getString(R.string.take_photo))) {

                    if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

                        getActivity().requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

                    }else{

                        openCamera();

                    }

                } else if (options[item].equals(getResources().getString(R.string.choose_from_device))) {

                    openGallery();

                } else if (options[item].equals(getResources().getString(R.string.cancel))) {

                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }

    public void openGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.sel_photo)), GALLERY_REQUEST);

    }

    private void openCamera(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

            photoURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, CAMERA_REQUEST);

        }else {

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileprovider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }


            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            try{

                Uri selImg = data.getData();
                Picasso.get().load(selImg).fit().centerCrop().into(img_pacient_img);
                turn_images.setVisibility(View.VISIBLE);

                DateFormat df = new SimpleDateFormat("dd/MM/yy - HH:mm");
                Date data_img = new Date();

                dataAtual = df.format(data_img);

            }catch (Exception exception){

                exception.printStackTrace();

            }

        }

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){

            try{

                Picasso.get().load(photoURI).fit().centerCrop().into(img_pacient_img);
                turn_images.setVisibility(View.VISIBLE);

                DateFormat df = new SimpleDateFormat("dd/MM/yy - HH:mm");
                Date data_img = new Date();

                dataAtual = df.format(data_img);

            }catch (Exception e){

                e.printStackTrace();

            }

        }

    }

    public void atualizarLista(ArrayList<AnamnesePodiatry> novosDados){

        this.list.clear();
        this.list.addAll(novosDados);
        gridAdapter.notifyDataSetChanged();

    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveImages(){

        try{

            anamnesePodiatry = new AnamnesePodiatry();
            anamnesePodiatry.setPatientid(ClientDetails.idSaved);
            anamnesePodiatry.setImagetxt(dataAtual);

            if(img_pacient_img.getDrawable() != null) {

                Bitmap bitmap = ((BitmapDrawable)img_pacient_img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArrayImage = stream.toByteArray();

                if (byteArrayImage != null) {

                    anamnesePodiatry.setPatientimage(byteArrayImage);
                    anamnesePodiatry.setImagerotate(img_rotate);

                }

            }

            if(controller.saveNewImage(anamnesePodiatry)){

                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_photo));
                img_pacient_img.setRotation(0);
                img_pacient_img.setImageDrawable(null);
                img_rotate = 0;
                rotation = 0.0f;
                fabSaveImages.setVisibility(View.GONE);
                img_pacient_img.setVisibility(View.GONE);
                turn_images.setVisibility(View.GONE);
                atualizarLista(controller.getAllImages());

            }else{

                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_photo));

            }

        }catch (Exception e){

            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_photo));

        }

    }
}