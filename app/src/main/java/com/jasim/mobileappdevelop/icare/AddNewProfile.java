package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DatePickerDialog.OnDateSetListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddNewProfile extends Activity {
    Button saveProfile;
    EditText name;
    EditText relation;
    EditText dateOfBirth;
    EditText mobile;
    EditText email;
    Spinner bloodGroup;
    EditText height;
    EditText weight;
    EditText sugarLevel;
    EditText bloodPressure;
    EditText allergicDescription;
    Spinner diabetes;
    Spinner highPressure;
    Spinner allergies;
    Spinner gender;
    String[] yesNO={"Yes","No"};
    String[] genderSpinnerArray={"Male","Female"};
    String[] bloodGroupArray={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String genderString;
    String diabetesStatus;
    String highPressureStatus;
    String allergiesStatus;
    String bloodGroupString;
    private DatePickerDialog birthDatePicker;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_profile);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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
        bloodPressure=(EditText)findViewById(R.id.etbloodPressure);
        diabetes=(Spinner)findViewById(R.id.spDiabetes);
        highPressure=(Spinner)findViewById(R.id.spPressure);
        allergies=(Spinner)findViewById(R.id.spAllergies);
        allergicDescription=(EditText)findViewById(R.id.etAllergicalDescription);
        setBirthDate();


        ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bloodGroupArray);
        bloodGroup.setAdapter(bloodGroupAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        diabetes.setAdapter(adapter);
        ArrayAdapter<String> alergiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        allergies.setAdapter(alergiesAdapter);
        ArrayAdapter<String> highpressureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yesNO);
        highPressure.setAdapter(highpressureAdapter);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genderSpinnerArray);
        gender.setAdapter(genderAdapter);

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
        Intent backToProfileList=new Intent(this,MainActivity.class);
        startActivity(backToProfileList);
        this.finish();
    }
    public void setBirthDate(){

        Calendar newCalendar = Calendar.getInstance();

        birthDatePicker = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void saveProfile(View view){

        String stringName=name.getText().toString();
        String stringRelation=relation.getText().toString();
        String stringBirthDate=dateOfBirth.getText().toString();
        String stringMobile=mobile.getText().toString();
        String stringEmail=email.getText().toString();
        String stringSugarLevel=sugarLevel.getText().toString();
        String stringBloodPressure=bloodPressure.getText().toString();
        String stringHeight=height.getText().toString();
        String stringWeight=weight.getText().toString();
        String allergiDescription=allergicDescription.getText().toString();

        DBHelper db=new DBHelper(this);
        db.insertProfile(stringName,stringMobile,stringRelation,stringBirthDate,stringEmail,stringHeight,stringWeight,bloodGroupString,genderString,diabetesStatus,stringSugarLevel,highPressureStatus,stringBloodPressure,allergiesStatus,allergiDescription);
        Intent backToProfileList=new Intent(this,MainActivity.class);
        startActivity(backToProfileList);
        this.finish();

    }


}
