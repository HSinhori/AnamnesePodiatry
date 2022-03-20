package net.anamneseonline.anamnesepodiatry.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.List;

public class Hands extends Fragment {

    View view;

    FloatingActionButton fabEditHands;

    AnamnesePodiatryController controller;

    private Switch switch_hands_palmar_left, switch_hands_keratosis_left,
            switch_hands_onychocriptoses_left, switch_hands_onychomycosis_left,
            switch_hands_pyo_left, switch_hands_palmar_right, switch_hands_keratosis_right,
            switch_hands_onychocriptoses_right, switch_hands_onychomycosis_right,
            switch_hands_pyo_right;

    private int int_palmar_left, int_keratosis_left,
            int_onychocriptoses_left, int_onychomycosis_left,
            int_pyo_left, int_palmar_right, int_keratosis_right,
            int_onychocriptoses_right, int_onychomycosis_right,
            int_pyo_right;

    private boolean fab_hands_check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hands, container, false);

        controller = new AnamnesePodiatryController(getContext());

        switch_hands_palmar_left = view.findViewById(R.id.switch_hand_palmar_wart_left);
        switch_hands_palmar_right = view.findViewById(R.id.switch_hand_palmar_wart_right);
        switch_hands_keratosis_left = view.findViewById(R.id.switch_hand_keratosis_left);
        switch_hands_keratosis_right = view.findViewById(R.id.switch_hand_keratosis_right);
        switch_hands_onychocriptoses_left = view.findViewById(R.id.switch_hands_onychocriptoses_left);
        switch_hands_onychocriptoses_right = view.findViewById(R.id.switch_hands_onychocriptoses_right);
        switch_hands_onychomycosis_left = view.findViewById(R.id.switch_hands_onychomycosis_left);
        switch_hands_onychomycosis_right = view.findViewById(R.id.switch_hands_onychomycosis_right);
        switch_hands_pyo_left = view.findViewById(R.id.switch_hands_pyo_left);
        switch_hands_pyo_right = view.findViewById(R.id.switch_hands_pyo_right);

        fabEditHands = view.findViewById(R.id.fabEditHands);

        fab_hands_check = true;

        setInfoHands();

        fabEditHands = view.findViewById(R.id.fabEditHands);
        fabEditHands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_hands_check) {

                    fabEditHands.setImageResource(android.R.drawable.ic_menu_save);
                    fabEditHands.setColorFilter(Color.WHITE);

                    switch_hands_palmar_left.setEnabled(true);
                    switch_hands_palmar_right.setEnabled(true);
                    switch_hands_keratosis_left.setEnabled(true);
                    switch_hands_keratosis_right.setEnabled(true);
                    switch_hands_onychocriptoses_left.setEnabled(true);
                    switch_hands_onychocriptoses_right.setEnabled(true);
                    switch_hands_onychomycosis_left.setEnabled(true);
                    switch_hands_onychomycosis_right.setEnabled(true);
                    switch_hands_pyo_left.setEnabled(true);
                    switch_hands_pyo_right.setEnabled(true);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));

                    fab_hands_check = false;

                }else{

                    fabEditHands.setImageResource(android.R.drawable.ic_menu_edit);
                    fabEditHands.setColorFilter(Color.WHITE);

                    try {

                        AnamnesePodiatry obj = new AnamnesePodiatry();
                        obj.setId(ClientDetails.idSaved);
                        obj.setPalmarleft(int_palmar_left);
                        obj.setPalmarright(int_palmar_right);
                        obj.setHandkeraleft(int_keratosis_left);
                        obj.setHandkeraright(int_keratosis_right);
                        obj.setHandonycholeft(int_onychocriptoses_left);
                        obj.setHandonychoright(int_onychocriptoses_right);
                        obj.setHandonychomyleft(int_onychomycosis_left);
                        obj.setHandonychomyright(int_onychomycosis_right);
                        obj.setPyoleft(int_pyo_left);
                        obj.setPyoright(int_pyo_right);

                        if(controller.alterPacientHands(obj)){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));

                            switch_hands_palmar_left.setEnabled(false);
                            switch_hands_palmar_right.setEnabled(false);
                            switch_hands_keratosis_left.setEnabled(false);
                            switch_hands_keratosis_right.setEnabled(false);
                            switch_hands_onychocriptoses_left.setEnabled(false);
                            switch_hands_onychocriptoses_right.setEnabled(false);
                            switch_hands_onychomycosis_left.setEnabled(false);
                            switch_hands_onychomycosis_right.setEnabled(false);
                            switch_hands_pyo_left.setEnabled(false);
                            switch_hands_pyo_right.setEnabled(false);

                            fab_hands_check = true;


                        }else{

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                        }

                    }catch (Exception e){

                        UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.cant_save_changes));

                    }
                }
            }
        });

        switch_hands_palmar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_palmar_left.isChecked()){

                    int_palmar_left = 1;
                    switch_hands_palmar_left.setTextColor(Color.RED);

                }else{

                    int_palmar_left = 0;
                    switch_hands_palmar_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_palmar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_palmar_right.isChecked()){

                    int_palmar_right = 1;
                    switch_hands_palmar_right.setTextColor(Color.RED);

                }else{

                    int_palmar_right = 0;
                    switch_hands_palmar_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_keratosis_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_keratosis_left.isChecked()){

                    int_keratosis_left = 1;
                    switch_hands_keratosis_left.setTextColor(Color.RED);

                }else{

                    int_keratosis_left = 0;
                    switch_hands_keratosis_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_keratosis_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_keratosis_right.isChecked()){

                    int_keratosis_right = 1;
                    switch_hands_keratosis_right.setTextColor(Color.RED);

                }else{

                    int_keratosis_right = 0;
                    switch_hands_keratosis_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_onychocriptoses_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_onychocriptoses_left.isChecked()){

                    int_onychocriptoses_left = 1;
                    switch_hands_onychocriptoses_left.setTextColor(Color.RED);

                }else{

                    int_onychocriptoses_left = 0;
                    switch_hands_onychocriptoses_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_onychocriptoses_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_onychocriptoses_right.isChecked()){

                    int_onychocriptoses_right = 1;
                    switch_hands_onychocriptoses_right.setTextColor(Color.RED);

                }else{

                    int_onychocriptoses_right = 0;
                    switch_hands_onychocriptoses_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_onychomycosis_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_onychomycosis_left.isChecked()){

                    int_onychomycosis_left = 1;
                    switch_hands_onychomycosis_left.setTextColor(Color.RED);

                }else{

                    int_onychomycosis_left = 0;
                    switch_hands_onychomycosis_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_onychomycosis_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_onychomycosis_right.isChecked()){

                    int_onychomycosis_right = 1;
                    switch_hands_onychomycosis_right.setTextColor(Color.RED);

                }else{

                    int_onychomycosis_right = 0;
                    switch_hands_onychomycosis_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_pyo_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_pyo_left.isChecked()){

                    int_pyo_left = 1;
                    switch_hands_pyo_left.setTextColor(Color.RED);

                }else{

                    int_pyo_left = 0;
                    switch_hands_pyo_left.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        switch_hands_pyo_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switch_hands_pyo_right.isChecked()){

                    int_pyo_right = 1;
                    switch_hands_pyo_right.setTextColor(Color.RED);

                }else{

                    int_pyo_right = 0;
                    switch_hands_pyo_right.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        return view;
    }

    private void setInfoHands(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            if(anamnesePodiatry.getPalmarleft() == 1) {

                switch_hands_palmar_left.setChecked(true);
                switch_hands_palmar_left.setTextColor(Color.RED);
                int_palmar_left = 1;

            }

            if(anamnesePodiatry.getPalmarright() == 1) {

                switch_hands_palmar_right.setChecked(true);
                switch_hands_palmar_right.setTextColor(Color.RED);
                int_palmar_right = 1;

            }

            if(anamnesePodiatry.getHandkeraleft() == 1) {

                switch_hands_keratosis_left.setChecked(true);
                switch_hands_keratosis_left.setTextColor(Color.RED);
                int_keratosis_left = 1;

            }

            if(anamnesePodiatry.getHandkeraright() == 1) {

                switch_hands_keratosis_right.setChecked(true);
                switch_hands_keratosis_right.setTextColor(Color.RED);
                int_keratosis_right = 1;

            }

            if(anamnesePodiatry.getHandonycholeft() == 1) {

                switch_hands_onychocriptoses_left.setChecked(true);
                switch_hands_onychocriptoses_left.setTextColor(Color.RED);
                int_onychocriptoses_left = 1;

            }

            if(anamnesePodiatry.getHandonychoright() == 1) {

                switch_hands_onychocriptoses_right.setChecked(true);
                switch_hands_onychocriptoses_right.setTextColor(Color.RED);
                int_onychocriptoses_right = 1;

            }

            if(anamnesePodiatry.getHandonychomyleft() == 1) {

                switch_hands_onychomycosis_left.setChecked(true);
                switch_hands_onychomycosis_left.setTextColor(Color.RED);
                int_onychomycosis_left = 1;

            }

            if(anamnesePodiatry.getHandonychomyright() == 1) {

                switch_hands_onychomycosis_right.setChecked(true);
                switch_hands_onychomycosis_right.setTextColor(Color.RED);
                int_onychomycosis_right = 1;

            }

            if(anamnesePodiatry.getPyoleft() == 1) {

                switch_hands_pyo_left.setChecked(true);
                switch_hands_pyo_left.setTextColor(Color.RED);
                int_pyo_left = 1;

            }

            if(anamnesePodiatry.getPyoright() == 1) {

                switch_hands_pyo_right.setChecked(true);
                switch_hands_pyo_right.setTextColor(Color.RED);
                int_pyo_right = 1;

            }

        }

    }
}