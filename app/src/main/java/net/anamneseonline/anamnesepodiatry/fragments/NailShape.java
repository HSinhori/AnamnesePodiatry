package net.anamneseonline.anamnesepodiatry.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.List;

public class NailShape extends Fragment {

    View view;

    RadioGroup radioGroup;

    RadioButton rB_normal, rB_incorreto, rB_involuta, rB_telha, rB_funil, rB_caracol, rB_gancho, rB_cunha, rB_checked;

    FloatingActionButton fabEditNail;

    AnamnesePodiatryController controller;

    private int nailshape;

    private boolean fab_customer_profile_check;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_nail_shape, container, false);

        controller = new AnamnesePodiatryController(getContext());

        rB_normal = view.findViewById(R.id.rB_normal);
        rB_incorreto = view.findViewById(R.id.rB_incorreto);
        rB_involuta = view.findViewById(R.id.rB_involuta);
        rB_telha = view.findViewById(R.id.rB_telha);
        rB_funil = view.findViewById(R.id.rB_funil);
        rB_caracol = view.findViewById(R.id.rB_caracol);
        rB_gancho = view.findViewById(R.id.rB_gancho);
        rB_cunha = view.findViewById(R.id.rB_cunha);

        radioGroup = view.findViewById(R.id.rG_nail);

        setInfoNailShape();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                //nailshape = radioGroup.getCheckedRadioButtonId();
                //Toast.makeText(getActivity(), String.valueOf(checkedId), Toast.LENGTH_SHORT).show();

                switch(checkedId){

                    case R.id.rB_normal:
                        nailshape = 0;
                        break;

                    case R.id.rB_incorreto:
                        nailshape = 1;
                        break;

                    case R.id.rB_involuta:
                        nailshape = 2;
                        break;

                    case R.id.rB_telha:
                        nailshape = 3;
                        break;

                    case R.id.rB_funil:
                        nailshape = 4;
                        break;

                    case R.id.rB_caracol:
                        nailshape = 5;
                        break;

                    case R.id.rB_cunha:
                        nailshape = 6;
                        break;

                    case R.id.rB_gancho:
                        nailshape = 7;
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + checkedId);
                }

            }
        });

        fab_customer_profile_check = true;

        fabEditNail = view.findViewById(R.id.fabEditNail);
        fabEditNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fabEditNail.setImageResource(android.R.drawable.ic_menu_save);
                    fabEditNail.setColorFilter(Color.WHITE);

                    rB_normal.setEnabled(true);
                    rB_incorreto.setEnabled(true);
                    rB_involuta.setEnabled(true);
                    rB_telha.setEnabled(true);
                    rB_funil.setEnabled(true);
                    rB_caracol.setEnabled(true);
                    rB_cunha.setEnabled(true);
                    rB_gancho.setEnabled(true);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));

                    fab_customer_profile_check = false;

                }else{

                    saveNailShape();

                }
            }
        });

        return view;
    }

    private void saveNailShape() {

        fabEditNail.setImageResource(android.R.drawable.ic_menu_edit);
        fabEditNail.setColorFilter(Color.WHITE);

        try {

            AnamnesePodiatry obj = new AnamnesePodiatry();
            obj.setId(ClientDetails.idSaved);
            obj.setNailshape(nailshape);

            if(controller.alterPacientNailShape(obj)){

                rB_normal.setEnabled(false);
                rB_incorreto.setEnabled(false);
                rB_involuta.setEnabled(false);
                rB_telha.setEnabled(false);
                rB_funil.setEnabled(false);
                rB_caracol.setEnabled(false);
                rB_cunha.setEnabled(false);
                rB_gancho.setEnabled(false);

                fab_customer_profile_check = true;

                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));

                fab_customer_profile_check = true;

            }else{

                UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

            }

        }catch (Exception e){

            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

        }

    }

    private void setInfoNailShape(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            if(anamnesePodiatry.getNailshape() == 0) {

                rB_normal.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 1){

                rB_incorreto.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 2){

                rB_involuta.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 3){

                rB_telha.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 4){

                rB_funil.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 5){

                rB_caracol.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 6){

                rB_cunha.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() == 7){

                rB_gancho.setChecked(true);

            }else if(anamnesePodiatry.getNailshape() > 7){

                nailshape = anamnesePodiatry.getNailshape();

                ((RadioButton)radioGroup.findViewById(nailshape)).setChecked(true);

                if(rB_normal.isChecked()){
                    nailshape = 0;
                }else if(rB_incorreto.isChecked()){
                    nailshape = 1;
                }if(rB_involuta.isChecked()){
                    nailshape = 2;
                }if(rB_telha.isChecked()){
                    nailshape = 3;
                }if(rB_funil.isChecked()){
                    nailshape = 4;
                }if(rB_caracol.isChecked()){
                    nailshape = 5;
                }if(rB_cunha.isChecked()){
                    nailshape = 6;
                }if(rB_gancho.isChecked()){
                    nailshape = 7;
                }

                saveNailShape();

            }

        }

    }
}
