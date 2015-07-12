package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateDoctor extends Activity{

    EditText name;
    EditText mobile;
    EditText email;
    EditText address;
    Button cancelButton;
    Button saveButton;
    Integer doctorId;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_doctor);
        name=(EditText)findViewById(R.id.etDoctorName);
        mobile=(EditText)findViewById(R.id.etDoctorMobile);
        email=(EditText)findViewById(R.id.etDoctorEmail);
        address=(EditText)findViewById(R.id.etDoctorAddress);
        saveButton=(Button)findViewById(R.id.btNewDoctorSave);
        cancelButton=(Button)findViewById(R.id.btNewDoctorCancel);
        Intent intent=getIntent();
        doctorId=intent.getIntExtra("doctor_id",0);
        dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map= dbHelper.getAboutOneDoctor(doctorId);
        name.setText(map.get("name"));
        mobile.setText(map.get("mobile"));
        email.setText(map.get("email"));
        address.setText(map.get("address"));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=name.getText().toString();
                String doctorMobile=mobile.getText().toString();
                String doctorEmail=email.getText().toString();
                String doctorAddress=address.getText().toString();
                if(doctorName!=null){
                    dbHelper.updateDoctor(doctorName,doctorMobile,doctorEmail,doctorAddress,doctorId);
                    Intent doctorInfoIntent = new Intent(getApplicationContext(), DoctorInfo.class);
                    doctorInfoIntent.putExtra("doctor_id",doctorId);
                    startActivity(doctorInfoIntent);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(),"Enter doctor name",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
