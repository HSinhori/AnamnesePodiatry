package net.anamneseonline.anamnesepodiatry.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;

import java.util.ArrayList;

public class AnamnesePodiatryAdapter extends ArrayAdapter<AnamnesePodiatry> implements View.OnClickListener {

    Context context;

    ArrayList<AnamnesePodiatry> dados;

    ViewHolder linha;

    Bitmap bitmap;

    private int img_rotate_costumer = 0;

    private static class ViewHolder {

        TextView txt_name_customer;
        TextView txt_client_phone;
        ImageView img_pacient_listview;
    }


    public AnamnesePodiatryAdapter(ArrayList<AnamnesePodiatry> dataSet, Context context) {
        super(context, R.layout.listview_clients_list, dataSet);

        this.dados = dataSet;

        this.context = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);


    }

    @Override
    public void onClick(final View view) {

    }

    @NonNull
    @Override
    public View getView(int position, View dataSet, @NonNull ViewGroup parent) {

        final AnamnesePodiatry anamnese_podiatry = getItem(position);

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutClientList = LayoutInflater.from(getContext());

            dataSet = layoutClientList.inflate(R.layout.listview_clients_list, parent, false);

            linha.txt_name_customer = dataSet.findViewById(R.id.txt_client_name);
            linha.txt_client_phone = dataSet.findViewById(R.id.txt_client_phone);
            linha.img_pacient_listview = dataSet.findViewById(R.id.img_pacient_listview);

            dataSet.setTag(linha);

        } else {

            linha = (ViewHolder) dataSet.getTag();

        }

        linha.txt_name_customer.setText(anamnese_podiatry.getName());
        img_rotate_costumer = anamnese_podiatry.getImagerotatecostumer();
        if(anamnese_podiatry.getImgprofilecustomer() != null){
            bitmap = BitmapFactory.decodeByteArray(anamnese_podiatry.getImgprofilecustomer(), 0,
                    anamnese_podiatry.getImgprofilecustomer().length);
            linha.img_pacient_listview.setImageBitmap(bitmap);
            linha.img_pacient_listview.setRotation(img_rotate_costumer*90);
        }else{
            Drawable imgDraw = ContextCompat.getDrawable(getContext(), R.drawable.ic_profile);
            linha.img_pacient_listview.setImageDrawable(imgDraw);
        }
        if (anamnese_podiatry.getPhonetwo().equals("")){
            linha.txt_client_phone.setText(anamnese_podiatry.getPhone());
        }else if (anamnese_podiatry.getPhone().equals(anamnese_podiatry.getPhonetwo())) {
            linha.txt_client_phone.setText(anamnese_podiatry.getPhone());
        } else {
            linha.txt_client_phone.setText(anamnese_podiatry.getPhone() + " / " + anamnese_podiatry.getPhonetwo());
        }

        return dataSet;
    }
}
