package net.anamneseonline.anamnesepodiatry.fragments;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.List;

public class Perfusion extends Fragment {

    View view;

    FloatingActionButton fabEditPerf;

    Switch cyanotic, resected, oily, fine, swollen, bromidose,
            anhidrosis, hyperhi, plantar, cavys, flat, halux_valgus,
            varus, bunion, cn, sn, callus, crack,
            keratosis, wart, onychomycosis, onicocryptosis, ref1001B, ref1001R;

    private int intCyanotic, intResected, intOily, intFine, intSwollen, intBromidose,
            intAhnidrosis, intHyperhi, intPlantar, intCavys, intFlat, intHalux_valgus,
            intVarus, intBunion, intCn, intSn, intCallus, intCrack,
            intKeratosis, intWart, intOnychomycosis, intOnicocryptosis, intRef1001B, intRef1001R;

    private boolean fab_customer_profile_check;

    AnamnesePodiatryController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_perfusion, container, false);

        controller = new AnamnesePodiatryController(getContext());

        cyanotic = view.findViewById(R.id.cyanotic);
        resected = view.findViewById(R.id.resected);
        oily = view.findViewById(R.id.oily);
        fine = view.findViewById(R.id.fine);
        swollen = view.findViewById(R.id.swollen);
        bromidose = view.findViewById(R.id.bromidose);
        anhidrosis = view.findViewById(R.id.anhidrosis);
        hyperhi = view.findViewById(R.id.hyperhi);
        plantar = view.findViewById(R.id.plantar);
        cavys = view.findViewById(R.id.cavys);
        flat = view.findViewById(R.id.flat);
        halux_valgus = view.findViewById(R.id.halux_valgus);
        varus = view.findViewById(R.id.varus);
        bunion = view.findViewById(R.id.bunion);
        cn = view.findViewById(R.id.cn);
        sn = view.findViewById(R.id.sn);
        callus = view.findViewById(R.id.callus);
        crack = view.findViewById(R.id.crack);
        keratosis = view.findViewById(R.id.keratosis);
        wart = view.findViewById(R.id.wart);
        onychomycosis = view.findViewById(R.id.onychomycosis);
        onicocryptosis = view.findViewById(R.id.onicocryptosis);
        ref1001B = view.findViewById(R.id.ref1001B);
        ref1001R = view.findViewById(R.id.ref1001R);

        fab_customer_profile_check = true;

        setInfoPerfusion();



        fabEditPerf = view.findViewById(R.id.fabEditPerf);
        fabEditPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fabEditPerf.setImageResource(android.R.drawable.ic_menu_save);
                    fabEditPerf.setColorFilter(Color.WHITE);

                    cyanotic.setEnabled(true);
                    resected.setEnabled(true);
                    oily.setEnabled(true);
                    fine.setEnabled(true);
                    swollen.setEnabled(true);
                    bromidose.setEnabled(true);
                    anhidrosis.setEnabled(true);
                    hyperhi.setEnabled(true);
                    plantar.setEnabled(true);
                    cavys.setEnabled(true);
                    flat.setEnabled(true);
                    halux_valgus.setEnabled(true);
                    varus.setEnabled(true);
                    bunion.setEnabled(true);
                    cn.setEnabled(true);
                    sn.setEnabled(true);
                    callus.setEnabled(true);
                    crack.setEnabled(true);
                    keratosis.setEnabled(true);
                    wart.setEnabled(true);
                    onychomycosis.setEnabled(true);
                    onicocryptosis.setEnabled(true);
                    ref1001B.setEnabled(true);
                    ref1001R.setEnabled(true);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));

                    fab_customer_profile_check = false;

                }else{

                    fabEditPerf.setImageResource(android.R.drawable.ic_menu_edit);
                    fabEditPerf.setColorFilter(Color.WHITE);

                    try {

                        AnamnesePodiatry obj = new AnamnesePodiatry();
                        obj.setId(ClientDetails.idSaved);
                        obj.setCyanotic(intCyanotic);
                        obj.setResected(intResected);
                        obj.setOily(intOily);
                        obj.setFine(intFine);
                        obj.setSwollen(intSwollen);
                        obj.setBromidose(intBromidose);
                        obj.setAhnidrosis(intAhnidrosis);
                        obj.setHyperhi(intHyperhi);
                        obj.setPlantar(intPlantar);
                        obj.setCavys(intCavys);
                        obj.setFlat(intFlat);
                        obj.setHaluxvalgus(intHalux_valgus);
                        obj.setVarus(intVarus);
                        obj.setBunion(intBunion);
                        obj.setCn(intCn);
                        obj.setSn(intSn);
                        obj.setCallus(intCallus);
                        obj.setCrack(intCrack);
                        obj.setKeratosis(intKeratosis);
                        obj.setWart(intWart);
                        obj.setOnychomycosis(intOnychomycosis);
                        obj.setOnicocryptosis(intOnicocryptosis);
                        obj.setRef1001B(intRef1001B);
                        obj.setRef1001R(intRef1001R);

                        if(controller.alterPacientPerfusion(obj)){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));

                            cyanotic.setEnabled(false);
                            resected.setEnabled(false);
                            oily.setEnabled(false);
                            fine.setEnabled(false);
                            swollen.setEnabled(false);
                            bromidose.setEnabled(false);
                            anhidrosis.setEnabled(false);
                            hyperhi.setEnabled(false);
                            plantar.setEnabled(false);
                            cavys.setEnabled(false);
                            flat.setEnabled(false);
                            halux_valgus.setEnabled(false);
                            varus.setEnabled(false);
                            bunion.setEnabled(false);
                            cn.setEnabled(false);
                            sn.setEnabled(false);
                            callus.setEnabled(false);
                            crack.setEnabled(false);
                            keratosis.setEnabled(false);
                            wart.setEnabled(false);
                            onychomycosis.setEnabled(false);
                            onicocryptosis.setEnabled(false);
                            ref1001B.setEnabled(false);
                            ref1001R.setEnabled(false);

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

        cyanotic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cyanotic.isChecked()){
                    intCyanotic = 1;
                    cyanotic.setTextColor(Color.RED);
                }else{
                    intCyanotic = 0;
                    cyanotic.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        resected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resected.isChecked()){
                    intResected = 1;
                    resected.setTextColor(Color.RED);
                }else{
                    intResected = 0;
                    resected.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        oily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(oily.isChecked()){
                    intOily = 1;
                    oily.setTextColor(Color.RED);
                }else{
                    intOily = 0;
                    oily.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        fine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fine.isChecked()){
                    intFine = 1;
                    fine.setTextColor(Color.RED);
                }else{
                    intFine = 0;
                    fine.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        swollen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(swollen.isChecked()){
                    intSwollen = 1;
                    swollen.setTextColor(Color.RED);
                }else{
                    intSwollen = 0;
                    swollen.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        bromidose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bromidose.isChecked()){
                    intBromidose = 1;
                    bromidose.setTextColor(Color.RED);
                }else{
                    intBromidose = 0;
                    bromidose.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        anhidrosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(anhidrosis.isChecked()){
                    intAhnidrosis = 1;
                    anhidrosis.setTextColor(Color.RED);
                }else{
                    intAhnidrosis = 0;
                    anhidrosis.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        hyperhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hyperhi.isChecked()){
                    intHyperhi = 1;
                    hyperhi.setTextColor(Color.RED);
                }else{
                    intHyperhi = 0;
                    hyperhi.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        plantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(plantar.isChecked()){
                    intPlantar = 1;
                    plantar.setTextColor(Color.RED);
                }else{
                    intPlantar = 0;
                    plantar.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        cavys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cavys.isChecked()){
                    intCavys = 1;
                    cavys.setTextColor(Color.RED);
                }else{
                    intCavys = 0;
                    cavys.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flat.isChecked()){
                    intFlat = 1;
                    flat.setTextColor(Color.RED);
                }else{
                    intFlat = 0;
                    flat.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        halux_valgus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(halux_valgus.isChecked()){
                    intHalux_valgus = 1;
                    halux_valgus.setTextColor(Color.RED);
                }else{
                    intHalux_valgus = 0;
                    halux_valgus.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        varus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(varus.isChecked()){
                    intVarus = 1;
                    varus.setTextColor(Color.RED);
                }else{
                    intVarus = 0;
                    varus.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        bunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bunion.isChecked()){
                    intBunion = 1;
                    bunion.setTextColor(Color.RED);
                }else{
                    intBunion = 0;
                    bunion.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cn.isChecked()){
                    intCn = 1;
                    cn.setTextColor(Color.RED);
                }else{
                    intCn = 0;
                    cn.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sn.isChecked()){
                    intSn = 1;
                    sn.setTextColor(Color.RED);
                }else{
                    intSn = 0;
                    sn.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(callus.isChecked()){
                    intCallus = 1;
                    callus.setTextColor(Color.RED);
                }else{
                    intCallus = 0;
                    callus.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        crack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(crack.isChecked()){
                    intCrack = 1;
                    crack.setTextColor(Color.RED);
                }else{
                    intCrack = 0;
                    crack.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        keratosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(keratosis.isChecked()){
                    intKeratosis = 1;
                    keratosis.setTextColor(Color.RED);
                }else{
                    intKeratosis = 0;
                    keratosis.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        wart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wart.isChecked()){
                    intWart = 1;
                    wart.setTextColor(Color.RED);
                }else{
                    intWart = 0;
                    wart.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        onychomycosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onychomycosis.isChecked()){
                    intOnychomycosis = 1;
                    onychomycosis.setTextColor(Color.RED);
                }else{
                    intOnychomycosis = 0;
                    onychomycosis.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        onicocryptosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onicocryptosis.isChecked()){
                    intOnicocryptosis = 1;
                    onicocryptosis.setTextColor(Color.RED);
                }else{
                    intOnicocryptosis = 0;
                    onicocryptosis.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        ref1001B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ref1001B.isChecked()){
                    intRef1001B = 1;
                    ref1001B.setTextColor(Color.RED);
                }else{
                    intRef1001B = 0;
                    ref1001B.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        ref1001R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ref1001R.isChecked()){
                    intRef1001R = 1;
                    ref1001R.setTextColor(Color.RED);
                }else{
                    intRef1001R = 0;
                    ref1001R.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });

        return view;
    }


    private void setInfoPerfusion() {

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for (AnamnesePodiatry anamnesePodiatry : PacientProfileList) {

            if (anamnesePodiatry.getCyanotic() == 1) {

                cyanotic.setChecked(true);
                cyanotic.setTextColor(Color.RED);
                intCyanotic = 1;

            }

            if (anamnesePodiatry.getResected() == 1) {

                resected.setChecked(true);
                resected.setTextColor(Color.RED);
                intResected = 1;

            }

            if (anamnesePodiatry.getOily() == 1) {

                oily.setChecked(true);
                oily.setTextColor(Color.RED);
                intOily = 1;

            }

            if (anamnesePodiatry.getFine() == 1) {

                fine.setChecked(true);
                fine.setTextColor(Color.RED);
                intFine = 1;

            }

            if (anamnesePodiatry.getSwollen() == 1) {

                swollen.setChecked(true);
                swollen.setTextColor(Color.RED);
                intSwollen = 1;

            }

            if (anamnesePodiatry.getBromidose() == 1) {

                bromidose.setChecked(true);
                bromidose.setTextColor(Color.RED);
                intBromidose = 1;

            }

            if (anamnesePodiatry.getAhnidrosis() == 1) {

                anhidrosis.setChecked(true);
                anhidrosis.setTextColor(Color.RED);
                intAhnidrosis = 1;

            }

            if (anamnesePodiatry.getHyperhi() == 1) {

                hyperhi.setChecked(true);
                hyperhi.setTextColor(Color.RED);
                intHyperhi = 1;

            }

            if (anamnesePodiatry.getPlantar() == 1) {

                plantar.setChecked(true);
                plantar.setTextColor(Color.RED);
                intPlantar = 1;

            }

            if (anamnesePodiatry.getCavys() == 1) {

                cavys.setChecked(true);
                cavys.setTextColor(Color.RED);
                intCavys = 1;

            }

            if (anamnesePodiatry.getFlat() == 1) {

                flat.setChecked(true);
                flat.setTextColor(Color.RED);
                intFlat = 1;

            }

            if (anamnesePodiatry.getHaluxvalgus() == 1) {

                halux_valgus.setChecked(true);
                halux_valgus.setTextColor(Color.RED);
                intHalux_valgus = 1;

            }

            if (anamnesePodiatry.getVarus() == 1) {

                varus.setChecked(true);
                varus.setTextColor(Color.RED);
                intVarus = 1;

            }

            if (anamnesePodiatry.getBunion() == 1) {

                bunion.setChecked(true);
                bunion.setTextColor(Color.RED);
                intBunion = 1;

            }

            if (anamnesePodiatry.getCn() == 1) {

                cn.setChecked(true);
                cn.setTextColor(Color.RED);
                intCn = 1;

            }

            if (anamnesePodiatry.getSn() == 1) {

                sn.setChecked(true);
                sn.setTextColor(Color.RED);
                intSn = 1;

            }

            if (anamnesePodiatry.getCallus() == 1) {

                callus.setChecked(true);
                callus.setTextColor(Color.RED);
                intCallus = 1;

            }

            if (anamnesePodiatry.getCrack() == 1) {

                crack.setChecked(true);
                crack.setTextColor(Color.RED);
                intCrack = 1;

            }

            if (anamnesePodiatry.getKeratosis() == 1) {

                keratosis.setChecked(true);
                keratosis.setTextColor(Color.RED);
                intKeratosis = 1;

            }

            if (anamnesePodiatry.getWart() == 1) {

                wart.setChecked(true);
                wart.setTextColor(Color.RED);
                intWart = 1;

            }

            if (anamnesePodiatry.getOnychomycosis() == 1) {

                onychomycosis.setChecked(true);
                onychomycosis.setTextColor(Color.RED);
                intOnychomycosis = 1;

            }

            if (anamnesePodiatry.getOnicocryptosis() == 1) {

                onicocryptosis.setChecked(true);
                onicocryptosis.setTextColor(Color.RED);
                intOnicocryptosis = 1;

            }

            if (anamnesePodiatry.getRef1001B() == 1) {

                ref1001B.setChecked(true);
                ref1001B.setTextColor(Color.RED);
                intRef1001B = 1;

            }

            if (anamnesePodiatry.getRef1001R() == 1) {

                ref1001R.setChecked(true);
                ref1001R.setTextColor(Color.RED);
                intRef1001R = 1;

            }
        }
    }
}