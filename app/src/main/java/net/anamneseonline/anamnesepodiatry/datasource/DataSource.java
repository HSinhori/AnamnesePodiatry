package net.anamneseonline.anamnesepodiatry.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.anamneseonline.anamnesepodiatry.datamodel.AnamnesePodiatryDataModel;
import net.anamneseonline.anamnesepodiatry.fragments.ClientDetails;
import net.anamneseonline.anamnesepodiatry.fragments.ClientListFragment;
import net.anamneseonline.anamnesepodiatry.fragments.ImagesFragment;
import net.anamneseonline.anamnesepodiatry.model.AnamnesePodiatry;

import java.util.ArrayList;

public class DataSource extends SQLiteOpenHelper {

    private static final String DB_NAME = "anamnese_podiatry.sqlite";
    private static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;

    public DataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(AnamnesePodiatryDataModel.createTable());

        }catch (Exception e){

            Log.e("Media", "DB---> ERRO:" + e.getMessage());

        }

        try{

            db.execSQL(AnamnesePodiatryDataModel.createPatientImageTable());

        }catch (Exception e){

            Log.e("Media", "DB---> ERRO:" + e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertDB(String tabela, ContentValues dados){

        boolean sucesso = true;

        try{

            sucesso = db.insert(tabela, null, dados) > 0;

        }catch (Exception e){

        }

        return sucesso;
    }


    public boolean deleteImg(String table, int id){

        boolean sucesso = true;

        sucesso = db.delete(table, "idimage=?", new String[]{Integer.toString(ImagesFragment.id_patient_image)}) > 0;


        return sucesso;
    }


    public boolean imagesDelete(String table, int id){

        boolean sucesso = true;

        sucesso = db.delete(table, "patientid=?", new String[]{Integer.toString(ClientListFragment.idSaved)}) > 0;


        return sucesso;
    }


    public boolean delete(String table, int id){

        boolean sucesso = true;

        sucesso = db.delete(table, "id=?", new String[]{Integer.toString(id)}) > 0;


        return sucesso;
    }

    public boolean alterPac(String table, ContentValues dados){

        boolean sucesso = true;

        sucesso = db.update(table, dados,"id=?", new String[]{Integer.toString(ClientDetails.idSaved)}) > 0;


        return sucesso;
    }

    public ArrayList<AnamnesePodiatry> getAllImages(){

        AnamnesePodiatry obj;

        ArrayList<AnamnesePodiatry> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + AnamnesePodiatryDataModel.getPatientImagesTable() + " WHERE patientid = " + ClientDetails.idSaved;

        cursor = db.rawQuery(sql, null);

        if(cursor.moveToLast()){

            do{
                obj = new AnamnesePodiatry();

                obj.setIdimage(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getIdimage())));
                obj.setPatientid(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPatientid())));
                obj.setPatientimage(cursor.getBlob(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPatientimage())));
                obj.setImagetxt(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImagetxt())));
                obj.setImagerotate(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImagerotate())));

                lista.add(obj);

            }while(cursor.moveToPrevious());

        }

        cursor.close();

        return lista;
    }

    public ArrayList<AnamnesePodiatry> getAllClients(){

        AnamnesePodiatry obj;

        ArrayList<AnamnesePodiatry> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + AnamnesePodiatryDataModel.getTable() + " ORDER BY name COLLATE NOCASE";

        cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){

            do{
                obj = new AnamnesePodiatry();

                obj.setId(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getId())));
                obj.setDate(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDate())));
                obj.setName(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getName())));
                obj.setPhone(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPhone())));
                obj.setPhonetwo(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPhonetwo())));
                obj.setImgprofilecustomer(cursor.getBlob(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImgprofilecustomer())));
                obj.setImagerotatecostumer(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImagerotatecostumer())));

                lista.add(obj);
            }while(cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }

    public ArrayList<AnamnesePodiatry> getPacienttProfileList(){

        AnamnesePodiatry obj;

        ArrayList<AnamnesePodiatry> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + AnamnesePodiatryDataModel.getTable() + " WHERE id = " + ClientDetails.idSaved;

        cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){

            do{
                obj = new AnamnesePodiatry();

                obj.setId(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getId())));
                obj.setDate(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDate())));
                obj.setName(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getName())));
                obj.setGender(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getGender())));
                obj.setAddress(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAddress())));
                obj.setDistrict(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDistrict())));
                obj.setCity(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCity())));
                obj.setPhone(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPhone())));
                obj.setPhonetwo(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPhonetwo())));
                obj.setProfession(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getProfession())));
                obj.setDiabetes(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDiabetes())));
                obj.setDiabetestype(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDiabetestype())));
                obj.setDiabetestime(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getDiabetestime())));
                obj.setAllergies(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAllergies())));
                obj.setAllergieswhat(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAllergieswhat())));
                obj.setSmoking(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getSmoking())));
                obj.setAlcoholic(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAlcoholic())));
                obj.setOphtahlm(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getOphtahlm())));
                obj.setCardiov(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCardiov())));
                obj.setRenal(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getRenal())));
                obj.setOthersclin(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getOthersclin())));
                obj.setNailshape(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getNailshape())));
                obj.setWartleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getWartleft())));
                obj.setWartright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getWartright())));
                obj.setCwcleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCwcleft())));
                obj.setCwcright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCwcright())));
                obj.setCwocleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCwocleft())));
                obj.setCwocright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCwocright())));
                obj.setFissuresleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getFissuresleft())));
                obj.setFissuresright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getFissuresright())));
                obj.setKeratosisleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getKeratosisleft())));
                obj.setKeratosisright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getKeratosisright())));
                obj.setRingwormleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getRingwormleft())));
                obj.setRingwormright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getRingwormright())));
                obj.setUlcerleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getUlcerleft())));
                obj.setUlcerright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getUlcerright())));
                obj.setFootwearnum(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getFootwearnum())));
                obj.setSuitable(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getSuitable())));
                obj.setHygiene(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHygiene())));
                obj.setCyanotic(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCyanotic())));
                obj.setResected(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getResected())));
                obj.setOily(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getOily())));
                obj.setFine(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getFine())));
                obj.setSwollen(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getSwollen())));
                obj.setBromidose(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getBromidose())));
                obj.setAhnidrosis(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAhnidrosis())));
                obj.setHyperhi(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHyperhi())));
                obj.setPlantar(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPlantar())));
                obj.setCavys(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCavys())));
                obj.setFlat(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getFlat())));
                obj.setHaluxvalgus(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHaluxvalgus())));
                obj.setVarus(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getVarus())));
                obj.setBunion(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getBunion())));
                obj.setCn(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCn())));
                obj.setSn(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getSn())));
                obj.setCallus(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCallus())));
                obj.setCrack(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getCrack())));
                obj.setKeratosis(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getKeratosis())));
                obj.setWart(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getWart())));
                obj.setOnychomycosis(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getOnychomycosis())));
                obj.setOnicocryptosis(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getOnicocryptosis())));
                obj.setRef1001B(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getRef1001B())));
                obj.setRef1001R(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getRef1001R())));
                obj.setAnnnotations(cursor.getString(cursor.getColumnIndex(AnamnesePodiatryDataModel.getAnnnotations())));
                obj.setImgprofilecustomer(cursor.getBlob(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImgprofilecustomer())));
                obj.setPalmarleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPalmarleft())));
                obj.setPalmarright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPalmarright())));
                obj.setHandkeraleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandkeraleft())));
                obj.setHandkeraright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandkeraright())));
                obj.setHandonycholeft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandonycholeft())));
                obj.setHandonychoright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandonychoright())));
                obj.setHandonychomyleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandonychomyleft())));
                obj.setHandonychomyright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getHandonychomyright())));
                obj.setPyoleft(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPyoleft())));
                obj.setPyoright(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getPyoright())));
                obj.setImagerotatecostumer(cursor.getInt(cursor.getColumnIndex(AnamnesePodiatryDataModel.getImagerotatecostumer())));

                lista.add(obj);
            }while(cursor.moveToNext());

        }

        cursor.close();

        return lista;
    }
}
