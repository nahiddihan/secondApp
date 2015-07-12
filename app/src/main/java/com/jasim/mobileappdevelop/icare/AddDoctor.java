package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NAKODIKO on 21-Jun-15.
 */
public class AddDoctor extends Activity {
    EditText name;
    EditText mobile;
    EditText email;
    EditText address;
    Button saveButton;
    Integer profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_doctor);
        name=(EditText)findViewById(R.id.etDoctorName);
        mobile=(EditText)findViewById(R.id.etDoctorMobile);
        email=(EditText)findViewById(R.id.etDoctorEmail);
        address=(EditText)findViewById(R.id.etDoctorAddress);
        saveButton=(Button)findViewById(R.id.btNewDoctorSave);
        Intent intent=getIntent();
        profileId=intent.getIntExtra("profile_id",0);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName=name.getText().toString();
                String doctorMobile=mobile.getText().toString();
                String doctorEmail=email.getText().toString();
                String doctorAddress=address.getText().toString();
                DBHelper db=new DBHelper(getApplicationContext());
                db.insertDoctor(doctorName, doctorMobile, doctorEmail, doctorAddress,profileId);
                Intent backToDoctorList=new Intent(getApplicationContext(),Doctor.class);
                backToDoctorList.putExtra("profile_id",profileId);
                startActivity(backToDoctorList);
               finish();
            }
        });
    }
}
