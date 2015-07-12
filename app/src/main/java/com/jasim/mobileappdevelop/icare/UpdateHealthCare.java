package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateHealthCare extends Activity {

    EditText name;
    EditText mobile;
    EditText email;
    EditText address;
    Button cancelButton;
    Button saveButton;
    Integer centerId;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_health_care_info);
        name=(EditText)findViewById(R.id.etHealthCareName);
        mobile=(EditText)findViewById(R.id.etHealthCareMobile);
        email=(EditText)findViewById(R.id.etHealthCareEmail);
        address=(EditText)findViewById(R.id.etHealthCareAddress);
        saveButton=(Button)findViewById(R.id.btHealthCareSave);
        cancelButton=(Button)findViewById(R.id.btUpdateHealthCareCancel);
        Intent intent=getIntent();
        centerId=intent.getIntExtra("center_id",0);
        dbHelper = new DBHelper(getApplicationContext());
        HashMap<String, String> map = new HashMap<String, String>();
        map= dbHelper.getAboutOneMedicalCenter(centerId);
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
                String healthCareName=name.getText().toString();
                String healthCareMobile=mobile.getText().toString();
                String healthCareEmail=email.getText().toString();
                String healthCareAddress=address.getText().toString();
                if(healthCareName!=null){
                    dbHelper.updateHealthCare(healthCareName,healthCareMobile,healthCareEmail,healthCareAddress,centerId);
                    Intent doctorInfoIntent = new Intent(getApplicationContext(), MedicalCenterInfo.class);
                    doctorInfoIntent.putExtra("center_id",centerId);
                    startActivity(doctorInfoIntent);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Enter HealthCare name", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
