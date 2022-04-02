package net.anamneseonline.anamnesepodiatry.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private int layout, img_rotate = 0;
    private ArrayList<AnamnesePodiatry> dados;

    @Override
    public int getCount() {
        return dados.size();
    }

    @Override
    public Object getItem(int position) {
        return dados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{

        ImageView imageView_grid;
        TextView txt_grid;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){

            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layout, null);

            holder.txt_grid = row.findViewById(R.id.txt_grid);
            holder.imageView_grid = row.findViewById(R.id.imageView_grid);

            row.setTag(holder);

        }else{

            holder = (ViewHolder) row.getTag();

        }

        AnamnesePodiatry anamnesePodiatry = dados.get(position);

        holder.txt_grid.setText(anamnesePodiatry.getImagetxt());

        byte[] img_patient = anamnesePodiatry.getPatientimage();

        img_rotate = anamnesePodiatry.getImagerotate();

        if(img_patient != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img_patient, 0, img_patient.length);
            holder.imageView_grid.setImageBitmap(bitmap);
            holder.imageView_grid.setRotation(img_rotate*90);
        }else{
            Drawable imgDraw = ContextCompat.getDrawable(context, R.drawable.ic_profile);
            holder.imageView_grid.setImageDrawable(imgDraw);
        }

        return row;
    }

    public GridAdapter(Context context, int layout, ArrayList<AnamnesePodiatry> dados) {
        this.context = context;
        this.layout = layout;
        this.dados = dados;
    }
}
