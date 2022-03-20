package net.anamneseonline.anamnesepodiatry.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.anamneseonline.anamnesepodiatry.R;
import net.anamneseonline.anamnesepodiatry.controller.AnamnesePodiatryController;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;
import net.anamneseonline.anamnesepodiatry.utilAnamnese.UtilAnamnesePodiatry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewClientFragment extends Fragment {

    EditText client_date;
    EditText new_client;
    EditText client_address;
    EditText client_district;
    EditText client_city;
    EditText client_phone;
    EditText client_phone_two;
    EditText client_profession;
    EditText client_gender;
    Button btn_save_new_client;
    String dataAtual;

    View view;

    Context context;

    AnamnesePodiatryController controller;
    AnamnesePodiatry anamnesePodiatry;

    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        controller = new AnamnesePodiatryController(context);

        anamnesePodiatry = new AnamnesePodiatry();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_client, container, false);

        fragmentManager = getFragmentManager();

        getActivity().setTitle(R.string.add_new_client);

        client_date = view.findViewById(R.id.edit_client_date);
        new_client = view.findViewById(R.id.edit_new_client);
        client_address = view.findViewById(R.id.edit_client_address);
        client_district = view.findViewById(R.id.edit_client_district);
        client_city = view.findViewById(R.id.edit_client_city);
        client_phone = view.findViewById(R.id.edit_client_phone);
        client_phone_two = view.findViewById(R.id.edit_client_phone_two);
        client_profession = view.findViewById(R.id.edit_client_profession);
        client_gender = view.findViewById(R.id.edit_client_gender);
        btn_save_new_client = view.findViewById(R.id.btn_save_new_client);

        client_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        client_phone_two.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        try {

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            Date data = new Date();

            dataAtual = df.format(data);



            client_date.setText(dataAtual);

        }catch (Exception e){

            client_date.setText(R.string.client_date);

        }

        btn_save_new_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new_client.getText().toString().length() < 3){

                    new_client.setError("*");
                    new_client.requestFocus();

                }else if(client_phone.getText().toString().length() < 8){

                    client_phone.setError("*");
                    client_phone.requestFocus();

                }else{

                    try {

                        anamnesePodiatry.setDate(client_date.getText().toString());
                        anamnesePodiatry.setName(new_client.getText().toString());
                        anamnesePodiatry.setGender(client_gender.getText().toString());
                        anamnesePodiatry.setAddress(client_address.getText().toString());
                        anamnesePodiatry.setCity(client_city.getText().toString());
                        anamnesePodiatry.setDistrict(client_district.getText().toString());
                        anamnesePodiatry.setPhone(client_phone.getText().toString());
                        anamnesePodiatry.setPhonetwo(client_phone_two.getText().toString());
                        anamnesePodiatry.setProfession(client_profession.getText().toString());

                        if(controller.saveNewClient(anamnesePodiatry)){

                            UtilAnamnesePodiatry.showMensagem(context, getString(R.string.saved_succes));
                            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ClientListFragment()).commit();


                        }else{

                            UtilAnamnesePodiatry.showMensagem(context, getString(R.string.cant_save));

                        }
                    }catch(Exception e){

                        UtilAnamnesePodiatry.showMensagem(context, getString(R.string.cant_save));

                    }

                }

            }
        });

        return view;
    }
}
