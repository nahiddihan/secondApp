package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class UpdateInfo extends Activity {

    Button saveProfile;
    EditText name;
    EditText relation;
    EditText dateOfBirth;
    EditText allergicDescription;
    EditText mobile;
    EditText email;
    Spinner bloodGroup;
    EditText height;
    EditText weight;
    EditText sugarLevel;
    EditText bloodPressure;
    Spinner diabetes;
    Spinner highPressure;
    Spinner allergies;
    Spinner gender;
    String[] yesNO={"Yes","No"};
    String[] genderSpinnerArray={"Male","Female"};
    String[] bloodGroupArray={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String profileName;
    String profileRelation;
    String profileBirthDate;
    String profileMobile;
    String profileEmail;
    String profileSugarLevel;
    String profileBloodPressure;
    String profileHeight;
    String profileWeight;
    String profileHeightPrev;
    String profileWeightPrev;
    String genderString;
    String diabetesStatus;
    String highPressureStatus;
    String highPressureStatusPrev;
    String allergiesStatus;
    String bloodGroupString;
    String profileSugarLevelPrev;
    String allergicalDescription;

    private DatePickerDialog birthDatePicker;
    private SimpleDateFormat dateFormatter;
    DBHelper dbHelper;
    HashMap<String, String> map;
    ArrayAdapter<String> bloodGroupAdapter;
    ArrayAdapter<String> DiabetesAdapter;
    ArrayAdapter<String> alergiesAdapter;
    ArrayAdapter<String> highpressureAdapter;

    Integer profileId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info);
        saveProfile=(Button)findViewById(R.id.btSaveProfile);
        name=(EditText)findViewById(R.id.etName);
        mobile=(EditText)findViewById(R.id.etMobile);
        relation=(EditText)findViewById(R.id.etRelation);
        dateOfBirth=(EditText)findViewById(R.id.etDOB);
        dateOfBirth.setInputType(InputType.TYPE_NULL);
        dateOfBirth.requestFocus();
        gender=(Spinner)findViewById(R.id.spGender);
        email=(EditText)findViewById(R.id.etEmail);
        height=(EditText)findViewById(R.id.spHeight);
        weight=(EditText)findViewById(R.id.etWeight);
        bloodGroup=(Spinner)findViewById(R.id.spBloodGroup);
        sugarLevel=(EditText)findViewById(R.id.etSugerLevel);
        allergicDescription=(EditText)findViewById(R.id.etAllergicalDescription);
        bloodPressure=(EditText)findViewById(R.id.etbloodPressure);
        diabetes=(Spinner)findViewById(R.id.spDiabetes);
        highPressure=(Spinner)findViewById(R.id.spPressure);
        allergies=(Spinner)findViewById(R.id.spAllergies);

        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);

        dbHelper = new DBHelper(getApplicationContext());
        map = new HashMap<String, String>();
        map= dbHelper.getAboutOneProfile(profileId);

        profileName=map.get("name");
         profileRelation=map.get("relation");
        profileBirthDate=map.get("birth_date");
         profileMobile=map.get("mobile");
         profileEmail=map.get("email");
       profileSugarLevel=map.get("blood_suger");
        profileBloodPressure=map.get("high_pressure");
       profileHeight=map.get("height");
      profileWeight=map.get("weight");
        genderString=map.get("gender");
       diabetesStatus=map.get("diabetes");
       highPressureStatus=map.get("high_pressure_desc");
       allergiesStatus=map.get("allergies");
        bloodGroupString=map.get("blood_group");
        allergicalDescription=map.get("allergic_description");

        sugarLevel.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals(" ") )
                {
                    profileSugarLevel=sugarLevel.getText().toString();
                    profileSugarLevelPrev=map.get("blood_suger");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });
        height.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals(" ") )
                {
                    profileHeight=height.getText().toString();
                    profileHeightPrev=map.get("height");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });

        weight.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals(" ") )
                {
                    profileWeight=weight.getText().toString();
                    profileWeightPrev=map.get("weight");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });

        bloodPressure.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals(" ") )
                {
                    highPressureStatus=bloodPressure.getText().toString();
                    highPressureStatusPrev=map.get("high_pressure_desc");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });





        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setBirthDate();

         bloodGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bloodGroupArray);
        bloodGroup.setAdapter(bloodGroupAdapter);
         DiabetesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        diabetes.setAdapter(DiabetesAdapter);
         alergiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        allergies.setAdapter(alergiesAdapter);
        highpressureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        highPressure.setAdapter(highpressureAdapter);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genderSpinnerArray);
        gender.setAdapter(genderAdapter);
        setDataFromDatabase();

        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = bloodGroup.getSelectedItemPosition();
                bloodGroupString = bloodGroupArray[sid];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bloodGroupString = bloodGroupArray[0];
            }
        });

        diabetes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = diabetes.getSelectedItemPosition();
                diabetesStatus = yesNO[sid];
                if(diabetesStatus.equalsIgnoreCase("No")){
                    sugarLevel.setEnabled(false);
                }else
                    sugarLevel.setEnabled(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                diabetesStatus = yesNO[1];
                if(diabetesStatus.equalsIgnoreCase("No")){
                    sugarLevel.setEnabled(false);
                }else
                    sugarLevel.setEnabled(true);
            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = gender.getSelectedItemPosition();
                genderString = genderSpinnerArray[sid];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genderString = genderSpinnerArray[0];

            }
        });

        highPressure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = highPressure.getSelectedItemPosition();
                highPressureStatus = yesNO[sid];
                if(highPressureStatus.equalsIgnoreCase("No")){
                    bloodPressure.setEnabled(false);
                }else
                    bloodPressure.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                highPressureStatus = yesNO[1];
                if(highPressureStatus.equalsIgnoreCase("No")){
                    bloodPressure.setEnabled(false);
                }else
                    bloodPressure.setEnabled(true);
            }


        });

        allergies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = allergies.getSelectedItemPosition();
                allergiesStatus = yesNO[sid];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                allergiesStatus = yesNO[0];

            }
        });


        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                birthDatePicker.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
//        Intent backToProfileList=new Intent(this,HomeScreen.class);
//        backToProfileList.putExtra("profile_id",profileId);
//        startActivity(backToProfileList);
        finish();
    }


    public void setBirthDate(){

        Calendar newCalendar = Calendar.getInstance();

        birthDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    public void setDataFromDatabase(){
        name.setText(profileName);
        relation.setText(profileRelation);
       dateOfBirth.setText(profileBirthDate);
        mobile.setText(profileMobile);
        email.setText(profileEmail);
        height.setText(profileHeight);
        weight.setText(profileWeight);
        sugarLevel.setText(profileSugarLevel);
        bloodPressure.setText(highPressureStatus);
        dateOfBirth.setText(profileBirthDate);
        bloodGroup.setSelection(bloodGroupAdapter.getPosition(bloodGroupString));
        diabetes.setSelection(DiabetesAdapter.getPosition(diabetesStatus));
        allergies.setSelection(alergiesAdapter.getPosition(allergiesStatus));
        allergicDescription.setText(allergicalDescription);
        highPressure.setSelection(highpressureAdapter.getPosition(profileBloodPressure));


    }

    public void updateProfile(View view){

         profileName=name.getText().toString();
         profileRelation=relation.getText().toString();
        profileBirthDate=dateOfBirth.getText().toString();
        profileMobile=mobile.getText().toString();
         profileEmail=email.getText().toString();
         profileSugarLevel=sugarLevel.getText().toString();
       profileBloodPressure=bloodPressure.getText().toString();
        profileHeight=height.getText().toString();
       profileWeight=weight.getText().toString();
        allergicalDescription=allergicDescription.getText().toString();

        DBHelper db=new DBHelper(this);
        db.updateProfile(profileId,profileName,profileMobile,profileRelation,profileBirthDate,profileHeight,profileWeight,profileEmail,bloodGroupString,genderString,diabetesStatus,profileSugarLevel,highPressureStatus,profileBloodPressure,allergiesStatus,allergicalDescription,profileSugarLevelPrev,highPressureStatusPrev,profileWeightPrev,profileHeightPrev);
        finish();

    }
}
