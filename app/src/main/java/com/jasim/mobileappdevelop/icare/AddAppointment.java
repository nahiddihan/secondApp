package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddAppointment extends Activity {
    Button saveAppointment;
    Button cancelAppointment;
    Spinner doctorSpinner;
    Spinner careCenterSpinner;
    EditText appointmentAddress;
    EditText appointmentDate;
    EditText appointmentTime;
    EditText appointmentTitle;
    Integer profileId;
    DBHelper mydb;
    ArrayList<HashMap<String, String>> doctorList;
    ArrayList<String> doctorArrayList;
    ArrayAdapter<String> DoctorSpinnerAdapter;
    ArrayList<HashMap<String, String>> careCenterList;
    ArrayList<String> careCenterArrayList;
    ArrayList<String> careCenterAddressArrayList;
    ArrayAdapter<String> careCenterArrayAdapter;
    private DatePickerDialog appointmentDatePicker;
    private SimpleDateFormat dateFormatter;
    String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_appointment);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);
        doctorList=new ArrayList<HashMap<String, String>>();
        mydb = new DBHelper(this);
        doctorList = mydb.getAllDoctor(profileId);
        doctorArrayList =new ArrayList<>();
        for(int i=0;i<doctorList.size();i++){

            doctorArrayList.add(doctorList.get(i).get("name"));
        }
        careCenterList = mydb.getAllMedicalCenter(profileId);
        careCenterArrayList =new ArrayList<>();
        careCenterAddressArrayList =new ArrayList<>();
        for(int i=0;i<careCenterList.size();i++){

            careCenterArrayList.add(careCenterList.get(i).get("name"));
            careCenterAddressArrayList.add(careCenterList.get(i).get("address"));
        }
        careCenterArrayList.add("other");

        doctorSpinner=(Spinner)findViewById(R.id.spDoctor);
        careCenterSpinner=(Spinner)findViewById(R.id.spCareCenter);
        appointmentAddress=(EditText)findViewById(R.id.etAppointmentAddress);
        appointmentTitle=(EditText)findViewById(R.id.etAppointmentTitle);

        appointmentDate=(EditText)findViewById(R.id.etAppointmentDate);
        appointmentDate.setInputType(InputType.TYPE_NULL);
        appointmentDate.requestFocus();
        setAppointmentDate();

        appointmentTime=(EditText)findViewById(R.id.etAppointmentTime);
        appointmentTime.setInputType(InputType.TYPE_NULL);
        appointmentTime.requestFocus();
        saveAppointment=(Button)findViewById(R.id.btAppointmentSave);
        cancelAppointment=(Button)findViewById(R.id.btAppointmentCancel);

         DoctorSpinnerAdapter=new ArrayAdapter<String>
                (getApplicationContext(),R.layout.spinner_item,R.id.item, doctorArrayList);
        doctorSpinner.setAdapter(DoctorSpinnerAdapter);
        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = doctorSpinner.getSelectedItemPosition();
                doctorName=doctorArrayList.get(sid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        careCenterArrayAdapter=new ArrayAdapter<String>
                (getApplicationContext(),R.layout.spinner_item,R.id.item,careCenterArrayList);
        careCenterSpinner.setAdapter(careCenterArrayAdapter);

        careCenterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid = careCenterSpinner.getSelectedItemPosition();
                if(careCenterArrayList.get(sid).equalsIgnoreCase("other")){
                    appointmentAddress.setEnabled(true);
                    appointmentAddress.setText("");

                }else{
                    String address=careCenterArrayList.get(sid)+"\n"+careCenterAddressArrayList.get(sid);
                    appointmentAddress.setEnabled(false);
                    appointmentAddress.setText(address);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        appointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentDatePicker.show();

            }
        });

        appointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String AmPm="";
                        int hour;

                        if(selectedHour >0 && selectedHour<12 ){
                            AmPm="AM";
                            hour=selectedHour;

                        }else {
                            AmPm = "PM";
                            hour=selectedHour-12;

                        }

                        appointmentTime.setText( hour + ":" + selectedMinute+"  "+AmPm);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        saveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title=appointmentTitle.getText().toString();
                String Address=appointmentAddress.getText().toString();
                String Date=appointmentDate.getText().toString();
                String Time=appointmentTime.getText().toString();

                DBHelper db=new DBHelper(getApplicationContext());
                db.insertAppointment(Title,doctorName,Address,Date,Time,profileId);
                Intent backToAppointment=new Intent(AddAppointment.this,Appointment.class);
                backToAppointment.putExtra("profile_id",profileId);
                startActivity(backToAppointment);
                finish();
            }
        });
        cancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void setAppointmentDate(){

        Calendar newCalendar = Calendar.getInstance();

        appointmentDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                appointmentDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
