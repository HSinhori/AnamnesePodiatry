package net.anamneseonline.anamnesepodiatry.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import org.intellij.lang.annotations.JdkConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class CustomerProfile extends Fragment {

    FloatingActionButton fab_edit_client, fabPdf;
    View view;
    private boolean fab_customer_profile_check;
    AnamnesePodiatryController controller;

    private int img_int = 0, img_rotate_costumer = 0;

    private String currentPhotoPath;

    EditText edit_customer_date, edit_customer_name, edit_customer_gender, edit_customer_address, edit_customer_district,
            edit_customer_city, edit_customer_phone, edit_customer_phone_two, edit_customer_profession;

    CircularImageView img_pacient_profile;
    ImageView turn_costumer;

    private byte[] byteArray;

    private static final int GALLERY_REQUEST = 100;
    private static final int CAMERA_REQUEST = 200;
    private static final int CAMERA_REQUEST_CODE = 300;
    private Uri photoURI;
    private float rotation = 0.0f;

    private TextView txt_customer_profile_pdf_saved;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_customer_profile, container, false);

        controller = new AnamnesePodiatryController(getContext());

        edit_customer_date = view.findViewById(R.id.edit_customer_date);
        edit_customer_name = view.findViewById(R.id.edit_customer_name);
        edit_customer_gender = view.findViewById(R.id.edit_customer_gender);
        edit_customer_address = view.findViewById(R.id.edit_customer_address);
        edit_customer_district = view.findViewById(R.id.edit_customer_district);
        edit_customer_city = view.findViewById(R.id.edit_customer_city);
        edit_customer_phone = view.findViewById(R.id.edit_customer_phone);
        edit_customer_phone_two = view.findViewById(R.id.edit_customer_phone_two);
        edit_customer_profession = view.findViewById(R.id.edit_customer_profession);
        turn_costumer = view.findViewById(R.id.turn_costumer);
        fabPdf = view.findViewById(R.id.fabPdf);

        txt_customer_profile_pdf_saved = view.findViewById(R.id.txt_customer_profile_pdf_saved);

        img_pacient_profile = (CircularImageView) view.findViewById(R.id.img_pacient_profile);

        edit_customer_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        edit_customer_phone_two.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        setInfoProfile();

        fab_customer_profile_check = true;

        fab_edit_client = view.findViewById(R.id.fabEditClient);
        fab_edit_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fab_edit_client.setImageResource(android.R.drawable.ic_menu_save);
                    fab_edit_client.setColorFilter(Color.WHITE);

                    img_pacient_profile.setEnabled(true);
                    edit_customer_date.setEnabled(true);
                    edit_customer_name.setEnabled(true);
                    edit_customer_gender.setEnabled(true);
                    edit_customer_address.setEnabled(true);
                    edit_customer_district.setEnabled(true);
                    edit_customer_city.setEnabled(true);
                    edit_customer_phone.setEnabled(true);
                    edit_customer_phone_two.setEnabled(true);
                    edit_customer_profession.setEnabled(true);
                    turn_costumer.setVisibility(View.VISIBLE);
                    img_int = 1;

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));

                    fab_customer_profile_check = false;
                }else{

                    try {

                        if(edit_customer_name.getText().toString().equals("")){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.null_name));
                            edit_customer_name.setError("*");
                            edit_customer_name.requestFocus();

                        }else if(edit_customer_phone.getText().toString().equals("")){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.null_phone));
                            edit_customer_phone.setError("*");
                            edit_customer_phone.requestFocus();

                        }else {

                            AnamnesePodiatry obj = new AnamnesePodiatry();
                            obj.setId(ClientDetails.idSaved);
                            obj.setDate(edit_customer_date.getText().toString());
                            obj.setName(edit_customer_name.getText().toString());
                            obj.setGender(edit_customer_gender.getText().toString());
                            obj.setAddress(edit_customer_address.getText().toString());
                            obj.setDistrict(edit_customer_district.getText().toString());
                            obj.setCity(edit_customer_city.getText().toString());
                            obj.setPhone(edit_customer_phone.getText().toString());
                            obj.setPhonetwo(edit_customer_phone_two.getText().toString());
                            obj.setProfession(edit_customer_profession.getText().toString());
                            if (img_pacient_profile.getDrawable() != null) {

                                Bitmap bitmap = ((BitmapDrawable) img_pacient_profile.getDrawable()).getBitmap();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byteArray = stream.toByteArray();

                                if (byteArray != null) {

                                    obj.setImgprofilecustomer(byteArray);

                                }

                            }
                            obj.setImagerotatecostumer(img_rotate_costumer);

                            if (controller.alterPacientProfile(obj)) {

                                fab_edit_client.setImageResource(android.R.drawable.ic_menu_edit);
                                fab_edit_client.setColorFilter(Color.WHITE);

                                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));

                                edit_customer_date.setEnabled(false);
                                edit_customer_name.setEnabled(false);
                                edit_customer_gender.setEnabled(false);
                                edit_customer_address.setEnabled(false);
                                edit_customer_district.setEnabled(false);
                                edit_customer_city.setEnabled(false);
                                edit_customer_phone.setEnabled(false);
                                edit_customer_phone_two.setEnabled(false);
                                edit_customer_profession.setEnabled(false);
                                img_rotate_costumer = 0;
                                rotation = 0.0f;
                                turn_costumer.setVisibility(View.GONE);
                                img_int = 0;

                                fab_customer_profile_check = true;

                            } else {

                                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                            }
                        }

                    }catch (Exception e){

                        UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                    }
                }
            }
        });

        fabPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPdf();
            }
        });

        img_pacient_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(img_int == 1) {

                    selectImage();

                }
            }
        });

        turn_costumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation = img_pacient_profile.getRotation();
                //UtilAnamnesePodiatry.showMensagem(getContext(), Float.toString(rotation));
                rotation = rotation + 90.0f;
                if(rotation > 270.0f){
                    rotation = 0.0f;
                }
                img_pacient_profile.setRotation(rotation);
                img_rotate_costumer = img_rotate_costumer + 1;
                if(img_rotate_costumer > 3){
                    img_rotate_costumer = 0;
                }

            }
        });

        return view;
    }

    private void setInfoProfile(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            edit_customer_date.setText(anamnesePodiatry.getDate());
            edit_customer_name.setText(anamnesePodiatry.getName());
            edit_customer_gender.setText(anamnesePodiatry.getGender());
            edit_customer_address.setText(anamnesePodiatry.getAddress());
            edit_customer_district.setText(anamnesePodiatry.getDistrict());
            edit_customer_city.setText(anamnesePodiatry.getCity());
            edit_customer_phone.setText(anamnesePodiatry.getPhone());
            edit_customer_phone_two.setText(anamnesePodiatry.getPhonetwo());
            edit_customer_profession.setText(anamnesePodiatry.getProfession());
            img_rotate_costumer = anamnesePodiatry.getImagerotatecostumer();
            if(anamnesePodiatry.getImgprofilecustomer() != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(anamnesePodiatry.getImgprofilecustomer(), 0,
                        anamnesePodiatry.getImgprofilecustomer().length);
                img_pacient_profile.setImageBitmap(bitmap);
                img_pacient_profile.setRotation(img_rotate_costumer*90);
            }

        }

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
                Picasso.get().load(selImg).fit().centerCrop().into(img_pacient_profile);

            }catch (Exception exception){

                exception.printStackTrace();

            }

        }

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){

            Picasso.get().load(photoURI).fit().centerCrop().into(img_pacient_profile);
        }

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

    private void infoPdf(){

        try {

            String pdfDate = "";
            String pdfName = "";
            String pdfGender = "";
            String pdfAddress = "";
            String pdfDistrict = "";
            String pdfCity = "";
            String pdfPhone = "";
            String pdfPhoneTwo = "";
            String pdfProfession = "";
            String pdfAnnotations = "";
            String pdfdiabetes = getString(R.string.no);
            String pdfdiabetes_tipo = "";
            String pdfdiabetes_tempo = "";
            String pdfalergias = getString(R.string.no);
            String pdfalergias_quais = "";
            String pdftabagismo = getString(R.string.no);
            String pdfalcoolico = getString(R.string.no);
            String pdfoftalmologicos = getString(R.string.no);
            String pdfcardiovascular = getString(R.string.no);
            String pdfrenais = getString(R.string.no);
            String pdfoutros = "";
            String pdfPalmarLeft = getString(R.string.no);
            String pdfPalmarRight = getString(R.string.no);
            String pdfHandKeraLeft = getString(R.string.no);
            String pdfHandKeraRight = getString(R.string.no);
            String pdfOnychoLeft = getString(R.string.no);
            String pdfOnychoRight = getString(R.string.no);
            String pdfOnychomyLeft = getString(R.string.no);
            String pdfOnychomyRight = getString(R.string.no);
            String pdfPyoLeft = getString(R.string.no);
            String pdfPyoRight = getString(R.string.no);
            String pdfWartLeft = getString(R.string.no);
            String pdfWartRight = getString(R.string.no);
            String pdfCwocLeft = getString(R.string.no);
            String pdfCwocRight = getString(R.string.no);
            String pdfCwcLeft = getString(R.string.no);
            String pdfCwcRight = getString(R.string.no);
            String pdfFissuresLeft = getString(R.string.no);
            String pdfFissuresRight = getString(R.string.no);
            String pdfKeraLeft = getString(R.string.no);
            String pdfKeraRight = getString(R.string.no);
            String pdfRingWormLeft = getString(R.string.no);
            String pdfRingWormRight = getString(R.string.no);
            String pdfUlcerLeft = getString(R.string.no);
            String pdfUlcerRight = getString(R.string.no);
            String pdfWearNumb = "0";
            String pdfSuitable = getString(R.string.no);
            String pdfHygiene = getString(R.string.no);
            String pdfNailShape = getString(R.string.dedo_normal);
            String pdfCyanotic = getString(R.string.no);
            String pdfResected = getString(R.string.no);
            String pdfOily = getString(R.string.no);
            String pdfFine = getString(R.string.no);
            String pdfSwollen = getString(R.string.no);
            String pdfBromidose = getString(R.string.no);
            String pdfAhnidrosis = getString(R.string.no);
            String pdfHyperhi = getString(R.string.no);
            String pdfPlantar = getString(R.string.no);
            String pdfCanvys = getString(R.string.no);
            String pdfFlat = getString(R.string.no);
            String pdfHalux = getString(R.string.no);
            String pdfVarus = getString(R.string.no);
            String pdfBunion = getString(R.string.no);
            String pdfCn = getString(R.string.no);
            String pdfSn = getString(R.string.no);
            String pdfCallus = getString(R.string.no);
            String pdfCrack = getString(R.string.no);
            String pdfKeratosis = getString(R.string.no);
            String pdfWart = getString(R.string.no);
            String pdfOnycho = getString(R.string.no);
            String pdfOnico = getString(R.string.no);
            String pdfRefB = getString(R.string.no);
            String pdfRefR = getString(R.string.no);

            List<AnamnesePodiatry> patientPdf = controller.PacienttProfileList();

            for (AnamnesePodiatry anamnesePodiatry : patientPdf) {

                if(anamnesePodiatry.getDate() != null) {
                    pdfDate = anamnesePodiatry.getDate();
                }


                if(anamnesePodiatry.getName() != null) {
                    pdfName = anamnesePodiatry.getName();
                }

                if(anamnesePodiatry.getGender() != null) {
                    pdfGender = anamnesePodiatry.getGender();
                }

                if(anamnesePodiatry.getAddress() != null) {
                    pdfAddress = anamnesePodiatry.getAddress();
                }

                if(anamnesePodiatry.getDistrict() != null) {
                    pdfDistrict = anamnesePodiatry.getDistrict();
                }

                if(anamnesePodiatry.getCity() != null) {
                    pdfCity = anamnesePodiatry.getCity();
                }

                if(anamnesePodiatry.getPhone() != null) {
                    pdfPhone = anamnesePodiatry.getPhone();
                }

                if(anamnesePodiatry.getPhonetwo() != null) {
                    pdfPhoneTwo = anamnesePodiatry.getPhonetwo();
                }

                if(anamnesePodiatry.getProfession() != null) {
                    pdfProfession = anamnesePodiatry.getProfession();
                }

                if(anamnesePodiatry.getAnnnotations() != null) {
                    pdfAnnotations = anamnesePodiatry.getAnnnotations();
                }

                if(anamnesePodiatry.getDiabetes() == 1) {

                    pdfdiabetes = getString(R.string.yes);

                }

                if(anamnesePodiatry.getDiabetestype() != null && anamnesePodiatry.getDiabetes() == 1){

                    pdfdiabetes_tipo = anamnesePodiatry.getDiabetestype();

                }

                if(anamnesePodiatry.getDiabetestime() != null && anamnesePodiatry.getDiabetes() == 1){

                    pdfdiabetes_tempo = anamnesePodiatry.getDiabetestime();

                }

                if(anamnesePodiatry.getAllergies() == 1) {

                    pdfalergias = getString(R.string.yes);

                }

                if(anamnesePodiatry.getAllergieswhat() != null && anamnesePodiatry.getAllergies() == 1){

                    pdfalergias_quais = anamnesePodiatry.getAllergieswhat();

                }

                if(anamnesePodiatry.getSmoking() == 1) {

                    pdftabagismo = getString(R.string.yes);

                }

                if(anamnesePodiatry.getAlcoholic() == 1) {

                    pdfalcoolico = getString(R.string.yes);
                }

                if(anamnesePodiatry.getOphtahlm() == 1) {

                    pdfoftalmologicos = getString(R.string.yes);

                }

                if(anamnesePodiatry.getCardiov() == 1) {

                    pdfcardiovascular = getString(R.string.yes);

                }

                if(anamnesePodiatry.getRenal() == 1) {

                    pdfrenais = getString(R.string.yes);
                }

                if(anamnesePodiatry.getOthersclin() != null){

                    pdfoutros = anamnesePodiatry.getOthersclin();

                }

                if(anamnesePodiatry.getPalmarleft() == 1) {

                    pdfPalmarLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getPalmarright() == 1) {

                    pdfPalmarRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandkeraleft() == 1) {

                    pdfHandKeraLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandkeraright() == 1) {

                    pdfHandKeraRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandonycholeft() == 1) {

                    pdfOnychoLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandonychoright() == 1) {

                    pdfOnychoRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandonychomyleft() == 1) {

                    pdfOnychomyLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHandonychomyright() == 1) {

                    pdfOnychomyRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getPyoleft() == 1) {

                    pdfPyoLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getPyoright() == 1) {

                    pdfPyoRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getWartleft() == 1) {

                    pdfWartLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getCwocleft() == 1) {

                    pdfCwocLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getCwcleft() == 1) {

                    pdfCwcLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getFissuresleft() == 1) {

                    pdfFissuresLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getKeratosisleft() == 1) {

                    pdfKeraLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getRingwormleft() == 1) {

                    pdfRingWormLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getUlcerleft() == 1) {

                    pdfUlcerLeft = getString(R.string.yes);

                }

                if(anamnesePodiatry.getWartright() == 1) {

                    pdfWartRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getCwocright() == 1) {

                    pdfCwocRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getCwcright() == 1) {

                    pdfCwcRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getFissuresright() == 1) {

                    pdfFissuresRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getKeratosisright() == 1) {

                    pdfKeraRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getRingwormright() == 1) {

                    pdfRingWormRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getUlcerright() == 1) {

                    pdfUlcerRight = getString(R.string.yes);

                }

                if(anamnesePodiatry.getFootwearnum() > 0){

                    pdfWearNumb = String.valueOf(anamnesePodiatry.getFootwearnum());

                }

                if(anamnesePodiatry.getSuitable() == 1) {

                    pdfSuitable = getString(R.string.yes);

                }

                if(anamnesePodiatry.getHygiene() == 1) {

                    pdfHygiene = getString(R.string.yes);

                }

                if(anamnesePodiatry.getNailshape() <= 0 || anamnesePodiatry.getNailshape() > 7) {
                    pdfNailShape = getString(R.string.dedo_normal);
                }else if(anamnesePodiatry.getNailshape() == 1){
                    pdfNailShape = getString(R.string.dedo_incorreto);
                }else if(anamnesePodiatry.getNailshape() == 2){
                    pdfNailShape = getString(R.string.dedo_involuta);
                }else if(anamnesePodiatry.getNailshape() == 3){
                    pdfNailShape = getString(R.string.dedo_telha);
                }else if(anamnesePodiatry.getNailshape() == 4) {
                    pdfNailShape = getString(R.string.dedo_funil);
                }else if(anamnesePodiatry.getNailshape() == 5){
                    pdfNailShape = getString(R.string.dedo_caracol);
                }else if(anamnesePodiatry.getNailshape() == 6){
                    pdfNailShape = getString(R.string.dedo_cunha);
                }else if(anamnesePodiatry.getNailshape() == 7) {
                    pdfNailShape = getString(R.string.dedo_gancho);
                }

                if (anamnesePodiatry.getCyanotic() == 1) {

                    pdfCyanotic = getString(R.string.yes);

                }

                if (anamnesePodiatry.getResected() == 1) {

                    pdfResected = getString(R.string.yes);

                }

                if (anamnesePodiatry.getOily() == 1) {

                    pdfOily = getString(R.string.yes);

                }

                if (anamnesePodiatry.getFine() == 1) {

                    pdfFine = getString(R.string.yes);

                }

                if (anamnesePodiatry.getSwollen() == 1) {

                    pdfSwollen = getString(R.string.yes);

                }

                if (anamnesePodiatry.getBromidose() == 1) {

                    pdfBromidose = getString(R.string.yes);

                }

                if (anamnesePodiatry.getAhnidrosis() == 1) {

                    pdfAhnidrosis = getString(R.string.yes);
                }

                if (anamnesePodiatry.getHyperhi() == 1) {

                    pdfHyperhi = getString(R.string.yes);

                }

                if (anamnesePodiatry.getPlantar() == 1) {

                    pdfPlantar = getString(R.string.yes);

                }

                if (anamnesePodiatry.getCavys() == 1) {

                    pdfCanvys = getString(R.string.yes);

                }

                if (anamnesePodiatry.getFlat() == 1) {

                    pdfFlat = getString(R.string.yes);

                }

                if (anamnesePodiatry.getHaluxvalgus() == 1) {

                    pdfHalux = getString(R.string.yes);

                }

                if (anamnesePodiatry.getVarus() == 1) {

                    pdfVarus = getString(R.string.yes);

                }

                if (anamnesePodiatry.getBunion() == 1) {

                    pdfBunion = getString(R.string.yes);

                }

                if (anamnesePodiatry.getCn() == 1) {

                    pdfCn = getString(R.string.yes);

                }

                if (anamnesePodiatry.getSn() == 1) {

                    pdfSn = getString(R.string.yes);

                }

                if (anamnesePodiatry.getCallus() == 1) {

                    pdfCallus = getString(R.string.yes);

                }

                if (anamnesePodiatry.getCrack() == 1) {

                    pdfCrack = getString(R.string.yes);

                }

                if (anamnesePodiatry.getKeratosis() == 1) {

                    pdfKeratosis = getString(R.string.yes);

                }

                if (anamnesePodiatry.getWart() == 1) {

                    pdfWart = getString(R.string.yes);

                }

                if (anamnesePodiatry.getOnychomycosis() == 1) {

                    pdfOnycho = getString(R.string.yes);

                }

                if (anamnesePodiatry.getOnicocryptosis() == 1) {

                    pdfOnico = getString(R.string.yes);

                }

                if (anamnesePodiatry.getRef1001B() == 1) {

                    pdfRefB = getString(R.string.yes);

                }

                if (anamnesePodiatry.getRef1001R() == 1) {

                    pdfRefR = getString(R.string.yes);

                }

            }

            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Canvas canvas;
            StaticLayout staticLayout;
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(16);

            PdfDocument.PageInfo pageOneInfo = new PdfDocument.PageInfo.Builder(
                    595, 842, 1
            ).create();
            PdfDocument.Page pageOne = pdfDocument.startPage(pageOneInfo);
            canvas = pageOne.getCanvas();

            myPaint.setTextSize(50);
            myPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.customer_profile), canvas.getWidth()/2.0f, 60, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.drawRect(30, 65, canvas.getWidth() - 30, 65, myPaint);

            myPaint.setTextSize(16);
            canvas.drawText(getString(R.string.client_date)+" "+pdfDate, 30, 120, myPaint);
            canvas.drawText(getString(R.string.patient_name)+": "+pdfName, 30, 145, myPaint);
            canvas.drawText(getString(R.string.client_gender)+": "+pdfGender, 30, 170, myPaint);
            canvas.drawText(getString(R.string.client_address)+": "+pdfAddress, 30, 195, myPaint);
            canvas.drawText(getString(R.string.client_district)+": "+pdfDistrict, 30, 220, myPaint);
            canvas.drawText(getString(R.string.client_city)+": "+pdfCity, 30, 245, myPaint);
            canvas.drawText(getString(R.string.client_phone)+": "+pdfPhone, 30, 270, myPaint);
            canvas.drawText(getString(R.string.client_phone_two)+": "+pdfPhoneTwo, 30, 295, myPaint);
            canvas.drawText(getString(R.string.client_profession)+": "+pdfProfession, 30, 320, myPaint);

            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.annotations)+":", 30, 375, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            staticLayout = StaticLayout.Builder
                    .obtain(pdfAnnotations, 0, pdfAnnotations.length(), textPaint, 505)
                    .build();
            canvas.translate(45, 390);
            staticLayout.draw(canvas);
            pdfDocument.finishPage(pageOne);

            //////FINAL DA PAGINA 1

            PdfDocument.PageInfo pageTwoInfo = new PdfDocument.PageInfo.Builder(
                    595, 842, 2
            ).create();
            PdfDocument.Page pageTwo = pdfDocument.startPage(pageTwoInfo);
            canvas = pageTwo.getCanvas();

            myPaint.setTextSize(50);
            myPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.clinical_data), canvas.getWidth()/2.0f, 60, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.drawRect(30, 65, canvas.getWidth() - 30, 65, myPaint);

            myPaint.setTextSize(16);
            canvas.drawText(getString(R.string.diabetes_customer)+": "+pdfdiabetes, 30, 120, myPaint);
            canvas.drawText(getString(R.string.diabetes_type)+": "+pdfdiabetes_tipo, 30, 145, myPaint);
            canvas.drawText(getString(R.string.diabetes_time)+": "+pdfdiabetes_tempo, 30, 170, myPaint);
            canvas.drawText(getString(R.string.allergies_customer)+": "+pdfalergias, 30, 195, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.whats_allergies)+": ", 30, 220, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            staticLayout = StaticLayout.Builder
                    .obtain(pdfalergias_quais, 0, pdfalergias_quais.length(), textPaint, 505)
                    .build();
            canvas.translate(45, 235);
            staticLayout.draw(canvas);
            canvas.restore();
            canvas.drawText(getString(R.string.smoking_customer)+": "+pdftabagismo, 30, 285, myPaint);
            canvas.drawText(getString(R.string.alcoholic_customer)+": "+pdfalcoolico, 30, 310, myPaint);
            canvas.drawText(getString(R.string.ophthalmology_customer)+": "+pdfoftalmologicos, 30, 335, myPaint);
            canvas.drawText(getString(R.string.cardiovascular_customer)+": "+pdfcardiovascular, 30, 360, myPaint);
            canvas.drawText(getString(R.string.renal_customer)+": "+pdfrenais, 30, 385, myPaint);

            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.clinical_others)+":", 30, 440, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            staticLayout = StaticLayout.Builder
                    .obtain(pdfoutros, 0, pdfoutros.length(), textPaint, 505)
                    .build();
            canvas.translate(45, 455);
            staticLayout.draw(canvas);
            pdfDocument.finishPage(pageTwo);

            ///////FINAL DA PAGINA 2

            PdfDocument.PageInfo pageThreeInfo = new PdfDocument.PageInfo.Builder(
                    595, 842, 3
            ).create();
            PdfDocument.Page pageThree = pdfDocument.startPage(pageThreeInfo);
            canvas = pageThree.getCanvas();

            myPaint.setTextSize(50);
            myPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.hands), canvas.getWidth()/2.0f, 60, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.drawRect(30, 65, canvas.getWidth() - 30, 65, myPaint);

            myPaint.setTextSize(16);

            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.hand_left)+": ", 30, 120, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(getString(R.string.palmar_wart)+": "+pdfPalmarLeft, 45, 140, myPaint);
            canvas.drawText(getString(R.string.keratosis)+": "+pdfHandKeraLeft, 45, 165, myPaint);
            canvas.drawText(getString(R.string.onicocryptosis)+": "+pdfOnychoLeft, 45, 190, myPaint);
            canvas.drawText(getString(R.string.onychomycosis)+": "+pdfOnychomyLeft, 45, 215, myPaint);
            canvas.drawText(getString(R.string.pyo_granu)+": "+pdfPyoLeft, 45, 240, myPaint);canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.hand_right)+": ", 30, 270, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(getString(R.string.palmar_wart)+": "+pdfPalmarRight, 45, 290, myPaint);
            canvas.drawText(getString(R.string.keratosis)+": "+pdfHandKeraRight, 45, 315, myPaint);
            canvas.drawText(getString(R.string.onicocryptosis)+": "+pdfOnychoRight, 45, 340, myPaint);
            canvas.drawText(getString(R.string.onychomycosis)+": "+pdfOnychomyRight, 45, 365, myPaint);
            canvas.drawText(getString(R.string.pyo_granu)+": "+pdfPyoRight, 45, 390, myPaint);

            pdfDocument.finishPage(pageThree);

            ///////FINAL DA PAGINA 3

            PdfDocument.PageInfo pageFourInfo = new PdfDocument.PageInfo.Builder(
                    595, 842, 4
            ).create();
            PdfDocument.Page pageFour = pdfDocument.startPage(pageFourInfo);
            canvas = pageFour.getCanvas();

            myPaint.setTextSize(50);
            myPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.feet), canvas.getWidth()/2.0f, 60, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.drawRect(30, 65, canvas.getWidth() - 30, 65, myPaint);

            myPaint.setTextSize(16);

            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.foot_left)+": ", 30, 120, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(getString(R.string.plant_wart)+": "+pdfWartLeft, 45, 140, myPaint);
            canvas.drawText(getString(R.string.cwoc)+": "+pdfCwocLeft, 45, 165, myPaint);
            canvas.drawText(getString(R.string.cwc)+": "+pdfCwcLeft, 45, 190, myPaint);
            canvas.drawText(getString(R.string.fissures)+": "+pdfFissuresLeft, 45, 215, myPaint);
            canvas.drawText(getString(R.string.keratosis)+": "+pdfKeraLeft, 45, 240, myPaint);
            canvas.drawText(getString(R.string.ringworm)+": "+pdfRingWormLeft, 45, 265, myPaint);
            canvas.drawText(getString(R.string.ulcer)+": "+pdfUlcerLeft, 45, 290, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.foot_right)+": ", 30, 320, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(getString(R.string.plant_wart)+": "+pdfWartRight, 45, 340, myPaint);
            canvas.drawText(getString(R.string.cwoc)+": "+pdfCwocRight, 45, 365, myPaint);
            canvas.drawText(getString(R.string.cwc)+": "+pdfCwcRight, 45, 390, myPaint);
            canvas.drawText(getString(R.string.fissures)+": "+pdfFissuresRight, 45, 415, myPaint);
            canvas.drawText(getString(R.string.keratosis)+": "+pdfKeraRight, 45, 440, myPaint);
            canvas.drawText(getString(R.string.ringworm)+": "+pdfRingWormRight, 45, 465, myPaint);
            canvas.drawText(getString(R.string.ulcer)+": "+pdfUlcerRight, 45, 490, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.calc_tam)+": ", 30, 520, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(pdfWearNumb, 45, 540, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.suitable)+": ", 30, 570, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(pdfSuitable, 45, 590, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.hygiene)+": ", 30, 620, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(pdfHygiene, 45, 640, myPaint);
            canvas.save();
            myPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(getString(R.string.nail_shape)+": ", 30, 670, myPaint);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.restore();
            canvas.drawText(pdfNailShape, 45, 690, myPaint);

            pdfDocument.finishPage(pageFour);

            ///////FINAL DA PAGINA 4

            PdfDocument.PageInfo pageFiveInfo = new PdfDocument.PageInfo.Builder(
                    595, 842, 5
            ).create();
            PdfDocument.Page pageFive = pdfDocument.startPage(pageFiveInfo);
            canvas = pageFive.getCanvas();

            myPaint.setTextSize(50);
            myPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(getString(R.string.perfusion), canvas.getWidth()/2.0f, 60, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            canvas.drawRect(30, 65, canvas.getWidth() - 30, 65, myPaint);

            myPaint.setTextSize(16);

            canvas.drawText(getString(R.string.cyanotic)+": "+pdfCyanotic, 45, 120, myPaint);
            canvas.drawText(getString(R.string.resected)+": "+pdfResected, 45, 145, myPaint);
            canvas.drawText(getString(R.string.oily)+": "+pdfOily, 45, 170, myPaint);
            canvas.drawText(getString(R.string.fine)+": "+pdfFine, 45, 195, myPaint);
            canvas.drawText(getString(R.string.swollen)+": "+pdfSwollen, 45, 220, myPaint);
            canvas.drawText(getString(R.string.bromidose)+": "+pdfBromidose, 45, 245, myPaint);
            canvas.drawText(getString(R.string.anhidrosis)+": "+pdfAhnidrosis, 45, 270, myPaint);
            canvas.drawText(getString(R.string.hyperhi)+": "+pdfHyperhi, 45, 295, myPaint);
            canvas.drawText(getString(R.string.plantar)+": "+pdfPlantar, 45, 320, myPaint);
            canvas.drawText(getString(R.string.cavys)+": "+pdfCanvys, 45, 345, myPaint);
            canvas.drawText(getString(R.string.flat)+": "+pdfFlat, 45, 370, myPaint);
            canvas.drawText(getString(R.string.halux_valgus)+": "+pdfHalux, 45, 395, myPaint);
            canvas.drawText(getString(R.string.varus)+": "+pdfVarus, 45, 420, myPaint);
            canvas.drawText(getString(R.string.bunion)+": "+pdfBunion, 45, 445, myPaint);
            canvas.drawText(getString(R.string.cn)+": "+pdfCn, 45, 470, myPaint);
            canvas.drawText(getString(R.string.sn)+": "+pdfSn, 45, 495, myPaint);
            canvas.drawText(getString(R.string.callus)+": "+pdfCallus, 45, 520, myPaint);
            canvas.drawText(getString(R.string.crack)+": "+pdfCrack, 45, 545, myPaint);
            canvas.drawText(getString(R.string.keratosis)+": "+pdfKeratosis, 45, 570, myPaint);
            canvas.drawText(getString(R.string.wart)+": "+pdfWart, 45, 595, myPaint);
            canvas.drawText(getString(R.string.onychomycosis)+": "+pdfOnycho, 45, 620, myPaint);
            canvas.drawText(getString(R.string.onicocryptosis)+": "+pdfOnico, 45, 645, myPaint);
            canvas.drawText(getString(R.string.refB)+": "+pdfRefB, 45, 670, myPaint);
            canvas.drawText(getString(R.string.refR)+": "+pdfRefR, 45, 695, myPaint);

            pdfDocument.finishPage(pageFive);

            //////GERAR PDF ABAIXO

            File file = new File(requireActivity()
                    .getExternalFilesDir("/"), "patient_" + pdfName + ".pdf");
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setDataAndType(Uri.fromFile(file), "application/pdf");
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            txt_customer_profile_pdf_saved.setText("PDF saved in: " + file);

            //Intent intent = Intent.createChooser(i, getString(R.string.open_pdf));
            try{
                startActivity(Intent.createChooser(i, getString(R.string.open_pdf)));
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
                Log.d("pdf", e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d("pdf", e.getMessage());
        }

    }

}