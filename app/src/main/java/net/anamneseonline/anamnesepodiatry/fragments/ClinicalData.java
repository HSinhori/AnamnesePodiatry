package net.anamneseonline.anamnesepodiatry.fragments;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.List;

public class ClinicalData extends Fragment {

    View view;

    Switch sdiabetes, sallergies, ssmoking, salcoholic, sophthalmology, scardiovascular, srenal;

    FloatingActionButton fabEditClinical;

    EditText edit_diabetes_type, edit_diabetes_time, edit_customer_allergies, edit_others_clin;

    private boolean fab_customer_profile_check;

    AnamnesePodiatryController controller;

    private int intDiabetes, intAllergies, intSmoking, intAlcoholic, intOphthalmology, intCardiovascular, intRenal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_clinical_data, container, false);

        controller = new AnamnesePodiatryController(getContext());

        sdiabetes = view.findViewById(R.id.switch_diabetes);
        sallergies = view.findViewById(R.id.switch_allergies);
        ssmoking = view.findViewById(R.id.switch_smoking);
        salcoholic = view.findViewById(R.id.switch_alcoholic);
        sophthalmology = view.findViewById(R.id.switch_ophthalmology);
        scardiovascular = view.findViewById(R.id.switch_cardiovascular);
        srenal = view.findViewById(R.id.switch_renal);

        edit_diabetes_time = view.findViewById(R.id.edit_diabetes_time);
        edit_diabetes_type = view.findViewById(R.id.edit_diabetes_type);
        edit_customer_allergies = view.findViewById(R.id.edit_customer_allergies);
        edit_others_clin = view.findViewById(R.id.edit_clinical_others);

        fab_customer_profile_check = true;

        setInfoClinical();

        fabEditClinical = view.findViewById(R.id.fabEditClinical);
        fabEditClinical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fabEditClinical.setImageResource(android.R.drawable.ic_menu_save);
                    fabEditClinical.setColorFilter(Color.WHITE);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));
                    fab_customer_profile_check = false;

                    sdiabetes.setEnabled(true);

                    if(sdiabetes.isChecked()) {

                        edit_diabetes_time.setEnabled(true);
                        edit_diabetes_type.setEnabled(true);

                    }
                    sallergies.setEnabled(true);

                    if(sallergies.isChecked()) {

                        edit_customer_allergies.setEnabled(true);

                    }
                    ssmoking.setEnabled(true);
                    salcoholic.setEnabled(true);
                    sophthalmology.setEnabled(true);
                    scardiovascular.setEnabled(true);
                    srenal.setEnabled(true);
                    edit_others_clin.setEnabled(true);

                }else{

                    fabEditClinical.setImageResource(android.R.drawable.ic_menu_edit);
                    fabEditClinical.setColorFilter(Color.WHITE);

                    try {

                        AnamnesePodiatry obj = new AnamnesePodiatry();
                        obj.setId(ClientDetails.idSaved);
                        obj.setDiabetes(intDiabetes);
                        obj.setDiabetestype(edit_diabetes_type.getText().toString());
                        obj.setDiabetestime(edit_diabetes_time.getText().toString());
                        obj.setAllergies(intAllergies);
                        obj.setAllergieswhat(edit_customer_allergies.getText().toString());
                        obj.setSmoking(intSmoking);
                        obj.setAlcoholic(intAlcoholic);
                        obj.setOphtahlm(intOphthalmology);
                        obj.setCardiov(intCardiovascular);
                        obj.setRenal(intRenal);
                        obj.setOthersclin(edit_others_clin.getText().toString());

                        if(controller.alterPacientClinical(obj)){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));
                            fab_customer_profile_check = true;

                            sdiabetes.setEnabled(false);
                            edit_diabetes_type.setEnabled(false);
                            edit_diabetes_time.setEnabled(false);
                            sallergies.setEnabled(false);
                            edit_customer_allergies.setEnabled(false);
                            ssmoking.setEnabled(false);
                            salcoholic.setEnabled(false);
                            sophthalmology.setEnabled(false);
                            scardiovascular.setEnabled(false);
                            srenal.setEnabled(false);
                            edit_others_clin.setEnabled(false);

                        }else{

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                        }

                    }catch (Exception e){

                        UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                    }
                }
            }
        });

        sdiabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdiabetes.isChecked()){

                    edit_diabetes_time.setEnabled(true);
                    edit_diabetes_type.setEnabled(true);
                    sdiabetes.setTextColor(Color.RED);
                    intDiabetes = 1;

                }else{

                    edit_diabetes_time.setEnabled(false);
                    edit_diabetes_type.setEnabled(false);
                    sdiabetes.setTextColor(getResources().getColor(R.color.colorPrimary));
                    intDiabetes = 0;

                    edit_diabetes_time.setText("");
                    edit_diabetes_type.setText("");

                }

            }
        });

        sallergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sallergies.isChecked()){

                    edit_customer_allergies.setEnabled(true);
                    sallergies.setTextColor(Color.RED);
                    intAllergies = 1;

                }else{

                    edit_customer_allergies.setEnabled(false);
                    sallergies.setTextColor(getResources().getColor(R.color.colorPrimary));
                    edit_customer_allergies.setText("");
                    intAllergies = 0;

                }

            }
        });

        ssmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ssmoking.isChecked()){

                    intSmoking = 1;
                    ssmoking.setTextColor(Color.RED);

                }else{

                    intSmoking = 0;
                    ssmoking.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        salcoholic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(salcoholic.isChecked()){

                    intAlcoholic = 1;
                    salcoholic.setTextColor(Color.RED);

                }else{

                    intAlcoholic = 0;
                    salcoholic.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        sophthalmology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sophthalmology.isChecked()){

                    intOphthalmology = 1;
                    sophthalmology.setTextColor(Color.RED);

                }else{

                    intOphthalmology = 0;
                    sophthalmology.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        scardiovascular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(scardiovascular.isChecked()){

                    intCardiovascular = 1;
                    scardiovascular.setTextColor(Color.RED);

                }else{

                    intCardiovascular = 0;
                    scardiovascular.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        srenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(srenal.isChecked()){

                    intRenal = 1;
                    srenal.setTextColor(Color.RED);

                }else{

                    intRenal = 0;
                    srenal.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        return view;
    }

    private void setInfoClinical(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            if(anamnesePodiatry.getDiabetes() == 1) {

                sdiabetes.setChecked(true);
                sdiabetes.setTextColor(Color.RED);
                intDiabetes = 1;

            }

            if(anamnesePodiatry.getDiabetestype() != null && anamnesePodiatry.getDiabetes() == 1){

                edit_diabetes_type.setText(anamnesePodiatry.getDiabetestype());

            }

            if(anamnesePodiatry.getDiabetestime() != null && anamnesePodiatry.getDiabetes() == 1){

                edit_diabetes_time.setText(anamnesePodiatry.getDiabetestime());

            }

            if(anamnesePodiatry.getAllergies() == 1) {

                sallergies.setChecked(true);
                sallergies.setTextColor(Color.RED);
                intAllergies = 1;

            }

            if(anamnesePodiatry.getAllergieswhat() != null && anamnesePodiatry.getAllergies() == 1){

                edit_customer_allergies.setText(anamnesePodiatry.getAllergieswhat());

            }

            if(anamnesePodiatry.getSmoking() == 1) {

                ssmoking.setChecked(true);
                ssmoking.setTextColor(Color.RED);
                intSmoking = 1;

            }

            if(anamnesePodiatry.getAlcoholic() == 1) {

                salcoholic.setChecked(true);
                salcoholic.setTextColor(Color.RED);
                intAlcoholic = 1;

            }

            if(anamnesePodiatry.getOphtahlm() == 1) {

                sophthalmology.setChecked(true);
                sophthalmology.setTextColor(Color.RED);
                intOphthalmology = 1;

            }

            if(anamnesePodiatry.getCardiov() == 1) {

                scardiovascular.setChecked(true);
                scardiovascular.setTextColor(Color.RED);
                intCardiovascular = 1;

            }

            if(anamnesePodiatry.getRenal() == 1) {

                srenal.setChecked(true);
                srenal.setTextColor(Color.RED);
                intRenal = 1;

            }

            if(anamnesePodiatry.getOthersclin() != null){

                edit_others_clin.setText(anamnesePodiatry.getOthersclin());

            }

        }

    }
}
