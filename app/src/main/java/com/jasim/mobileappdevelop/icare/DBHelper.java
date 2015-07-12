package com.jasim.mobileappdevelop.icare;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "icare.db";
    public static final String PROFILE_TABLE_NAME = "profile";
    private HashMap hp;
    ArrayList<HashMap<String, String>> hashMapArrayList;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

        db.execSQL(
                "create table if not exists profile " +
                        "(profile_id integer primary key, name text, mobile text, relation text, birth_date text, height text, weight text, blood_group text, gender text, email text,diabetes text,blood_suger text,high_pressure text,high_pressure_desc text,allergies text,allergic_description text,suger_prev text,high_pressure_prev text,weight_prev text,height_prev text)"
        );
        db.execSQL(
                "create table if not exists doctor " +
                        "(doctor_id integer primary key, name text, mobile text, email text, address text,user_id text)"
        );
            db.execSQL(
                    "create table if not exists health_center " +
                            "(health_center_id integer primary key, name text, mobile text, email text, address text, user_id text)"
            );
            db.execSQL(
                    "create table if not exists vaccine " +
                            "(vaccine_id integer primary key, title text, description text, user_id text)"
            );
            db.execSQL(
                    "create table if not exists vaccine_dose " +
                            "(dose_id integer primary key, date text, dose_title text, vaccine_id text)"
            );
            db.execSQL(
                    "create table if not exists appointment " +
                            "(appointment_id integer primary key, title text, doctor_name text, care_center text, user_id text, date text, time text, alarm_date text, alarm_time text)"
            );

            db.execSQL(
                    "create table if not exists diet " +
                            "(diet_id integer primary key, date text, breakfast_menu text, lunch_menu text, diner_menu text, user_id text)"
            );
            db.execSQL(
                    "create table if not exists note " +
                            "(note_id integer primary key, date text, time text, title text, description text, user_id text)"
            );
    }catch(SQLiteException e) {
            Log.e("create err",e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS profile");
        onCreate(db);
    }

    public boolean insertProfile(String name, String mobile, String relation, String birthDate,String email,String height, String weight,String bloodGroup,String gender,String diabetes,String bloodSugar,String highPressure,String highPressureDesc,String allergies,String allergiDescription )
    {
        String suger_prev=" ";
        String blood_pressure_prev=" ";
        String weightPrev=" ";
        String heightPrev=" ";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("relation", relation);
        contentValues.put("birth_date", birthDate);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        contentValues.put("email", email);
        contentValues.put("blood_group", bloodGroup);
        contentValues.put("gender", gender);
        contentValues.put("diabetes", diabetes);
        contentValues.put("blood_suger", bloodSugar);
        contentValues.put("high_pressure", highPressure);
        contentValues.put("high_pressure_desc", highPressureDesc);
        contentValues.put("allergies", allergies);
        contentValues.put("allergic_description", allergiDescription);
        contentValues.put("suger_prev", suger_prev);
        contentValues.put("high_pressure_prev", blood_pressure_prev);
        contentValues.put("weight_prev", weightPrev);
        contentValues.put("height_prev", heightPrev);
        db.insert("profile", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from profile where profile_id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PROFILE_TABLE_NAME);
        return numRows;
    }

    public boolean updateProfile(Integer id, String name, String mobile, String relation, String birthDate,String height,String weight,String email,String bloodGroup,String gender,String diabetes,String bloodSugar,String highPressure,String highPressureDesc,String allergies,String allergiDescription,String sugerPrev,String bloodPressurePrevious,String weightPrev,String heightPrev)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("relation", relation);
        contentValues.put("birth_date", birthDate);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        contentValues.put("email", email);
        contentValues.put("blood_group", bloodGroup);
        contentValues.put("gender", gender);
        contentValues.put("diabetes", diabetes);
        contentValues.put("blood_suger", bloodSugar);
        contentValues.put("high_pressure", highPressure);
        contentValues.put("high_pressure_desc", highPressureDesc);
        contentValues.put("allergies", allergies);
        contentValues.put("allergic_description", allergiDescription);
        contentValues.put("suger_prev", sugerPrev);
        contentValues.put("high_pressure_prev", bloodPressurePrevious);
        contentValues.put("weight_prev", weightPrev);
        contentValues.put("height_prev", heightPrev);
        db.update("profile", contentValues, "profile_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteProfile(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("profile", "profile_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<HashMap<String, String>> getAllProfile()
    {

        ArrayList<HashMap<String, String>> profileList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from profile", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
           // doctorArrayList.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_NAME)));
            HashMap<String, String> profileMap = new HashMap<String, String>();
            profileMap.put("id",res.getString(res.getColumnIndex("profile_id")));
            profileMap.put("name",res.getString(res.getColumnIndex("name")));
            profileList.add(profileMap);

            res.moveToNext();
        }
        return profileList;
    }
    public HashMap<String,String> getAboutOneProfile(Integer id)
    {
       // ArrayList<String> doctorArrayList = new ArrayList<String>();

        HashMap<String, String> map = new HashMap<String, String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from profile where profile_id="+id+"", null );
        Log.v("user details:",res.toString());
        res.moveToFirst();

        while(res.isAfterLast() == false){

           map.put("id",res.getString(res.getColumnIndex("profile_id")));
            map.put("name",res.getString(res.getColumnIndex("name")));
            map.put("mobile",res.getString(res.getColumnIndex("mobile")));
            map.put("relation",res.getString(res.getColumnIndex("relation")));
            map.put("birth_date",res.getString(res.getColumnIndex("birth_date")));
            map.put("height",res.getString(res.getColumnIndex("height")));
            map.put("weight",res.getString(res.getColumnIndex("weight")));
            map.put("blood_group",res.getString(res.getColumnIndex("blood_group")));
            map.put("gender",res.getString(res.getColumnIndex("gender")));
            map.put("email",res.getString(res.getColumnIndex("email")));
            map.put("diabetes",res.getString(res.getColumnIndex("diabetes")));
            map.put("blood_suger",res.getString(res.getColumnIndex("blood_suger")));
            map.put("high_pressure",res.getString(res.getColumnIndex("high_pressure")));
            map.put("high_pressure_desc",res.getString(res.getColumnIndex("high_pressure_desc")));
            map.put("allergies",res.getString(res.getColumnIndex("allergies")));
            map.put("allergic_description",res.getString(res.getColumnIndex("allergic_description")));
            map.put("suger_prev",res.getString(res.getColumnIndex("suger_prev")));
            map.put("high_pressure_prev",res.getString(res.getColumnIndex("high_pressure_prev")));
            map.put("weight_prev", res.getString(res.getColumnIndex("weight_prev")));
            map.put("height_prev", res.getString(res.getColumnIndex("height_prev")));
            res.moveToNext();
        }
        return map;
    }
    public ArrayList<HashMap<String, String>> getAllDoctor(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> DoctorList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from doctor where user_id="+id+"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            // doctorArrayList.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_NAME)));
            HashMap<String, String> doctorMap = new HashMap<String, String>();
            doctorMap.put("id",res.getString(res.getColumnIndex("doctor_id")));
            doctorMap.put("name",res.getString(res.getColumnIndex("name")));
            DoctorList.add(doctorMap);

            res.moveToNext();
        }
        return DoctorList;
    }

    public boolean insertDoctor(String name, String mobile, String email, String address, Integer id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("user_id",id);
        db.insert("doctor", null, contentValues);
        return true;
    }
    public boolean updateDoctor(String name, String mobile, String email, String address, Integer id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("email", email);
        contentValues.put("address", address);
       // contentValues.put("user_id",id);
        db.update("doctor", contentValues, "doctor_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public HashMap<String,String> getAboutOneDoctor(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();

        HashMap<String, String> map = new HashMap<String, String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from doctor where doctor_id="+id+"", null );
        Log.v("doctor details:",res.toString());
        res.moveToFirst();

        while(res.isAfterLast() == false){

            map.put("id",res.getString(res.getColumnIndex("doctor_id")));
            map.put("name",res.getString(res.getColumnIndex("name")));
            map.put("mobile",res.getString(res.getColumnIndex("mobile")));
            map.put("email",res.getString(res.getColumnIndex("email")));
            map.put("address",res.getString(res.getColumnIndex("address")));
           res.moveToNext();
        }
        return map;
    }

    public ArrayList<HashMap<String, String>> getAllMedicalCenter(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> medicalCenterList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from health_center where user_id="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            HashMap<String, String> doctorMap = new HashMap<String, String>();
            doctorMap.put("id",res.getString(res.getColumnIndex("health_center_id")));
            doctorMap.put("name",res.getString(res.getColumnIndex("name")));
            doctorMap.put("address",res.getString(res.getColumnIndex("address")));
            medicalCenterList.add(doctorMap);

            res.moveToNext();
        }
        return medicalCenterList;
    }

    public boolean insertMedicalCenter(String name, String mobile,String email,String address,Integer id )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("email", email);
        contentValues.put("address", address);
        contentValues.put("user_id", id);
        db.insert("health_center", null, contentValues);
        return true;
    }
    public boolean updateHealthCare(String name, String mobile,String email,String address,Integer id )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("email", email);
        contentValues.put("address", address);
      //  contentValues.put("user_id", id);
        db.update("health_center", contentValues, "health_center_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public HashMap<String,String> getAboutOneMedicalCenter(Integer id)
    {

        HashMap<String, String> map = new HashMap<String, String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from health_center where health_center_id="+id+"", null );
        Log.v("center details:",res.toString());
        res.moveToFirst();

        while(res.isAfterLast() == false){

            map.put("id",res.getString(res.getColumnIndex("health_center_id")));
            map.put("name",res.getString(res.getColumnIndex("name")));
            map.put("mobile",res.getString(res.getColumnIndex("mobile")));
            map.put("email",res.getString(res.getColumnIndex("email")));
            map.put("address",res.getString(res.getColumnIndex("address")));
            res.moveToNext();
        }
        return map;
    }

    public Integer deleteHealthCare(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("health_center","health_center_id = ? ", new String[] { Integer.toString(id) });
    }
    public Integer deleteAppointment(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("appointment","appointment_id = ? ", new String[] { Integer.toString(id) });
    }
    public boolean insertVaccine(String title, String description,String userId )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("user_id", userId);
        db.insert("vaccine", null, contentValues);
        return true;
    }
    public ArrayList<HashMap<String, String>> getAllVaccine(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> vaccineList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from vaccine where user_id="+id+"", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            HashMap<String, String> vaccineMap = new HashMap<String, String>();
            vaccineMap.put("vaccine_id",res.getString(res.getColumnIndex("vaccine_id")));
            vaccineMap.put("title",res.getString(res.getColumnIndex("title")));
            vaccineList.add(vaccineMap);

            res.moveToNext();
        }
        return vaccineList;
    }

    public boolean insertDose(String title, String date, String vaccineId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dose_title", title);
        contentValues.put("date", date);
        contentValues.put("vaccine_id", vaccineId);

        db.insert("vaccine_dose", null, contentValues);
        return true;
    }
    public Integer deleteDose(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("vaccine_dose", "dose_id = ? ", new String[] { Integer.toString(id) });
    }
    public Integer deleteVaccine(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("vaccine", "vaccine_id = ? ", new String[] { Integer.toString(id) });
    }

    public Integer deleteDoctor(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("doctor", "doctor_id = ? ", new String[] { Integer.toString(id) });
    }
    public ArrayList<HashMap<String, String>> getAllVaccineDose(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> doseList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from vaccine_dose where vaccine_id="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            HashMap<String, String> vaccineMap = new HashMap<String, String>();
            vaccineMap.put("dose_id",res.getString(res.getColumnIndex("dose_id")));
            vaccineMap.put("dose_title",res.getString(res.getColumnIndex("dose_title")));
            vaccineMap.put("date",res.getString(res.getColumnIndex("date")));

            doseList.add(vaccineMap);

            res.moveToNext();
        }
        return doseList;
    }

    public boolean insertAppointment(String title, String doctorName, String careCenterAddress, String date,String time, Integer id)
    {

        String alarmDate=" ";
        String alarmTime=" ";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("doctor_name", doctorName);
        contentValues.put("care_center", careCenterAddress);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("alarm_date", alarmDate);
        contentValues.put("alarm_time", alarmTime);
        contentValues.put("user_id",id);
        db.insert("appointment", null, contentValues);
        return true;
    }
    public ArrayList<HashMap<String, String>> getAllAppointment(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> alarmList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from appointment where user_id="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            HashMap<String, String> vaccineMap = new HashMap<String, String>();
            vaccineMap.put("appointment_id",res.getString(res.getColumnIndex("appointment_id")));
            vaccineMap.put("title",res.getString(res.getColumnIndex("title")));
            vaccineMap.put("date",res.getString(res.getColumnIndex("date")));
            vaccineMap.put("time",res.getString(res.getColumnIndex("time")));
            alarmList.add(vaccineMap);
            res.moveToNext();
        }
        return alarmList;
    }
    public HashMap<String,String> getOneAppointmentDetails(Integer id)
    {

        HashMap<String, String> map = new HashMap<String, String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from appointment where appointment_id="+id+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            map.put("appointment_id",res.getString(res.getColumnIndex("appointment_id")));
            map.put("title",res.getString(res.getColumnIndex("title")));
            map.put("doctor_name",res.getString(res.getColumnIndex("doctor_name")));
            map.put("care_center",res.getString(res.getColumnIndex("care_center")));
            map.put("date",res.getString(res.getColumnIndex("date")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            res.moveToNext();
        }
        return map;
    }

    public boolean insertNote(String title, String description, String date, String time, Integer id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("user_id",id);
        db.insert("note", null, contentValues);
        return true;
    }

    public boolean updateNote(String title, String description, String date, String time, Integer id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("date", date);
        contentValues.put("time", time);
      
        db.update("note", contentValues, "note_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteNote(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("note", "note_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<HashMap<String, String>> getAllNote(Integer id)
    {
        // ArrayList<String> doctorArrayList = new ArrayList<String>();
        ArrayList<HashMap<String, String>> noteList=new ArrayList<HashMap<String, String>>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from note where user_id="+id+"", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            // doctorArrayList.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_NAME)));
            HashMap<String, String> noteMap = new HashMap<String, String>();
            noteMap.put("note_id",res.getString(res.getColumnIndex("note_id")));
            noteMap.put("title",res.getString(res.getColumnIndex("title")));
            noteMap.put("date",res.getString(res.getColumnIndex("date")));
            noteMap.put("time",res.getString(res.getColumnIndex("time")));
            noteList.add(noteMap);

            res.moveToNext();
        }
        return noteList;
    }
    public HashMap<String,String> getAboutOneNote(Integer id)
    {


        HashMap<String, String> map = new HashMap<String, String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from note where note_id="+id+"", null );

        res.moveToFirst();

        while(res.isAfterLast() == false){

            map.put("note_id",res.getString(res.getColumnIndex("note_id")));
            map.put("title",res.getString(res.getColumnIndex("title")));
            map.put("description",res.getString(res.getColumnIndex("description")));
            map.put("date",res.getString(res.getColumnIndex("date")));
            map.put("time",res.getString(res.getColumnIndex("time")));
            map.put("user_id",res.getString(res.getColumnIndex("user_id")));
            res.moveToNext();
        }
        return map;
    }
}