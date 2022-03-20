package net.anamneseonline.anamnesepodiatry.fragments;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.util.List;

public class Annotations extends Fragment {

    FloatingActionButton fabAnnotations;
    View view;
    private boolean fab_customer_profile_check;
    EditText edit_annotations;
    AnamnesePodiatryController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_annotations, container, false);

        fab_customer_profile_check = true;
        controller = new AnamnesePodiatryController(getContext());

        edit_annotations = view.findViewById(R.id.edit_annotations);

        setInfoAnnotations();

        fabAnnotations = view.findViewById(R.id.fabEditAnnotations);
        fabAnnotations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fab_customer_profile_check) {

                    fabAnnotations.setImageResource(android.R.drawable.ic_menu_save);
                    fabAnnotations.setColorFilter(Color.WHITE);
                    edit_annotations.setEnabled(true);

                    UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.not_forget_save));
                    fab_customer_profile_check = false;

                }else{

                    fabAnnotations.setImageResource(android.R.drawable.ic_menu_edit);
                    fabAnnotations.setColorFilter(Color.WHITE);

                    try {

                        AnamnesePodiatry obj = new AnamnesePodiatry();
                        obj.setId(ClientDetails.idSaved);
                        obj.setAnnnotations(edit_annotations.getText().toString());

                        if(controller.alterPacientAnnotations(obj)){

                            UtilAnamnesePodiatry.showMensagem(getContext(), getString(R.string.saved_changes));

                            edit_annotations.setEnabled(false);

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

        return view;
    }

    private void setInfoAnnotations(){

        List<AnamnesePodiatry> PacientProfileList = controller.PacienttProfileList();

        for(AnamnesePodiatry anamnesePodiatry: PacientProfileList){

            if(anamnesePodiatry.getAnnnotations() != null) {

                edit_annotations.setText(anamnesePodiatry.getAnnnotations());

            }

        }

    }
}
