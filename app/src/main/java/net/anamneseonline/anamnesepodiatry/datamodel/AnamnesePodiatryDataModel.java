package net.anamneseonline.anamnesepodiatry.datamodel;

public class AnamnesePodiatryDataModel {

    private final static String table = "anamnese_clients";

    private final static String id = "id";
    private final static String date = "date";
    private final static String name = "name";
    private final static String gender = "gender";
    private final static String address = "address";
    private final static String city = "city";
    private final static String district = "district";
    private final static String phone = "phone";
    private final static String phonetwo = "phonetwo";
    private final static String profession = "profession";
    private final static String diabetes = "diabetes";
    private final static String diabetestype = "diabetestype";
    private final static String diabetestime = "diabetestime";
    private final static String smoking = "smoking";
    private final static String alcoholic = "alcoholic";
    private final static String allergies = "allergies";
    private final static String allergieswhat = "allergieswhat";
    private final static String ophtahlm = "ophtahlm";
    private final static String cardiov = "cardiov";
    private final static String renal = "renal";
    private final static String othersclin = "otherclin";
    private final static String nailshape = "nailshape";
    private final static String wartleft = "wartleft";
    private final static String cwcleft = "cwcleft";
    private final static String cwocleft = "cwocleft";
    private final static String fissuresleft = "fissuresleft";
    private final static String keratosisleft = "keratosisleft";
    private final static String ringwormleft = "ringwormleft";
    private final static String ulcerleft = "ulcerleft";
    private final static String wartright = "wartright";
    private final static String cwcright = "cwcright";
    private final static String cwocright = "cwocright";
    private final static String fissuresright = "fissuresright";
    private final static String keratosisright = "keratosisright";
    private final static String ringwormright = "ringwormright";
    private final static String ulcerright = "ulcerright";
    private final static String footwearnum = "footwearnum";
    private final static String suitable = "suitable";
    private final static String hygiene = "hygiene";
    private final static String cyanotic = "cyanotic";
    private final static String resected = "resected";
    private final static String oily = "oily";
    private final static String fine = "fine";
    private final static String swollen = "swollen";
    private final static String bromidose = "bromidose";
    private final static String ahnidrosis = "ahnidrosis";
    private final static String hyperhi = "hyperhi";
    private final static String plantar = "plantar";
    private final static String cavys = "cavys";
    private final static String flat = "flat";
    private final static String haluxvalgus = "haluxvalgus";
    private final static String varus = "varus";
    private final static String bunion = "bunion";
    private final static String cn = "cn";
    private final static String sn = "sn";
    private final static String callus = "callus";
    private final static String crack = "crack";
    private final static String keratosis = "keratosis";
    private final static String wart = "wart";
    private final static String onychomycosis = "onychomycosis";
    private final static String onicocryptosis = "onicocryptosis";
    private final static String ref1001B = "ref1001B";
    private final static String ref1001R = "ref1001R";
    private final static String annnotations = "annotations";
    private final static String imgprofilecustomer = "imgprofilecustomer";
    private final static String palmarleft = "palmarleft";
    private final static String palmarright = "palmarright";
    private final static String handkeraleft = "handkeraleft";
    private final static String handkeraright = "handkeraright";
    private final static String handonycholeft = "handonycholeft";
    private final static String handonychoright = "handonychoright";
    private final static String handonychomyleft = "handonychomyleft";
    private final static String handonychomyright = "handonychomyright";
    private final static String pyoleft = "pyoleft";
    private final static String pyoright = "pyoright";
    private final static String imagerotatecostumer = "imagerotatecostumer";

    private final static String patienttable = "anamnese_patient_images";

    private final static String idimage = "idimage";
    private final static String patientid = "patientid";
    private final static String patientimage = "patientimage";
    private final static String imagetxt = "imagetxt";
    private final static String imagerotate = "imagerotate";

    private static String queryCreateTable = "";

    public static String createTable(){

        queryCreateTable = "CREATE TABLE " + table;
        queryCreateTable += "(";
        queryCreateTable += id + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCreateTable += date + " TEXT, ";
        queryCreateTable += name + " TEXT, ";
        queryCreateTable += gender + " TEXT, ";
        queryCreateTable += address + " TEXT, ";
        queryCreateTable += city + " TEXT, ";
        queryCreateTable += district + " TEXT, ";
        queryCreateTable += phone + " TEXT, ";
        queryCreateTable += phonetwo + " TEXT, ";
        queryCreateTable += profession + " TEXT, ";
        queryCreateTable += diabetes + " INTEGER DEFAULT 0, ";
        queryCreateTable += diabetestype + " TEXT, ";
        queryCreateTable += diabetestime + " TEXT, ";
        queryCreateTable += smoking + " INTEGER, ";
        queryCreateTable += alcoholic + " INTEGER DEFAULT 0, ";
        queryCreateTable += allergies + " INTEGER DEFAULT 0, ";
        queryCreateTable += allergieswhat + " TEXT, ";
        queryCreateTable += ophtahlm + " INTEGER DEFAULT 0, ";
        queryCreateTable += cardiov + " INTEGER DEFAULT 0, ";
        queryCreateTable += renal + " INTEGER DEFAULT 0, ";
        queryCreateTable += othersclin + " TEXT, ";
        queryCreateTable += nailshape + " INTEGER DEFAULT 0, ";
        queryCreateTable += wartleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += cwcleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += cwocleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += fissuresleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += keratosisleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += ringwormleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += ulcerleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += wartright + " INTEGER DEFAULT 0, ";
        queryCreateTable += cwcright + " INTEGER DEFAULT 0, ";
        queryCreateTable += cwocright + " INTEGER DEFAULT 0, ";
        queryCreateTable += fissuresright + " INTEGER DEFAULT 0, ";
        queryCreateTable += keratosisright + " INTEGER DEFAULT 0, ";
        queryCreateTable += ringwormright + " INTEGER DEFAULT 0, ";
        queryCreateTable += ulcerright + " INTEGER DEFAULT 0, ";
        queryCreateTable += footwearnum + " INTEGER DEFAULT 0, ";
        queryCreateTable += suitable + " INTEGER DEFAULT 0, ";
        queryCreateTable += hygiene + " INTEGER DEFAULT 0, ";
        queryCreateTable += cyanotic + " INTEGER DEFAULT 0, ";
        queryCreateTable += resected + " INTEGER DEFAULT 0, ";
        queryCreateTable += oily + " INTEGER DEFAULT 0, ";
        queryCreateTable += fine + " INTEGER DEFAULT 0, ";
        queryCreateTable += swollen + " INTEGER DEFAULT 0, ";
        queryCreateTable += bromidose + " INTEGER DEFAULT 0, ";
        queryCreateTable += ahnidrosis + " INTEGER DEFAULT 0, ";
        queryCreateTable += hyperhi + " INTEGER DEFAULT 0, ";
        queryCreateTable += plantar + " INTEGER DEFAULT 0, ";
        queryCreateTable += cavys + " INTEGER DEFAULT 0, ";
        queryCreateTable += flat + " INTEGER DEFAULT 0, ";
        queryCreateTable += haluxvalgus + " INTEGER DEFAULT 0, ";
        queryCreateTable += varus + " INTEGER DEFAULT 0, ";
        queryCreateTable += bunion + " INTEGER DEFAULT 0, ";
        queryCreateTable += cn + " INTEGER DEFAULT 0, ";
        queryCreateTable += sn + " INTEGER DEFAULT 0, ";
        queryCreateTable += callus + " INTEGER DEFAULT 0, ";
        queryCreateTable += crack + " INTEGER DEFAULT 0, ";
        queryCreateTable += keratosis + " INTEGER DEFAULT 0, ";
        queryCreateTable += wart + " INTEGER DEFAULT 0, ";
        queryCreateTable += onychomycosis + " INTEGER DEFAULT 0, ";
        queryCreateTable += onicocryptosis + " INTEGER DEFAULT 0, ";
        queryCreateTable += ref1001B + " INTEGER DEFAULT 0, ";
        queryCreateTable += ref1001R + " INTEGER DEFAULT 0, ";
        queryCreateTable += annnotations + " TEXT, ";
        queryCreateTable += imgprofilecustomer + " BLOB, ";
        queryCreateTable += palmarleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += palmarright + " INTEGER DEFAULT 0, ";
        queryCreateTable += handkeraleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += handkeraright + " INTEGER DEFAULT 0, ";
        queryCreateTable += handonycholeft + " INTEGER DEFAULT 0, ";
        queryCreateTable += handonychoright + " INTEGER DEFAULT 0, ";
        queryCreateTable += handonychomyleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += handonychomyright + " INTEGER DEFAULT 0, ";
        queryCreateTable += pyoleft + " INTEGER DEFAULT 0, ";
        queryCreateTable += pyoright + " INTEGER DEFAULT 0, ";
        queryCreateTable += imagerotatecostumer + " INTEGER DEFAULT 0 ";
        queryCreateTable += ")";

        return queryCreateTable;

    }

    private static String queryPatientImageTable = "";

    public static String createPatientImageTable(){

        queryPatientImageTable = "CREATE TABLE " + patienttable;
        queryPatientImageTable += "(";
        queryPatientImageTable += idimage + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryPatientImageTable += patientid + " INTEGER DEFAULT 0, ";
        queryPatientImageTable += patientimage + " BLOB, ";
        queryPatientImageTable += imagetxt + " TEXT, ";
        queryPatientImageTable += imagerotate + " INTEGER DEFAULT 0 ";
        queryPatientImageTable += ")";

        return queryPatientImageTable;

    }


    public static String getTable() {
        return table;
    }

    public static String getId() {
        return id;
    }

    public static String getDate() {
        return date;
    }

    public static String getName() {
        return name;
    }

    public static String getGender() {
        return gender;
    }

    public static String getAddress() {
        return address;
    }

    public static String getCity() {
        return city;
    }

    public static String getDistrict() {
        return district;
    }

    public static String getPhone() {
        return phone;
    }

    public static String getPhonetwo() {
        return phonetwo;
    }

    public static String getProfession() {
        return profession;
    }

    public static String getDiabetes() {
        return diabetes;
    }

    public static String getDiabetestype() {
        return diabetestype;
    }

    public static String getDiabetestime() {
        return diabetestime;
    }

    public static String getSmoking() {
        return smoking;
    }

    public static String getAlcoholic() {
        return alcoholic;
    }

    public static String getAllergies() {
        return allergies;
    }

    public static String getAllergieswhat() {
        return allergieswhat;
    }

    public static String getOphtahlm() {
        return ophtahlm;
    }

    public static String getCardiov() {
        return cardiov;
    }

    public static String getRenal() {
        return renal;
    }

    public static String getOthersclin() {
        return othersclin;
    }

    public static String getNailshape() {
        return nailshape;
    }

    public static String getWartleft() {
        return wartleft;
    }

    public static String getCwcleft() {
        return cwcleft;
    }

    public static String getCwocleft() {
        return cwocleft;
    }

    public static String getFissuresleft() {
        return fissuresleft;
    }

    public static String getKeratosisleft() {
        return keratosisleft;
    }

    public static String getRingwormleft() {
        return ringwormleft;
    }

    public static String getUlcerleft() {
        return ulcerleft;
    }

    public static String getWartright() {
        return wartright;
    }

    public static String getCwcright() {
        return cwcright;
    }

    public static String getCwocright() {
        return cwocright;
    }

    public static String getFissuresright() {
        return fissuresright;
    }

    public static String getKeratosisright() {
        return keratosisright;
    }

    public static String getRingwormright() {
        return ringwormright;
    }

    public static String getUlcerright() {
        return ulcerright;
    }

    public static String getFootwearnum() {
        return footwearnum;
    }

    public static String getSuitable() {
        return suitable;
    }

    public static String getHygiene() {
        return hygiene;
    }

    public static String getCyanotic() {
        return cyanotic;
    }

    public static String getResected() {
        return resected;
    }

    public static String getOily() {
        return oily;
    }

    public static String getFine() {
        return fine;
    }

    public static String getSwollen() {
        return swollen;
    }

    public static String getBromidose() {
        return bromidose;
    }

    public static String getAhnidrosis() {
        return ahnidrosis;
    }

    public static String getHyperhi() {
        return hyperhi;
    }

    public static String getPlantar() {
        return plantar;
    }

    public static String getCavys() {
        return cavys;
    }

    public static String getFlat() {
        return flat;
    }

    public static String getHaluxvalgus() {
        return haluxvalgus;
    }

    public static String getVarus() {
        return varus;
    }

    public static String getBunion() {
        return bunion;
    }

    public static String getCn() {
        return cn;
    }

    public static String getSn() {
        return sn;
    }

    public static String getCallus() {
        return callus;
    }

    public static String getCrack() {
        return crack;
    }

    public static String getKeratosis() {
        return keratosis;
    }

    public static String getWart() {
        return wart;
    }

    public static String getOnychomycosis() {
        return onychomycosis;
    }

    public static String getOnicocryptosis() {
        return onicocryptosis;
    }

    public static String getRef1001B() {
        return ref1001B;
    }

    public static String getRef1001R() {
        return ref1001R;
    }

    public static String getAnnnotations() {
        return annnotations;
    }

    public static String getImgprofilecustomer() {
        return imgprofilecustomer;
    }

    public static String getPatientImagesTable() {
        return patienttable;
    }

    public static String getIdimage() {
        return idimage;
    }

    public static String getPatientid() {
        return patientid;
    }

    public static String getPatientimage() {
        return patientimage;
    }

    public static String getPalmarleft() {
        return palmarleft;
    }

    public static String getPalmarright() {
        return palmarright;
    }

    public static String getHandkeraleft() {
        return handkeraleft;
    }

    public static String getHandkeraright() {
        return handkeraright;
    }

    public static String getHandonycholeft() {
        return handonycholeft;
    }

    public static String getHandonychoright() {
        return handonychoright;
    }

    public static String getHandonychomyleft() {
        return handonychomyleft;
    }

    public static String getHandonychomyright() {
        return handonychomyright;
    }

    public static String getPyoleft() {
        return pyoleft;
    }

    public static String getPatienttable() {
        return patienttable;
    }

    public static String getImagerotate() {
        return imagerotate;
    }

    public static String getImagerotatecostumer() {
        return imagerotatecostumer;
    }

    public static String getPyoright() {
        return pyoright;
    }

    public static String getImagetxt() {
        return imagetxt;
    }

    public static String getQueryCreateTable() {
        return queryCreateTable;
    }

    public static void setQueryCreateTable(String queryCreateTable) {
        AnamnesePodiatryDataModel.queryCreateTable = queryCreateTable;
    }

    public static String getQueryPatientImageTable() {
        return queryPatientImageTable;
    }

    public static void setQueryPatientImageTable(String queryPatientImageTable) {
        AnamnesePodiatryDataModel.queryPatientImageTable = queryPatientImageTable;
    }
}
