package net.anamneseonline.anamnesepodiatry.controller;

import android.content.ContentValues;
import android.content.Context;

import net.anamneseonline.anamnesepodiatry.datamodel.AnamnesePodiatryDataModel;
import net.anamneseonline.anamnesepodiatry.datasource.DataSource;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;

import java.util.ArrayList;

public class AnamnesePodiatryController extends DataSource {

    ContentValues dados;

    public AnamnesePodiatryController(Context context) {
        super(context);
    }

    public boolean saveNewImage(AnamnesePodiatry anamnesePodiatry){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getPatientid(), anamnesePodiatry.getPatientid());
        dados.put(AnamnesePodiatryDataModel.getPatientimage(), anamnesePodiatry.getPatientimage());
        dados.put(AnamnesePodiatryDataModel.getImagetxt(), anamnesePodiatry.getImagetxt());
        dados.put(AnamnesePodiatryDataModel.getImagerotate(), anamnesePodiatry.getImagerotate());

        sucesso = insertDB(AnamnesePodiatryDataModel.getPatientImagesTable(), dados);

        return sucesso;
    }

    public boolean deleteImage(AnamnesePodiatry obj){

        boolean sucesso = true;

        sucesso = deleteImg(AnamnesePodiatryDataModel.getPatientImagesTable(), obj.getIdimage());


        return sucesso;
    }

    public boolean deleteAllImages(AnamnesePodiatry obj){

        boolean sucesso = true;

        sucesso = imagesDelete(AnamnesePodiatryDataModel.getPatientImagesTable(), obj.getPatientid());


        return sucesso;
    }

    public boolean saveNewClient(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getDate(), obj.getDate());
        dados.put(AnamnesePodiatryDataModel.getName(), obj.getName());
        dados.put(AnamnesePodiatryDataModel.getGender(), obj.getGender());
        dados.put(AnamnesePodiatryDataModel.getAddress(), obj.getAddress());
        dados.put(AnamnesePodiatryDataModel.getCity(), obj.getCity());
        dados.put(AnamnesePodiatryDataModel.getDistrict(), obj.getDistrict());
        dados.put(AnamnesePodiatryDataModel.getPhone(), obj.getPhone());
        dados.put(AnamnesePodiatryDataModel.getPhonetwo(), obj.getPhonetwo());
        dados.put(AnamnesePodiatryDataModel.getProfession(), obj.getProfession());

        sucesso = insertDB(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean deleteClient(AnamnesePodiatry obj){

        boolean sucesso = true;

        sucesso = delete(AnamnesePodiatryDataModel.getTable(), obj.getId());


        return sucesso;
    }

    public boolean alterPacientProfile(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getDate(), obj.getDate());
        dados.put(AnamnesePodiatryDataModel.getName(), obj.getName());
        dados.put(AnamnesePodiatryDataModel.getGender(), obj.getGender());
        dados.put(AnamnesePodiatryDataModel.getAddress(), obj.getAddress());
        dados.put(AnamnesePodiatryDataModel.getCity(), obj.getCity());
        dados.put(AnamnesePodiatryDataModel.getDistrict(), obj.getDistrict());
        dados.put(AnamnesePodiatryDataModel.getPhone(), obj.getPhone());
        dados.put(AnamnesePodiatryDataModel.getPhonetwo(), obj.getPhonetwo());
        dados.put(AnamnesePodiatryDataModel.getProfession(), obj.getProfession());
        dados.put(AnamnesePodiatryDataModel.getNailshape(), obj.getNailshape());
        dados.put(AnamnesePodiatryDataModel.getImgprofilecustomer(), obj.getImgprofilecustomer());
        dados.put(AnamnesePodiatryDataModel.getImagerotatecostumer(), obj.getImagerotatecostumer());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientClinical(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getDiabetes(), obj.getDiabetes());
        dados.put(AnamnesePodiatryDataModel.getDiabetestype(), obj.getDiabetestype());
        dados.put(AnamnesePodiatryDataModel.getDiabetestime(), obj.getDiabetestime());
        dados.put(AnamnesePodiatryDataModel.getAllergies(), obj.getAllergies());
        dados.put(AnamnesePodiatryDataModel.getAllergieswhat(), obj.getAllergieswhat());
        dados.put(AnamnesePodiatryDataModel.getSmoking(), obj.getSmoking());
        dados.put(AnamnesePodiatryDataModel.getAlcoholic(), obj.getAlcoholic());
        dados.put(AnamnesePodiatryDataModel.getOphtahlm(), obj.getOphtahlm());
        dados.put(AnamnesePodiatryDataModel.getCardiov(), obj.getCardiov());
        dados.put(AnamnesePodiatryDataModel.getRenal(), obj.getRenal());
        dados.put(AnamnesePodiatryDataModel.getOthersclin(), obj.getOthersclin());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientNailShape(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getNailshape(), obj.getNailshape());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientFeet(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getWartleft(), obj.getWartleft());
        dados.put(AnamnesePodiatryDataModel.getWartright(), obj.getWartright());
        dados.put(AnamnesePodiatryDataModel.getCwcleft(), obj.getCwcleft());
        dados.put(AnamnesePodiatryDataModel.getCwcright(), obj.getCwcright());
        dados.put(AnamnesePodiatryDataModel.getCwocleft(), obj.getCwocleft());
        dados.put(AnamnesePodiatryDataModel.getCwocright(), obj.getCwocright());
        dados.put(AnamnesePodiatryDataModel.getFissuresleft(), obj.getFissuresleft());
        dados.put(AnamnesePodiatryDataModel.getFissuresright(), obj.getFissuresright());
        dados.put(AnamnesePodiatryDataModel.getKeratosisleft(), obj.getKeratosisleft());
        dados.put(AnamnesePodiatryDataModel.getKeratosisright(), obj.getKeratosisright());
        dados.put(AnamnesePodiatryDataModel.getRingwormleft(), obj.getRingwormleft());
        dados.put(AnamnesePodiatryDataModel.getRingwormright(), obj.getRingwormright());
        dados.put(AnamnesePodiatryDataModel.getUlcerleft(), obj.getUlcerleft());
        dados.put(AnamnesePodiatryDataModel.getUlcerright(), obj.getUlcerright());
        dados.put(AnamnesePodiatryDataModel.getFootwearnum(), obj.getFootwearnum());
        dados.put(AnamnesePodiatryDataModel.getSuitable(), obj.getSuitable());
        dados.put(AnamnesePodiatryDataModel.getHygiene(), obj.getHygiene());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientHands(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getPalmarleft(), obj.getPalmarleft());
        dados.put(AnamnesePodiatryDataModel.getPalmarright(), obj.getPalmarright());
        dados.put(AnamnesePodiatryDataModel.getHandkeraleft(), obj.getHandkeraleft());
        dados.put(AnamnesePodiatryDataModel.getHandkeraright(), obj.getHandkeraright());
        dados.put(AnamnesePodiatryDataModel.getHandonycholeft(), obj.getHandonycholeft());
        dados.put(AnamnesePodiatryDataModel.getHandonychoright(), obj.getHandonychoright());
        dados.put(AnamnesePodiatryDataModel.getHandonychomyleft(), obj.getHandonychomyleft());
        dados.put(AnamnesePodiatryDataModel.getHandonychomyright(), obj.getHandonychomyright());
        dados.put(AnamnesePodiatryDataModel.getPyoleft(), obj.getPyoleft());
        dados.put(AnamnesePodiatryDataModel.getPyoright(), obj.getPyoright());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientPerfusion(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getCyanotic(), obj.getCyanotic());
        dados.put(AnamnesePodiatryDataModel.getResected(), obj.getResected());
        dados.put(AnamnesePodiatryDataModel.getOily(), obj.getOily());
        dados.put(AnamnesePodiatryDataModel.getFine(), obj.getFine());
        dados.put(AnamnesePodiatryDataModel.getSwollen(), obj.getSwollen());
        dados.put(AnamnesePodiatryDataModel.getBromidose(), obj.getBromidose());
        dados.put(AnamnesePodiatryDataModel.getAhnidrosis(), obj.getAhnidrosis());
        dados.put(AnamnesePodiatryDataModel.getHyperhi(), obj.getHyperhi());
        dados.put(AnamnesePodiatryDataModel.getPlantar(), obj.getPlantar());
        dados.put(AnamnesePodiatryDataModel.getCavys(), obj.getCavys());
        dados.put(AnamnesePodiatryDataModel.getFlat(), obj.getFlat());
        dados.put(AnamnesePodiatryDataModel.getHaluxvalgus(), obj.getHaluxvalgus());
        dados.put(AnamnesePodiatryDataModel.getVarus(), obj.getVarus());
        dados.put(AnamnesePodiatryDataModel.getBunion(), obj.getBunion());
        dados.put(AnamnesePodiatryDataModel.getCn(), obj.getCn());
        dados.put(AnamnesePodiatryDataModel.getSn(), obj.getSn());
        dados.put(AnamnesePodiatryDataModel.getCallus(), obj.getCallus());
        dados.put(AnamnesePodiatryDataModel.getCrack(), obj.getCrack());
        dados.put(AnamnesePodiatryDataModel.getKeratosis(), obj.getKeratosis());
        dados.put(AnamnesePodiatryDataModel.getWart(), obj.getWart());
        dados.put(AnamnesePodiatryDataModel.getOnychomycosis(), obj.getOnychomycosis());
        dados.put(AnamnesePodiatryDataModel.getOnicocryptosis(), obj.getOnicocryptosis());
        dados.put(AnamnesePodiatryDataModel.getRef1001B(), obj.getRef1001B());
        dados.put(AnamnesePodiatryDataModel.getRef1001R(), obj.getRef1001R());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public boolean alterPacientAnnotations(AnamnesePodiatry obj){

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(AnamnesePodiatryDataModel.getId(), obj.getId());
        dados.put(AnamnesePodiatryDataModel.getAnnnotations(), obj.getAnnnotations());

        sucesso = alterPac(AnamnesePodiatryDataModel.getTable(), dados);

        return sucesso;
    }

    public ArrayList<AnamnesePodiatry> list(){
        return getAllClients();
    }

    public ArrayList<AnamnesePodiatry> PacienttProfileList(){
        return getPacienttProfileList();
    }

    public ArrayList<AnamnesePodiatry> images(){
        return getAllImages();
    }
}
