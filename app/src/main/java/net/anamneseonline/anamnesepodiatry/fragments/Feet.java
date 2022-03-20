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

public class Feet extends Fragment {

    View view;

    FloatingActionButton fabEditFeet;

    AnamnesePodiatryController controller;

    private Switch wart_left, wart_right, cwoc_left, cwoc_right,
            cwc_left, cwc_right, fissures_left, fissures_right,
            keratosis_left, keratosis_right, ringworm_left, ringworm_right,
            ulcer_left, ulcer_right, switch_suitable, switch_hygiene;

    private int inTwart_left, inTwart_right, inTcwoc_left, inTcwoc_right,
            inTcwc_left, inTcwc_right, inTfissures_left, inTfissures_right,
            inTkeratosis_left, inTkeratosis_right, inTringworm_left, inTringworm_right,
            inTulcer_left, inTulcer_right, inTswitch_suitable, inTswitch_hygiene;

    EditText calcTam;

    private boolean fab_customer_profile_check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_feet, container, false);

        controller = new AnamnesePodiatryController(getContext());

        calcTam = view.findViewById(R.id.editCalcTam);

        wart_left = view.findViewById(R.id.switch_feet_plant_wart_left);
        wart_right = view.findViewById(R.id.switch_feet_plant_wart_right);
        cwoc_left = view.findViewById(R.id.switch_feet_cwoc_left);
        cwoc_right = view.findViewById(R.id.switch_feet_cwoc_right);
        cwc_left = view.findViewById(R.id.switch_feet_cwc_left);
        cwc_right = view.findViewById(R.id.switch_feet_cwc_right);
        fissures_left = view.findViewById(R.id.switch_feet_fissures_left);
        fissures_right = view.findViewById(R.id.switch_feet_fissures_right);
        keratosis_left = view.findViewById(R.id.switch_feet_keratosis_left);
        keratosis_right = view.findViewById(R.id.switch_feet_keratosis_right);
        ringworm_left = view.findViewById(R.id.switch_feet_ringworm_left);
        ringworm_right = view.findViewById(R.id.switch_feet_ringworm_right);
        ulcer_left = view.findViewById(R.id.switch_feet_ulcer_left);
        ulcer_right = view.findViewById(R.id.switch_feet_ulcer_right);
        switch_suitable = view.findViewById(R.id.switch_suitable);
        switch_hygiene = view.findViewById(R.id.switch_hygiene);

        fab_customer_profile_check = true;

        setInfoFeet();

        fabEditFeet = view.findViewById(R.id.fabEditFeet);
        fabEditFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fabEditFeet.setImageResource(android.R.drawable.ic_menu_save);
                    fabEditFeet.setColorFilter(Color.WHITE);

                    wart_left.setEnabled(true);
                    wart_right.setEnabled(true);
                    cwc_left.setEnabled(true);
                    cwc_right.setEnabled(true);
                    cwoc_left.setEnabled(true);
                    cwoc_right.setEnabled(true);
                    fissures_left.setEnabled(true);
                    fissures_right.setEnabled(true);
                    keratosis_left.setEnabled(true);
                    keratosis_right.setEnabled(true);
                    ringworm_left.setEnabled(true);
                    ringworm_right.setEnabled(true);
                    ulcer_left.setEnabled(true);
                    ulcer_right.setEnabled(true);
                    calcTam.setEnabled(true);
                    switch_suitable.setEnabled(true);
                    switch_hygiene.setEnabled(true);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));

                    fab_customer_profile_check = false;

                }else{

                    fabEditFeet.setImageResource(android.R.drawable.ic_menu_edit);
                    fabEditFeet.setColorFilter(Color.WHITE);

                    try {

                        AnamnesePodiatry obj = new AnamnesePodiatry();
                        obj.setId(ClientDetails.idSaved);
                        obj.setWartleft(inTwart_left);
                        obj.setCwcleft(inTcwc_left);
                        obj.setCwocleft(inTcwoc_left);
                        obj.setFissuresleft(inTfissures_left);
                        obj.setKeratosisleft(inTkeratosis_left);
                        obj.setRingwormleft(inTringworm_left);
                        obj.setUlcerleft(inTulcer_left);
                        obj.setWartright(inTwart_right);
                        obj.setCwcright(inTcwc_right);
                        obj.setCwocright(inTcwoc_right);
                        obj.setFissuresright(inTfissures_right);
                        obj.setKeratosisright(inTkeratosis_right);
                        obj.setRingwormright(inTringworm_right);
                        obj.setUlcerright(inTulcer_right);
                        if(calcTam.getText().toString().equals("") || Integer.valueOf(calcTam.getText().toString()) < 0){
                            obj.setFootwearnum(0);
                        }else{
                            obj.setFootwearnum(Integer.parseInt(calcTam.getText().toString()));
                        }
                        obj.setSuitable(inTswitch_suitable);
                        obj.setHygiene(inTswitch_hygiene);

                        if(controller.alterPacientFeet(obj)){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));
                            wart_left.setEnabled(false);
                            wart_right.setEnabled(false);
                            cwc_left.setEnabled(false);
                            cwc_right.setEnabled(false);
                            cwoc_left.setEnabled(false);
                            cwoc_right.setEnabled(false);
                            fissures_left.setEnabled(false);
                            fissures_right.setEnabled(false);
                            keratosis_left.setEnabled(false);
                            keratosis_right.setEnabled(false);
                            ringworm_left.setEnabled(false);
                            ringworm_right.setEnabled(false);
                            ulcer_left.setEnabled(false);
                            ulcer_right.setEnabled(false);
                            calcTam.setEnabled(false);
                            switch_suitable.setEnabled(false);
                            switch_hygiene.setEnabled(false);

                            fab_customer_profile_check = true;


                        }else{

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                        }

                    }catch (Exception e){

                        UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                    }
                }
            }
        });

        wart_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wart_left.isChecked()){

                    inTwart_left = 1;
                    wart_left.setTextColor(Color.RED);

                }else{

                    inTwart_left = 0;
                    wart_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        cwoc_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cwoc_left.isChecked()){

                    inTcwoc_left = 1;
                    cwoc_left.setTextColor(Color.RED);

                }else{

                    inTcwoc_left = 0;
                    cwoc_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        cwc_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cwc_left.isChecked()){

                    inTcwc_left = 1;
                    cwc_left.setTextColor(Color.RED);

                }else{

                    inTcwc_left = 0;
                    cwc_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        fissures_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fissures_left.isChecked()){

                    inTfissures_left = 1;
                    fissures_left.setTextColor(Color.RED);

                }else{

                    inTfissures_left = 0;
                    fissures_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        keratosis_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(keratosis_left.isChecked()){

                    inTkeratosis_left = 1;
                    keratosis_left.setTextColor(Color.RED);

                }else{

                    inTkeratosis_left = 0;
                    keratosis_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        ringworm_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ringworm_left.isChecked()){

                    inTringworm_left = 1;
                    ringworm_left.setTextColor(Color.RED);

                }else{

                    inTringworm_left = 0;
                    ringworm_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        ulcer_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ulcer_left.isChecked()){

                    inTulcer_left = 1;
                    ulcer_left.setTextColor(Color.RED);

                }else{

                    inTulcer_left = 0;
                    ulcer_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        wart_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wart_right.isChecked()){

                    inTwart_right = 1;
                    wart_right.setTextColor(Color.RED);

                }else{

                    inTwart_right = 0;
                    wart_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        cwoc_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cwoc_right.isChecked()){

                    inTcwoc_right = 1;
                    cwoc_right.setTextColor(Color.RED);

                }else{

                    inTcwoc_right = 0;
                    cwoc_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        cwc_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cwc_right.isChecked()){

                    inTcwc_right = 1;
                    cwc_right.setTextColor(Color.RED);

                }else{

                    inTcwc_right = 0;
                    cwc_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        fissures_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fissures_right.isChecked()){

                    inTfissures_right = 1;
                    fissures_right.setTextColor(Color.RED);

                }else{

                    inTfissures_right = 0;
                    fissures_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        keratosis_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(keratosis_right.isChecked()){

                    inTkeratosis_right = 1;
                    keratosis_right.setTextColor(Color.RED);

                }else{

                    inTkeratosis_right = 0;
                    keratosis_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        ringworm_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ringworm_right.isChecked()){

                    inTringworm_right = 1;
                    ringworm_right.setTextColor(Color.RED);

                }else{

                    inTringworm_right = 0;
                    ringworm_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        ulcer_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ulcer_right.isChecked()){

                    inTulcer_right = 1;
                    ulcer_right.setTextColor(Color.RED);

                }else{

                    inTulcer_right = 0;
                    ulcer_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_suitable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_suitable.isChecked()){

                    inTswitch_suitable = 1;
                    switch_suitable.setTextColor(getResources().getColor(R.color.colorPrimary));

                }else{

                    inTswitch_suitable = 0;
                    switch_suitable.setTextColor(Color.RED);

                }

            }
        });

        switch_hygiene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hygiene.isChecked()){

                    inTswitch_hygiene = 1;
                    switch_hygiene.setTextColor(getResources().getColor(R.color.colorPrimary));

                }else{

                    inTswitch_hygiene = 0;
                    switch_hygiene.setTextColor(Color.RED);

                }

            }
        });

        return view;
    }

    private void setInfoFeet(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            if(anamnesePodiatry.getWartleft() == 1) {

                wart_left.setChecked(true);
                wart_left.setTextColor(Color.RED);
                inTwart_left = 1;

            }

            if(anamnesePodiatry.getCwocleft() == 1) {

                cwoc_left.setChecked(true);
                cwoc_left.setTextColor(Color.RED);
                inTcwoc_left = 1;

            }

            if(anamnesePodiatry.getCwcleft() == 1) {

                cwc_left.setChecked(true);
                cwc_left.setTextColor(Color.RED);
                inTcwc_left = 1;

            }

            if(anamnesePodiatry.getFissuresleft() == 1) {

                fissures_left.setChecked(true);
                fissures_left.setTextColor(Color.RED);
                inTfissures_left = 1;

            }

            if(anamnesePodiatry.getKeratosisleft() == 1) {

                keratosis_left.setChecked(true);
                keratosis_left.setTextColor(Color.RED);
                inTkeratosis_left = 1;

            }

            if(anamnesePodiatry.getRingwormleft() == 1) {

                ringworm_left.setChecked(true);
                ringworm_left.setTextColor(Color.RED);
                inTringworm_left = 1;

            }

            if(anamnesePodiatry.getUlcerleft() == 1) {

                ulcer_left.setChecked(true);
                ulcer_left.setTextColor(Color.RED);
                inTulcer_left = 1;

            }

            if(anamnesePodiatry.getWartright() == 1) {

                wart_right.setChecked(true);
                wart_right.setTextColor(Color.RED);
                inTwart_right = 1;

            }

            if(anamnesePodiatry.getCwocright() == 1) {

                cwoc_right.setChecked(true);
                cwoc_right.setTextColor(Color.RED);
                inTcwoc_right = 1;

            }

            if(anamnesePodiatry.getCwcright() == 1) {

                cwc_right.setChecked(true);
                cwc_right.setTextColor(Color.RED);
                inTcwc_right = 1;

            }

            if(anamnesePodiatry.getFissuresright() == 1) {

                fissures_right.setChecked(true);
                fissures_right.setTextColor(Color.RED);
                inTfissures_right = 1;

            }

            if(anamnesePodiatry.getKeratosisright() == 1) {

                keratosis_right.setChecked(true);
                keratosis_right.setTextColor(Color.RED);
                inTkeratosis_right = 1;

            }

            if(anamnesePodiatry.getRingwormright() == 1) {

                ringworm_right.setChecked(true);
                ringworm_right.setTextColor(Color.RED);
                inTringworm_right = 1;

            }

            if(anamnesePodiatry.getUlcerright() == 1) {

                ulcer_right.setChecked(true);
                ulcer_right.setTextColor(Color.RED);
                inTulcer_right = 1;

            }

            if(anamnesePodiatry.getFootwearnum() > 0){

                calcTam.setText(String.valueOf(anamnesePodiatry.getFootwearnum()));

            }

            if(anamnesePodiatry.getSuitable() == 1) {

                switch_suitable.setChecked(true);
                inTswitch_suitable = 1;

            }else{

                switch_suitable.setTextColor(Color.RED);

            }

            if(anamnesePodiatry.getHygiene() == 1) {

                switch_hygiene.setChecked(true);
                inTswitch_hygiene = 1;

            }else{

                switch_hygiene.setTextColor(Color.RED);

            }

        }

    }
}